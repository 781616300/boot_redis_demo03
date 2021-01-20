package com.zzyy.study.test;

/**
 * @auther zzyy
 * @create 2020-11-21 17:06
 */
public class Client01
{
    public static void main(String[] args)
    {
        System.out.println("==============第3季");

        //===========第3季 https://www.bilibili.com/video/BV1Hy4y1B78T?p=1&share_medium=android&share_plat=android&share_source=COPY&share_tag=s_i&timestamp=1605941821&unique_k=xIPwAV
        //1 sTempSubString = 3e80ea38

        //2 字符串3e80ea38将它作为16进制包装，后续做位运算
        System.out.println("-sTempSubString字符串作按照16进制包装，打出来是10进制"+Long.parseLong("3e80ea38", 16));

        //3 long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
        System.out.println(Long.toBinaryString(0x3FFFFFFF)); //111111111111111111111111111111
        System.out.println(Long.toBinaryString(0x3e80ea38)); //111110100000001110101000111000
        System.out.println("1---lHexLong: "+Long.toBinaryString(0x3FFFFFFF & 0x3e80ea38)); //111110100000001110101000111000 ---> 1048635960(10进制) ---> 3e80ea38(16进制)

        //4 long index = 0x0000003D & lHexLong;
        System.out.println("1---index: "+Long.toBinaryString(0x0000003D & 0x3e80ea38)+"\t"+"index: 56"); //二进制的111000换10进制：56


        System.out.println(1048635960 >> 5); //32769873
        //-------------第一次结束

        //32769873(10进制) = 1f40751(16进制)
        System.out.println("2---lHexLong: "+Long.toBinaryString(0x3FFFFFFF & 0x1f40751));//1111101000000011101010001(2进制) = 32769873(10进制) ---> 1f40751(16进制)
        System.out.println("2---index: "+Long.toBinaryString(0x0000003D & 0x1f40751)+"\t"+"index: 17"); //二进制的10001换10进制：17
        System.out.println(32769873 >> 5); //1024058
        //-------------第二次结束

        //1024058(10进制) = fa03a(16进制)
        System.out.println("3---lHexLong: "+Long.toBinaryString(0x3FFFFFFF & 0xfa03a));//11111010000000111010(2进制) = 1024058(10进制) ---> fa03a(16进制)
        System.out.println("3---index: "+Long.toBinaryString(0x0000003D & 0xfa03a)+"\t"+"index: 56"); //二进制的111000换10进制：56
        System.out.println(1024058 >> 5); //32001
        //-------------第三次结束



    }
}
