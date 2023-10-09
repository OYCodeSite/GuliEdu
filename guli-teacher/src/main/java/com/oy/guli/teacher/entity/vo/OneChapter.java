package com.oy.guli.teacher.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author OY
 * @Date 2021/3/17
 */
@Data
public class OneChapter {

    private String id;

    private String title;

    private List<TwoVideo> children = new ArrayList<>();
}
