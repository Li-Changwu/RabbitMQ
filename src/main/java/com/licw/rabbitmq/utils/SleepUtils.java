package com.licw.rabbitmq.utils;

/**
 * TODO
 *
 * @author licw
 * @version V1.0.0
 * @date 2022/11/4 13:19
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000L *  second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
