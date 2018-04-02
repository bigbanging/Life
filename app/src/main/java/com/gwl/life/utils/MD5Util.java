package com.gwl.life.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.utils
 * 文件名： MD5Util
 * 创建者： GWL
 * 创建时间：2018/4/1 21:22
 * 描述  ： MD5加密
 */
public class MD5Util {
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
    public static String encryption(String content){
        String s1 = content;
        for(int i = 0;i < 100;i++){
            s1 = getMD5(s1);
        }
        return s1;
    }
}
