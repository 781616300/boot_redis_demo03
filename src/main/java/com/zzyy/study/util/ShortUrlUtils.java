package com.zzyy.study.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @auther zzyy
 * @create 2020-11-21 16:23
 */
public class ShortUrlUtils
{
    //26+26+10=62
    public static  final  String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    /**
     * 一个长链接URL转换为4个短KEY
     　思路：
     　　1)将长网址md5生成32位签名串,分为4段, 每段8个字节;
     　　2)对这四段循环处理, 取8个字节, 将他看成16进制串与0x3fffffff(30位1)与操作, 即超过30位的忽略处理;
     　　3)这30位分成6段, 每5位的数字作为字母表的索引取得特定字符, 依次进行获得6位字符串;
     　　4)总的md5串可以获得4个6位串; 取里面的任意一个就可作为这个长url的短url地址;
     * 当我们点击这6个字母的链接后，我们又可以跳转到原始的真实链接地址。
     */
    public static String[] shortUrl(String url) {
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = DigestUtils.md5Hex(url);
        System.out.println("---------------sMD5EncryptResult: "+sMD5EncryptResult);
        System.out.println();
        //md5处理后是32位
        String hex = sMD5EncryptResult;
        //切割为4组，每组8个字符, 32 = 4 *  8
        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++) {
            //取出8位字符串，md5 32位，按照8位一组字符,被切割为4组
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            System.out.println("---------------sTempSubString: "+sTempSubString);
            System.out.println("-sTempSubString作为16进制的表示"+Long.parseLong(sTempSubString, 16));

            //把加密字符按照8位一组16进制与 0x3FFFFFFF 进行位与运算
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 ,
            // 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            System.out.println("---------lHexLong: "+lHexLong);

            String outChars = "";
            for (int j = 0; j < 6; j++) {
                //0x0000003D它的10进制是61，61代表最上面定义的chars数组长度62的0到61的坐标。
                //0x0000003D & lHexLong进行位与运算，就是格式化为6位，即保证了index绝对是61以内的值
                long index = 0x0000003D & lHexLong;
                System.out.println("----------index: "+index);
                // 按照下标index把从chars数组取得的字符逐个相加
                outChars += chars[(int) index];
                //每次循环按位移5位，因为30位的二进制，分6次循环，即每次右移5位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组,会产生一组6位字符串
            resUrl[i] = outChars;
        }
        return resUrl;
    }


    /**
     * 测试类
     * @param args
     */
    public static void main(String[] args) {
        // 长连接
        String longUrl = "https://www.bilibili.com/video/BV1Hy4y1B78T?p=1&share_medium=android&share_plat=android&share_source=COPY&share_tag=s_i&timestamp=1605941821&unique_k=xIPwAV";

        // 转换成的短链接后6位码，返回4个短链接
        String[] shortCodeArray = shortUrl(longUrl);

        for (int i = 0; i < shortCodeArray.length; i++) {
            // 任意一个都可以作为短链接码
            System.out.println(shortCodeArray[i]);
        }
    }
}
