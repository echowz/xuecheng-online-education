package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "课程师资信息接口", tags = "课程师资信息接口")
@RestController
public class CourseTeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;

    @ApiOperation("查询课程的教师列表")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> listTeacherByCourseId(@PathVariable Long courseId) {
        return courseTeacherService.listTeacherByCourseId(courseId);
    }

    @ApiOperation("保存课程教师信息 添加/编辑")
    @PostMapping("/courseTeacher")
    public void saveCourseTeacher(@RequestBody SaveCourseTeacherDto saveCourseTeacherDto) {
        courseTeacherService.saveCourseTeacher(saveCourseTeacherDto);
    }

    @ApiOperation("删除课程教师")
    @DeleteMapping ("/courseTeacher/course/{courseId}/{id}")
    public void deleteCourseTeacher(@PathVariable Long courseId, @PathVariable Long id) {
        courseTeacherService.deleteCourseTeacher(courseId,id);
    }
}
