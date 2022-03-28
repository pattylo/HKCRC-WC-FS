package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";
    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    //@CrossOrigin(origins = "http://localhost:9090")
    @PostMapping("upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //定义文件的唯一标识
        String flag = IdUtil.fastSimpleUUID();
        //String rootFilePath =  System.getProperty("user.dir") + "/springboot/src/main/resource/files/"+flag+"_"+originalFilename;
        String rootFilePath =  System.getProperty("user.dir") +"/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return Result.success(ip+":"+port+"/files/"+flag);
    }

    @PostMapping("/uploadfile")
    public Result<?> upload2(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //定义文件的唯一标识
        String flag = IdUtil.fastSimpleUUID();
        //String rootFilePath =  System.getProperty("user.dir") + "/springboot/src/main/resource/files/"+flag+"_"+originalFilename;
        String rootFilePath =  "../files/"+System.getProperty("user.dir") +"/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return Result.success("/files/"+flag);
    }

    /**
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles( @PathVariable String flag, HttpServletResponse response){
        OutputStream os;
        String basePath = System.getProperty("user.dir")+"springboot/src/main/resources/files/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath+fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch(Exception e){
            System.out.println("文件下载失败");
        }
    }
}
