package org.example.controller.common;


import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/common")
@Slf4j
public class UploadController {
    private final static  String LOCAL_PATH="E:\\AAADeepSeek\\image";

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
    //生成唯一文件名称
        String originalFilename = file.getOriginalFilename();
        //拿到最后一个小数点后边的全部内容
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //随机改名(防止名字重复覆盖)
        String newName= UUID.randomUUID()+ext;
        //2.添加上传文件的储存路径
        File folder = new File(LOCAL_PATH);
        //若文件不存在就创建
        if(!folder.exists()) folder.mkdirs();
        //写入磁盘保存
        //在(folder, newName)这个文件夹里创建一个待保存的空文件对象，只是在内存定义路径，磁盘还没生成文件
        File targetFile = new File(folder, newName);
        //把前端传过来来的图片写入磁盘，真正生成文件夹图片
        file.transferTo(targetFile);
        //拼接虚拟路径"/uploads/"，这个用什么前缀webConfig里面就要用什么前缀addResourceHandler("/uploads/**")
        String url="/uploads/"+ newName;
        return Result.success(url);


    }
}
