package com.xuecheng.content.service;


import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

public interface CourseTeacherService {
    List<CourseTeacher> listTeacherByCourseId(Long id);

    void saveCourseTeacher(SaveCourseTeacherDto saveCourseTeacherDto);

    void deleteCourseTeacher(Long courseId, Long id);
}
