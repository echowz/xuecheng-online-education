package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

public interface TeachplanService {

    /**
     * 查询课程计划树型结构
     */
    List<TeachplanDto> findTeachplanTree(long courseId);

}