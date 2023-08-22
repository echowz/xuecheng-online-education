package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;

import java.io.File;

/**
 * 课程预览、发布接口
 */
public interface CoursePublishService {


    /**
     * 获取课程预览信息
     */
    CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     *  提交审核
     */
    void commitAudit(Long companyId,Long courseId);

    /**
     * 课程发布接口
     */
    void publish(Long companyId,Long courseId);


    /**
     * @description 课程静态化
     * @param courseId  课程id
     * @return File 静态化文件
     */
    public File generateCourseHtml(Long courseId);
    /**
     * 上传课程静态化页面
     */
    public void  uploadCourseHtml(Long courseId,File file);
}