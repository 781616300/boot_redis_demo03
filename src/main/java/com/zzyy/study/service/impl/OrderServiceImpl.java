package com.zzyy.study.service.impl;

import com.zzyy.study.dao.OrderDao;
import com.zzyy.study.entities.Order;
import com.zzyy.study.service.OrderService;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @auther zzyy
 * @create 2020-10-25 14:04
 */
@Service
public class OrderServiceImpl implements OrderService
{
    public static final String CACHE_KEY_PREFIX = "ord:";


    @Resource
    private OrderDao orderDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public int addOrder(Order order)
    {
        //1 先往mysql插入
        int result = orderDao.addOrder(order);
        System.out.println("----------orderDao.addOrder(order): "+result);
        if(result > 0)
        {
            //2 mysql插入成功,再把数据从mysql捞出来，插入redis
            Order orderbyMysql = orderDao.getOrderBySerial(order.getOrderSerial());
            //3 插入redis
            redisTemplate.opsForValue().set(CACHE_KEY_PREFIX+orderbyMysql.getId(),orderbyMysql);
        }else{
            result = -4444;
        }
        return result;
    }

    @Override
    public int updateOrder(Order order)
    {
        //1 先修改mysql数据库
        int result = orderDao.updateOrder(order);
        if(result > 0)
        {
            //2 mysql修改成功,再把数据从mysql捞出来，插入redis
            Order orderbyMysql = orderDao.getOrderById(order.getId());
            //3 插入redis
            redisTemplate.opsForValue().set(CACHE_KEY_PREFIX+orderbyMysql.getId(),orderbyMysql);
        }else{
            result = -4444;
        }
        return result;
    }

    @Override
    public Order getOrderByToken(String orderToken)
    {
        return orderDao.getOrderByToken(orderToken);
    }

    @Override
    public Order getOrderBySerial(String orderSerial)
    {
        return orderDao.getOrderBySerial(orderSerial);
    }

    @Override
    public Order getOrderById(Long id)
    {
        Order order = null;
        //1 先去redis查，查得到直接返回，查不到去mysql查并回写redis
        order = (Order)redisTemplate.opsForValue().get(CACHE_KEY_PREFIX + id);
        if(null != order)
        {
            return order;
        }else{
            order = orderDao.getOrderById(id);
            //3 插入redis
            redisTemplate.opsForValue().set(CACHE_KEY_PREFIX+order.getId(),order);
        }
        return order;
    }

    @Override
    public int updateOrderStatus(String orderToken, Integer orderStatus)
    {
        return orderDao.updateOrderStatus(orderToken,orderStatus);
    }

    @Override
    public int updateOrderStatusBySerial(String orderSerial, Integer orderStatus)
    {
        return orderDao.updateOrderStatusBySerial(orderSerial,orderStatus);
    }
}
