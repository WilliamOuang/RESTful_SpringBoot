/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webstore.ws.api;

import com.webstore.model.Order;
import com.webstore.model.OrderProduct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author WeiliangOuyang
 */
@RestController
public class OrderController {
    
    private static    List<Order> orderList= new ArrayList<Order>();

    static{
        OrderProduct o = new OrderProduct();
            o.setP(ProductController.getProducts().get(0));
            o.setQuantity(1);
            o.setOrderPrice(ProductController.getProducts().get(0).getPrice());
            o.setOrderTime(System.currentTimeMillis());
            
        OrderProduct o1 = new OrderProduct();
            o1.setP(ProductController.getProducts().get(1));
            o1.setOrderPrice(ProductController.getProducts().get(1).getPrice());
            o1.setOrderTime(System.currentTimeMillis());
        
        List<OrderProduct> orders= new ArrayList<OrderProduct>();
            orders.add(o);
            orders.add(o1);

        Order order= new Order();
            order.setOrderId(1);
            order.setCheckOut(false);
            order.setOrderName("Order01");
            order.setList(orders);
            
        orderList.add(order);
    }
      
    Order findOrder(long id){
        
        for( int i = 0; i <orderList.size();i++ ){
            Order o = orderList.get(i);
             if(o.getOrderId()==id){
                return o;
            }
        }
       
        return null;
    }
    @RequestMapping(value = "/api/orders",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Order>> getOrderList() {
        return new ResponseEntity<Collection<Order>>(orderList, HttpStatus.OK);
    }
    
    
    
    @RequestMapping(value = "/api/orders",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody  Order o) {
            o.setOrderId(orderList.size()+1);
            orderList.add(o);
            List<OrderProduct> orders =o.getList();
            for(int i=0;i<orders.size();i++){
                OrderProduct op =orders.get(i);
                op.setOrderTime(System.currentTimeMillis());
            }
        return new ResponseEntity<Order>(o, HttpStatus.CREATED);
    }
    
    
    
    
    @RequestMapping(value = "/api/confirmOrder",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> confirmOrder(@RequestBody  Order o) {
            Order order =findOrder(o.getOrderId());
            orderList.remove(order);
            o.setOrderId(order.getOrderId());
          //  Compare Create time
          
            orderList.add(o);
            
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(value = "/api/orders",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrder(@RequestBody  Order o) {
            
            Order order =findOrder(o.getOrderId());
            orderList.remove(order);
            o.setOrderId(order.getOrderId());
            orderList.add(o);
        
            List<OrderProduct> orders =o.getList();
            for(int i=0;i<orders.size();i++){
                OrderProduct op =orders.get(i);
                op.setOrderTime(System.currentTimeMillis());
            }
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/orders/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") int id) {

        Order ord =findOrder(id);
        orderList.remove(ord);

        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
}
