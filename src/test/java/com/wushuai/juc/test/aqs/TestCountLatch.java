package com.wushuai.juc.test.aqs;

import com.wushuai.juc.entity.Country;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * <p>TestCountLatch</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-02 10:01
 */
public class TestCountLatch {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(Country.values().length);
        for (int i = 1; i <= 11; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("i : " + finalI + ", val : " + Objects.requireNonNull(Country.foreach(finalI)).getVal());
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    @Test
    public void test() {
        TestCountLatch o = new TestCountLatch();

    }
}
