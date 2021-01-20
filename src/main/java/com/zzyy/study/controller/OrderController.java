package com.zzyy.study.controller;

import com.zzyy.study.entities.Order;
import com.zzyy.study.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-10-25 14:06
 */
@RestController
public class OrderController
{
    @Resource
    private OrderService orderService;

    /**
     * http://localhost:5555/order/add?orderName=麦当劳01&orderStatus=0&orderToken=7c020e3e2b2740cc9e21eb2726d3a033&orderSerial=1603351603733
     * @param order
     * @return
     */
    @RequestMapping("/order/add")
    public String add(Order order)
    {
        return "-----add order success: " + orderService.addOrder(order);
    }

    /**
     * http://localhost:5555/order/update?id=106&orderName=951&orderStatus=0&orderToken=7c020e3e2b2740cc9e21eb2726d3a032&orderSerial=1603351603732
     * @param order
     * @return
     */
    @RequestMapping("/order/update")
    public String update(Order order)
    {
        return "-----update order success: " + orderService.updateOrder(order);
    }

    @RequestMapping("/order/{id}")
    public Order get(@PathVariable Long id)
    {
        return orderService.getOrderById(id);
    }
}