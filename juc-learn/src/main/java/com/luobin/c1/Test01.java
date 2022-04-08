package com.luobin.c1;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Doraemon
 * @date 2022/4/5 4:20 下午
 * @version 1.0
 */
public class Test01 {
    public static void main(String[] args) {
        Account account = new AccountCas(10000);


    }

}

class AccountCas implements Account {
    // 使用底层创建号的这种方式，就可以不用加锁就可以实现线程安全的访问；
    private AtomicInteger balance;

    @Override
    public Integer getBalance() {
        return null;
    }

    @Override
    public void withdraw(Integer amount) {

        while (true) {
            int prev = balance.get();

            int next = prev - amount;

            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }

    public AccountCas(int balance) {
        // 进行封装；
        this.balance = new AtomicInteger(balance);
    }
}

interface Account {
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end - start) / 1000_000 + " ms");
    }
}
