package br.com.chuksricardo.order_management_ssm;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

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
}
