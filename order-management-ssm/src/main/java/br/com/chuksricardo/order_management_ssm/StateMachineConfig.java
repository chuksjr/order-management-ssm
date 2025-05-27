package br.com.chuksricardo.order_management_ssm;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvents>{
  @Override
  public void configure(StateMachineStateConfigurer<OrderState, OrderEvents> states) throws Exception {
    states
    .withStates()
    .initial(OrderState.NEW)
    .states(EnumSet.allOf(OrderState.class))
    .end(OrderState.COMPLETED)
    .end(OrderState.CANCELED);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvents> transitions) throws Exception{
    transitions
    .withExternal().source(OrderState.NEW).target(OrderState.VALIDATED).event(OrderEvents.VALIDATE)
    .action(validateOrderAction())
    .and()
    .withExternal().source(OrderState.VALIDATED).target(OrderState.PAID).event(OrderEvents.PAY)
    .action(payOrderAction())
    .and()
    .withExternal().source(OrderState.PAID).target(OrderState.SHIPPED).event(OrderEvents.SHIP)
    .action(shipOrderAction())
    .and()
    .withExternal().source(OrderState.SHIPPED).target(OrderState.COMPLETED).event(OrderEvents.COMPLETE)
    .and()
    .withExternal().source(OrderState.VALIDATED).target(OrderState.CANCELED).event(OrderEvents.CANCEL)
    .and()
    .withExternal().source(OrderState.PAID).target(OrderState.CANCELED).event(OrderEvents.CANCEL);

  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvents> config) throws Exception{
    config.withConfiguration().listener(stateMachineListener());
  }

  @Bean
  Action<OrderState, OrderEvents> shipOrderAction() {
    return contex -> {
      System.out.println("Shipping Order");
    };
  }
  
  @Bean
  Action<OrderState, OrderEvents> payOrderAction() {
    return contex -> {
      System.out.println("Paying Order");
    };
  }

  @Bean
  Action<OrderState, OrderEvents> validateOrderAction() {
    return contex -> {
      System.out.println("Validating Order");
    };
  }

    @Bean
    StateMachineListener<OrderState, OrderEvents> stateMachineListener() {
        return new StateMachineListenerAdapter<>(){
          @Override
          public void transition(Transition<OrderState, OrderEvents> transition){
            System.out.println("Transitioning from" + transition.getSource().getId()+" to " + transition.getTarget().getId());
          }
        };
    }
}
