package br.com.chuksricardo.order_management_ssm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
  @Autowired
  private StateMachineFactory<OrderState, OrderEvents> stateMachineFactory;
  private StateMachine<OrderState, OrderEvents> stateMachine;

  public void newOrder(){
    initOrderSaga();
    validadeOrder();
  }

   private void validadeOrder(){
    System.out.println("Validating order");
    stateMachine.sendEvent(Mono.just(
      MessageBuilder.withPayload(OrderEvents.VALIDATE).build()))
      .subscribe(result -> System.out.println(result.getResultType()));
      System.out.println("Final state: " + stateMachine.getState().getId());
  }

   void payOrder(){
    System.out.println("Paying order");
    stateMachine.sendEvent(Mono.just(
      MessageBuilder.withPayload(OrderEvents.PAY).build()))
      .subscribe(result -> System.out.println(result.getResultType()));
      System.out.println("Final state: " + stateMachine.getState().getId());
  }

  void shipOrder(){
    System.out.println("Shipping order");
    stateMachine.sendEvent(Mono.just(
      MessageBuilder.withPayload(OrderEvents.SHIP).build()))
      .subscribe(result -> System.out.println(result.getResultType()));
      System.out.println("Final state: " + stateMachine.getState().getId());
  }

  void completeOrder(){
    System.out.println("Completing order");
    stateMachine.sendEvent(Mono.just(
      MessageBuilder.withPayload(OrderEvents.COMPLETE).build()))
      .subscribe(result -> System.out.println(result.getResultType()));
      System.out.println("Final state: " + stateMachine.getState().getId());

      stopOrderSaga();
  }

  private void initOrderSaga(){
    System.out.println("Initializing order saga");
    stateMachine = stateMachineFactory.getStateMachine();
    stateMachine.startReactively().subscribe();
    System.out.println("Final state: " + stateMachine.getState().getId());
  }

  private void stopOrderSaga() {
    System.out.println("Stopping saga");
    stateMachine.stopReactively().subscribe();  
  }


}
