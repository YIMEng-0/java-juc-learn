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
@Slf4j(topic = "c.Test20")
public class Test20 {
    public static void main(String[] args) {
        // 创建出来的同一个 guardedObject 对象，需要保证线程安全；
        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            // 获取 response 的时候，需要等待另外一个线程返回结果；
            log.debug("等待下载结果");
            List<String> list = (List<String>) guardedObject.get(10000);
            log.debug(String.valueOf(list.size()));
        }, "t1").start();

        new Thread(() -> {
            log.debug("执行下载");
            try {
                // 获取执行结果，放到完成的方法中叫醒 t1 线程
                List list =  download();
                // 将数据传递过去之后，就会将 wait 状态的线程唤醒；
                guardedObject.complete(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com/").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}

class GuardedObject {
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
