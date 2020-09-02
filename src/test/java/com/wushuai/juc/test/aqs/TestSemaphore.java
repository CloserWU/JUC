package com.wushuai.juc.test.aqs;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * <p>TestSemaphore</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-02 10:25
 */
public class TestSemaphore {


    public static void main(String[] args) {
        // 3个资源
        Semaphore semaphore = new Semaphore(3);

        // 6个获取
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("i " + finalI + " 获得一个资源");
                    Thread.sleep(2500);
                    semaphore.release();
                    System.out.println("释放一个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void test() {
        TestSemaphore o = new TestSemaphore();

    }
}
