package br.com.chuksricardo.order_management_ssm;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

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
    .and()
    .withExternal().source(OrderState.VALIDATED).target(OrderState.PAID).event(OrderEvents.PAY)
    .and()
    .withExternal().source(OrderState.PAID).target(OrderState.SHIPPED).event(OrderEvents.SHIP)
    .and()
    .withExternal().source(OrderState.SHIPPED).target(OrderState.COMPLETED).event(OrderEvents.COMPLETE)
    .and()
    .withExternal().source(OrderState.VALIDATED).target(OrderState.CANCELED).event(OrderEvents.CANCEL)
    .and()
    .withExternal().source(OrderState.PAID).target(OrderState.CANCELED).event(OrderEvents.CANCEL);

  }
}
