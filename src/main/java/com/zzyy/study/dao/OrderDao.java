package com.zzyy.study.dao;

import com.zzyy.study.entities.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther zzyy
 * @create 2020-10-25 13:43
 */
@Mapper
public interface OrderDao
{
    int addOrder(Order order);

    int updateOrder(Order order);

    Order getOrderByToken(String orderToken);

    int updateOrderStatus(@Param("orderToken") String orderToken, @Param("orderStatus")Integer orderStatus);

    Order getOrderBySerial(String orderSerial);

    int updateOrderStatusBySerial(@Param("orderSerial") String orderToken, @Param("orderStatus")Integer orderStatus);


    Order getOrderById(@Param("id") Long id);
}