package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

public interface TeachplanService {

    /**
     * 查询课程计划树型结构
     */
    List<TeachplanDto> findTeachplanTree(long courseId);

    /**
     * 新增或修改保存课程计划
     */
    void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * 删除课程计划
     */
    void deleteTeachplan(Long id);

    TeachplanDto getTeachplanDtoById(Long id);

    /**
     * 课程计划上下移动
     * @param id 课程教学计划id
     * @param upOrDown true为上移，false为下移
     */
    void teachplanMove(Long id, boolean upOrDown);
}