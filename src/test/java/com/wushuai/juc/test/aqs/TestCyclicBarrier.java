package com.wushuai.juc.test.aqs;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>TestCyclicBarrier</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-02 10:19
 */
public class TestCyclicBarrier {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("done");
        });

        for (int i = 0; i < 7; i++) {
            int finalI = i;
            int finalI1 = i;
            new Thread(() -> {
                try {
                    System.out.println("party " + finalI1 + " done, waiting...");
                    int await = cyclicBarrier.await();
                    System.out.println("i : " + finalI + ", await : " + await);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void test() {
        TestCyclicBarrier o = new TestCyclicBarrier();

    }
}
