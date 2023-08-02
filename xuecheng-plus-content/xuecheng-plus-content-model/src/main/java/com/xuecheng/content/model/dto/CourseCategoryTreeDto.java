package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 课程分类树型结点dto
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
  List<CourseCategoryTreeDto> childrenTreeNodes;

  @Override
  public String toString() {
    return "CourseCategoryTreeDto{" +
            " field: "+
            super.toString()+
            "\n"+
            "childrenTreeNodes=" + childrenTreeNodes +"\n"+
            '}';
  }
}