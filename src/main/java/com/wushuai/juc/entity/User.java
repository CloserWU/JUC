package com.wushuai.juc.entity;


import lombok.*;

/**
 * <p>User</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-12 17:10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private boolean gender;
    private String name;
    private Integer age;
}
