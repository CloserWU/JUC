package com.wushuai.juc.entity;


import lombok.Getter;

/**
 * <p>Country</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-02 10:06
 */
public enum Country {
    ONE(1, "v1"),
    TWO(2, "v2"),
    THREE(3, "v3"),
    FOUR(4, "v4"),
    FIVE(5, "v5"),
    SIX(6, "v6"),
    SEVEN(7, "v7"),
    EIGHT(8, "v8"),
    NINE(9, "v9"),
    TEN(10, "v10"),
    ELEVEN(11, "v11");

    @Getter private Integer key;
    @Getter private String val;


    Country(Integer key, String val) {
        this.val = val;
        this.key = key;
    }

    public static Country foreach(int key) {
        Country[] values = Country.values();
        for (Country ele : values) {
            if (ele.getKey() == key) {
                return ele;
            }
        }
        return null;
    }

}
