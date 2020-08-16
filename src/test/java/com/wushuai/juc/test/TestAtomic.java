package com.wushuai.juc.test;

import com.wushuai.juc.entity.User;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <p>TestAtomic</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-16 11:30
 */
public class TestAtomic {

    static int getStamp(int oldStamp) {
        return (int) (System.currentTimeMillis() % 100000)  + oldStamp;
    }


    public static void main(String[] args) {
        User user1 = new User(true, "Coach", 22);
        User user2 = new User(false, "Lynn", 25);
        int seed = (int) (System.currentTimeMillis() % 100000);
        AtomicInteger a1 = new AtomicInteger(1);
        AtomicStampedReference<User> user = new AtomicStampedReference<>(user1, getStamp(seed));
        new Thread(new Runnable() {
            @Override
            public void run() {
                user.compareAndSet(user1, user2, user.getStamp(), getStamp(user.getStamp()));
                user.compareAndSet(user2, user1, user.getStamp(), getStamp(user.getStamp()));
                System.out.println(Thread.currentThread().getName() + user.getReference().toString() + user.getStamp());
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(user.compareAndSet(user1, user2, getStamp(seed), getStamp(user.getStamp())));
            System.out.println(Thread.currentThread().getName() + user.getReference().toString() + user.getStamp());
        }, "t2").start();
    }
}
