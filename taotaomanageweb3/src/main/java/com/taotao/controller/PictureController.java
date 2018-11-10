package com.taotao.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.taotao.common.pojo.PictureResult;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-09 17:36
 **/

@Controller
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/upload")
    @ResponseBody
    public PictureResult uploda(MultipartFile uploadFile) throws Exception {
        //调用service上传图片
        PictureResult pictureResult = pictureService.uploadFile(uploadFile);
        System.out.println(pictureResult);
        //返回上传结果
        return pictureResult;
    }
}
