package com.oy.guli.teacher.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author OY
 * @Date 2021/3/14
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery implements Serializable {

    @ApiModelProperty(value = "课程名称")
    private String subjectId;

    @ApiModelProperty(value = "讲师id")
    private String subjectParentId;

    @ApiModelProperty(value = "一级类别id")
    private String title;

    @ApiModelProperty(value = "二级类别id")
    private String teacherId;
}
