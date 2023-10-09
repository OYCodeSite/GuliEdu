package com.oy.guli.teacher.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author OY
 * @Date 2021/2/27
 */
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
public class TeacherQuery {

    private String name;

    private Integer level;

    @ApiModelProperty(value = "创建时间",example = "2019-01-01 8:00:00")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间",example = "2019-01-01 8:00:00")
    private Date gmtModified;
}
