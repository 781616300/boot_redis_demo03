package com.zzyy.study.controller;

import com.zzyy.study.util.ShortUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther zzyy
 * @create 2020-11-21 16:22
 */
@RestController
public class ShortUrlController
{
    private  final static  String SHORT_URL_KEY="short:url";

    @Resource
    private HttpServletResponse response;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 长链接转换为短链接
     * 实现原理：长链接转换为短加密串key，然后存储在redis的hash结构中。
     */
    @GetMapping(value = "/encode")
    public String encode(String longUrl) {
        //一个长链接url转换为4个短加密串key
        String [] keys= ShortUrlUtils.shortUrl(longUrl);
        //任意取出其中一个，我们就拿第一个
        String shortUrlKey=keys[0];
        //用hash存储，key=加密串，value=原始url
        this.redisTemplate.opsForHash().put(SHORT_URL_KEY,shortUrlKey,longUrl);
        System.out.println("长链接: "+longUrl+"\t"+"转换短链接: "+shortUrlKey);

        return "http://127.0.0.1:5555/"+shortUrlKey;
    }

    /**
     * 重定向到原始的URL
     * 实现原理：通过短加密串KEY到redis找出原始URL，然后重定向出去
     */
    @GetMapping(value = "/decode/{shortUrlKey}")
    public void decode(@PathVariable String shortUrlKey) {
        //到redis中把原始url找出来
        String url=(String) this.redisTemplate.opsForHash().get(SHORT_URL_KEY,shortUrlKey);
        System.out.println("----真实长地址url: "+url);
        try {
            //重定向到原始的url
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
