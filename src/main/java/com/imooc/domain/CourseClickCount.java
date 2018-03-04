package com.imooc.domain;

import org.springframework.stereotype.Component;

/**
 * Created by Horizon
 * Time: 下午9:28 2018/3/3
 * Description:
 */
@Component
public class CourseClickCount {

    private String name;
    private Long value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CourseClickCount{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
