package com.wushuai.juc.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>TestReadWrite</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-31 15:30
 */
public class TestReadWrite {

    ReadWriteLock lock = new ReentrantReadWriteLock();

    volatile Map<Integer, String> memo = new HashMap<>();

    public static void main(String[] args) {
        TestReadWrite o = new TestReadWrite();
        o.func1();
    }

    void func1() {
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(() -> {
                write(finalI, UUID.randomUUID().toString().substring(0, 6));
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(() -> {
                read(finalI);
            }).start();
        }

    }


    void read(int key) {
        lock.readLock().lock();
        lock.readLock().lock();
        try {
            System.out.println("begin read");
            Thread.sleep(150);
            System.out.println(memo.get(key));
            System.out.println("end read : " + key + " = ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
            lock.readLock().unlock();
        }
    }

    void write(int key, String val) {
        lock.writeLock().lock();
        try {
            System.out.println("begin write: " + key);
            Thread.sleep(150);
            memo.put(key, val);
            System.out.println("end write");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

}
