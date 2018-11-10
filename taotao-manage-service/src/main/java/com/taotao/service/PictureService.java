package com.taotao.service;

import com.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-09 17:14
 **/
public interface PictureService {
    PictureResult uploadFile(MultipartFile uploadFile) throws Exception;
}
