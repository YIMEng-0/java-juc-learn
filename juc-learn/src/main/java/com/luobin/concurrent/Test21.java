package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Doraemon
 * @date 2022/3/29 10:47 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test21")
public class Test21 {
    public static void main(String[] args) {

    }

}

class GuardedObject1 {
    // 表示 GuardedObject
    private int id;

    public GuardedObject1(int id) {

    }





    // 结果
    private Object response;

    public Object get(long timedOut) {
        synchronized (this) {


            while (response == null) {
                // 记录开始时间
                long begin = System.currentTimeMillis();
                // 经历的时间
                long passTime = 0; // 刚开始还没有等待，所以是 0 ；

                // 经过的时间大于设定的超时时间，超时了，直接打断即可
                long waitTime = timedOut - passTime;
                // timeOut = passTime + waitTime;
                if (waitTime <= 0) {
                    break;
                }

                try {
                    // 等待是为了获取线程 2 的执行结果
                    this.wait(waitTime); // 因为存在虚假唤醒， while 可以控制虚假唤醒的次数，如果直接 是 timeOut 每次来都等待这个长时间
                    // 早就超过了 timeOut 给定的时间，防止虚假唤醒的操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 经历的时间
                passTime = System.currentTimeMillis() - begin;
            }
        }

        // 不用等待了，因为有数值可以获取了
        // 因为 response 有了值，不用等待，可以拿到这个 response;
        return response;
    }

    public void complete(Object response) {
        synchronized (this) {
            // 给结果变量赋值
            this.response = response;

            // 因为赋值好了，所以可以叫醒需要获取的线程
            this.notifyAll();
        }
    }
}
