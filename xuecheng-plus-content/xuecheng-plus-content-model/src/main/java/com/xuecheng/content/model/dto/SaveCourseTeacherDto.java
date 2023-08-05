package com.xuecheng.content.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "AddCourseTeacherDto",description = "添加课程教师的参数Dto")
public class SaveCourseTeacherDto {

    @ApiModelProperty(value = "教师Id")
    private Long id;

    @NotEmpty(message = "课程id不能为空")
    @ApiModelProperty(value = "课程id",required = true)
    private Long courseId;

    @NotEmpty(message = "教师名称不能为空")
    @ApiModelProperty(value = "教师名称",required = true)
    private String teacherName;

    @NotEmpty(message = "教师职位不能为空")
    @ApiModelProperty(value = "教师职位",required = true)
    private String position;

    @ApiModelProperty(value = "教师简介")
    private String introduction;

    @ApiModelProperty(value = "教师照片")
    private String photograph;
}
