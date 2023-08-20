package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;

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

}