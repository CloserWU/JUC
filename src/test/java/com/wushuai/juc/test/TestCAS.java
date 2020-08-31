package com.wushuai.juc.test;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>TestCAS</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-31 14:51
 */
public class TestCAS {
    static final Object o = new Object();
    static Lock lock = new ReentrantLock();

    volatile int a = 0;
    AtomicInteger a1 = new AtomicInteger(0);


    static Unsafe unsafe = null;
    static long state;

    static {
        try {
            Class<?> obj = Class.forName("sun.misc.Unsafe");
            Field field = obj.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            state = unsafe.objectFieldOffset(TestCAS.class.getDeclaredField("a"));
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestCAS o = new TestCAS();
        o.func2();
    }


    void func0() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(() -> {
                lock.lock();
                try {
                    while (a != j) {
                        // 拿到锁，不释放，死锁
                    }
                    a = j + 1;
                    System.out.println(Thread.currentThread().getName() + "a:" + a);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, String.valueOf(i)).start();
        }
    }


    void func() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(() -> {
                while (a != j) {
                    // 不加锁，无法满足原子性
                }
                a = j + 1;
                System.out.println(Thread.currentThread().getName() + "a:" + a);
            }, String.valueOf(i)).start();
        }
    }

    void func_1() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(() -> {
                while (a != j) {
                    // 不加锁，无法满足原子性
                }
                int i1 = unsafe.getAndAddInt(this, state, 1);
                System.out.println(Thread.currentThread().getName() + "a:" + (i1 + 1));
            }, String.valueOf(i)).start();
        }
    }

    void func1() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread(() -> {
                while (a1.get() != j) {

                }
                // 以下两句不保证原子性
                boolean b = a1.compareAndSet(j, j + 1);
                if (b) {
                    System.out.println(Thread.currentThread().getName() + "a1:" + (j + 1));
                }

            }, String.valueOf(i)).start();
        }
    }

    AtomicReference<Thread> atomicReference = new AtomicReference<>(null);


    void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) { }
        System.out.println("this lock is held by " + thread.getName());
    }

    void unlock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(thread, null)) { }
        System.out.println("unlock");
    }

    void func2() {
        // 一把锁
        new Thread(() -> {
            lock();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }, "AA").start();

        new Thread(() -> {
            lock();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }, "BB").start();
    }

    @Test
    public void test() {
        TestCAS o = new TestCAS();

    }
}
