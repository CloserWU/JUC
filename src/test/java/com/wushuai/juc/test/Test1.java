package com.wushuai.juc.test;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <p>Test1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-12 17:13
 */
public class Test1 {

    static Unsafe unsafe;
    static long stateOffset;
    private volatile int state = 0;

    static {
        try {
            Class<?> name = Class.forName("sun.misc.Unsafe");
            Field field = name.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(Test1.class.getDeclaredField("state"));
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test() {
        Test1 o = new Test1();
        System.out.println(unsafe.getAndAddInt(o, stateOffset, 1));
        System.out.println(state);
        System.out.println(o.state);
    }
}
