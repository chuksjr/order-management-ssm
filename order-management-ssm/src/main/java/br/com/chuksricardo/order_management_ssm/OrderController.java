package br.com.chuksricardo.order_management_ssm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
  @Autowired
  private OrderService orderService;



  @PostMapping("newOrder")
  public String newOrder(){
    orderService.newOrder();
    return "new order";
  }
  


  @PostMapping("pay")
  public String payOrder(){
    orderService.payOrder();
    return "pay order";
  }


  @PostMapping("ship")
  public String shipOrder(){
    orderService.shipOrder();
    return "ship order";
  }


  @PostMapping("complete")
  public String completeOrder(){
    orderService.completeOrder();
    return "complete order";
  }
}
