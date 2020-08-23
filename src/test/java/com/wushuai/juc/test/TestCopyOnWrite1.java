package com.wushuai.juc.test;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>TestCopyOnWrite1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-23 11:42
 */
public class TestCopyOnWrite1 {

    public static void main(String[] args) {
        // 新建CopyOnWriteArrayList 在add时调用addIfAbsent进行去重操作，同时采取读写分离
        Set<String> set = new CopyOnWriteArraySet<>();
        try {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    set.add(UUID.randomUUID().toString().substring(0, 6));
                    System.out.println(set);
                }, String.valueOf(i)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // new HashMap<>(); add, map.put(E e, PRESENT) PRESENT-> static final new Object();
        new HashSet<>();
    }

    @Test
    public void test() {
        TestCopyOnWrite1 o = new TestCopyOnWrite1();

    }
}
