package com.oy.guli.teacher.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/9
 */
@Data
public class OneSubject {
    private String id;

    private String title;

    List<TwoSubject> children = new ArrayList<>();
}
