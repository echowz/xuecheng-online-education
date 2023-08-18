package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程计划编辑接口
 */
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
@RestController
public class TeachplanController {

    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("根据课程id 查询课程教学计划 的树形结构（章节目录，大章节小章节）")
    @ApiImplicitParam(value = "courseId", name = "课程基础Id值", required = true, dataType = "Long", paramType = "path")
    @GetMapping("teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }


    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplan) {
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan(@PathVariable Long id) {
        teachplanService.deleteTeachplan(id);
    }

    @ApiOperation("课程计划章节上移")
    @PostMapping("/teachplan/moveup/{id}")
    public void teachplanMoveUp(@PathVariable Long id) {
        teachplanService.teachplanMove(id, true);
    }

    @ApiOperation("课程计划章节下移")
    @PostMapping("/teachplan/movedown/{id}")
    public void teachplanMoveDown(@PathVariable Long id) {
        teachplanService.teachplanMove(id, false);
    }

    @ApiOperation(value = "课程计划和媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto) {
        teachplanService.associationMedia(bindTeachplanMediaDto);
    }

    @ApiOperation("课程计划解绑媒资信息")
    @DeleteMapping("/teachplan/association/media/{teachPlanId}/{mediaId}")
    void deleteAssociation(@PathVariable String teachPlanId, @PathVariable String mediaId) {
        teachplanService.deleteAssociation(teachPlanId,mediaId);
    }
}