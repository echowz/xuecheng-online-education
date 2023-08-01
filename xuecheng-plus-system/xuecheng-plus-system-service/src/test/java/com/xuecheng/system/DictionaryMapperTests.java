package com.xuecheng.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xuecheng.system.mapper.DictionaryMapper;
import com.xuecheng.system.model.po.Dictionary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DictionaryMapperTests {

    @Autowired
    DictionaryMapper dictionaryMapper;
    @Test
    void testDictionaryMapper() {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(queryWrapper);
        System.out.println(dictionaryList);
    }


    @Test
    void sayHi(){
        System.out.println("hi!!!!");
    }

}