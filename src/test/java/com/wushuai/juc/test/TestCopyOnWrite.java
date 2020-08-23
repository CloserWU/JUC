package com.wushuai.juc.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>TestCopyOnWrite</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-23 11:08
 */
public class TestCopyOnWrite {

    public static void main(String[] args) {
        // modCount++; 指令重排序，多个线程进入，会将元素放入同一个位置
        List<String> ori = new ArrayList<>();
        // list, 外层包锁，详见 SynchronizedList 内部类重写的add方法，并且对set和get都加了锁
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        // list1，读写分离list，详见CopyOnWriteArrayList的add方法，首先获得锁，并复制一份array，在新的array加入元素，在写回原array
        // 为什么要采取这种费时间费空间的操作，原因在于， **读写分离**， add时，加入元素的操作并不影响get，并对get来说是不可见的。
        // get的还是原array，只有add完成了并且释放锁，get才能获取到新的
        List<String> list1 = new CopyOnWriteArrayList<>();
        try {
            for (int i = 0; i < 100; i++) {
                // Exception in thread "78" Exception in thread "81" java.util.ConcurrentModificationException
                new Thread(() -> {
                    ori.add(UUID.randomUUID().toString().substring(0, 6));
                    System.out.println(ori);
                }, String.valueOf(i)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    list.add(UUID.randomUUID().toString().substring(0, 6));
                    System.out.println(list);
                }, String.valueOf(i)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() { }
}
