package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;

public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://81.70.191.86:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    //上传文件
    @Test
    public void upload() {
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".png");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
        }
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("t/yyxzz.png")  //储存目标位置文件
                    .filename("C:\\Users\\10077\\Desktop\\pic\\lbxx.png") //源文件
//                    .contentType("video/mp4")//默认根据扩展名确定文件内容类型，也可以指定
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

    }

    //查询文件 下载
    @Test
    public void getFile() {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("t/yyxzz.png").build();
        try(
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream(new File("D:\\lbxx.png"));
        ) {
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete(){
        try {
            // 删除目标储存文件
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket("testbucket").object("t/t").build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

}