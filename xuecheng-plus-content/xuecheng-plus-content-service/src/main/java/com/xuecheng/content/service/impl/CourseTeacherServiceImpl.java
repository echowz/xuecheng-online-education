package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> listTeacherByCourseId(Long id){
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, id);
        return courseTeacherMapper.selectList(queryWrapper);
    }

    @Override
    public void saveCourseTeacher(SaveCourseTeacherDto saveCourseTeacherDto){
        Long teacherId = saveCourseTeacherDto.getId();
        CourseTeacher courseTeacher = new CourseTeacher();
        BeanUtils.copyProperties(saveCourseTeacherDto,courseTeacher);
        // 通过是否有主键判断添加还是修改
        if(teacherId != null){
            courseTeacherMapper.updateById(courseTeacher);
        }else {
            courseTeacherMapper.insert(courseTeacher);
        }
    }

    @Override
    public void deleteCourseTeacher(Long courseId,Long id){
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        queryWrapper.eq(CourseTeacher::getId,id);
        courseTeacherMapper.delete(queryWrapper);
    }
}
