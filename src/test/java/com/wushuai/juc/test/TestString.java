package com.wushuai.juc.test;

import org.junit.Test;

/**
 * <p>TestString</p>
 * <p>
 * https://juejin.im/post/6844904192209846280#heading-6
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-23 10:24
 */
public class TestString {


    @Test
    public void test() {
        String s1 = "1";
        String s2 = new String("1");
        System.out.println(s1 == s2);
    }

    @Test
    public void test1() {
        String s2 = new String("1");
        String s1 = "1";
        System.out.println(s1 == s2);
    }

    @Test
    public void test2() {
        String s2 = new String("1");
        s2.intern();
        String s1 = "1";
        System.out.println(s1 == s2);
    }


    @Test
    public void test3() {
        String s2 = new String("1") + new String("1");
        s2.intern();
        String s1 = "11";
        System.out.println(s1 == s2);
    }


    @Test
    public void test4() {
        String s2 = new String("1");
        s2.intern();
        String s1 = "1";
        System.out.println(s1 == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
    }
}
