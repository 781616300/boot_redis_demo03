package com.zzyy.study.service;

import com.zzyy.study.entities.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @auther zzyy
 * @create 2020-10-25 14:01
 */
public interface OrderService
{
    int addOrder(Order order);
    int updateOrder(Order order);

    int updateOrderStatus(String orderToken,Integer orderStatus);

    int updateOrderStatusBySerial(String orderSerial,Integer orderStatus);


    Order getOrderByToken(String orderToken);

    Order getOrderBySerial(String orderSerial);

    Order getOrderById(Long id);

}
