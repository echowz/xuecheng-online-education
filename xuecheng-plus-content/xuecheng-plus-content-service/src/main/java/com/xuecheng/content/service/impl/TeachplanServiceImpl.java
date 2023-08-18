package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程计划service接口实现类
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);

            teachplanMapper.insert(teachplanNew);

        }

    }

    /**
     * 获取最新的排序号
     */
    private int getTeachplanCount(long courseId, long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }


    @Override
    public void deleteTeachplan(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        // 删除大章
        if (teachplan.getParentid() == 0) {
            TeachplanDto teachplanDto = this.getTeachplanDtoById(id);
            // 没有小节才可以删除
            if (teachplanDto.getTeachPlanTreeNodes().isEmpty()) {
                teachplanMapper.deleteById(id);
            } else {
                XueChengPlusException.cast("该大章节下仍有小节，不可删除");
            }
        }
        // 删除小节
        else {
            // 查询绑定在该小节下的media，并删除
            LambdaQueryWrapper<TeachplanMedia> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TeachplanMedia::getTeachplanId, teachplan.getId());
            TeachplanMedia media = teachplanMediaMapper.selectOne(queryWrapper);
            if (media != null)
                teachplanMediaMapper.deleteById(media.getId());

            teachplanMapper.deleteById(id);
        }


        // 修正删除导致的顺序序号错误问题
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid());
        queryWrapper.orderByAsc(Teachplan::getOrderby);
        // 查询库中缺失一项的列表顺序
        List<Teachplan> teachplanList = teachplanMapper.selectList(queryWrapper);
        // 按照该顺序重新赋值
        for(int i=0;i<teachplanList.size();i++){
            Teachplan item = teachplanList.get(i);
            if(item.getOrderby() != i) {
                item.setOrderby(i);
                teachplanMapper.updateById(item);
            }
        }


    }

    /**
     * 根据大章节id 查询某个大章节 及其小节和媒资信息
     */
    @Override
    public TeachplanDto getTeachplanDtoById(Long id) {
        return teachplanMapper.getTeachplanDtoById(id);
    }


    @Override
    public void teachplanMove(Long id, boolean upOrDown){
        int tmp = upOrDown?-1:1;

        Teachplan source = teachplanMapper.selectById(id);
        LambdaQueryWrapper<Teachplan> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,source.getParentid());
        queryWrapper.eq(Teachplan::getOrderby,source.getOrderby()+tmp);
        Teachplan target = teachplanMapper.selectOne(queryWrapper);
        // 不处于端点，可进行交换
        if(target != null){
            source.setOrderby(source.getOrderby()+tmp);
            target.setOrderby(target.getOrderby()-tmp);
            teachplanMapper.updateById(source);
            teachplanMapper.updateById(target);
        }
    }

    @Transactional
    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        //教学计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            XueChengPlusException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if(grade!=2){
            XueChengPlusException.cast("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachplan.getCourseId();

        //先删除原来该教学计划绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId,teachplanId));

        //再添加教学计划与媒资的绑定关系
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }

    @Override
    public void deleteAssociation(String teachplanId, String mediaId) {
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId,teachplanId));
    }
}