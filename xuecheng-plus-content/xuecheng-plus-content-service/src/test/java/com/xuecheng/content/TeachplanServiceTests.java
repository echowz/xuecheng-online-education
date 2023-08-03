package com.xuecheng.content;


import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeachplanServiceTests {

    @Autowired
    TeachplanService teachplanService;

    /**
     * 根据大章节id 查询某个大章节 及其小节和媒资信息
     */
    @Test
    public void getTeachplanDtoByIdTest(){
        TeachplanDto TeachplanDto = teachplanService.getTeachplanDtoById(43L);
        System.out.println(TeachplanDto);
    }

}
