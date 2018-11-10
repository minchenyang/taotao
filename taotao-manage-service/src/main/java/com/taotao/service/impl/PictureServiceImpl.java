package com.taotao.service.impl;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-09 17:14
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_Host}")
    private String FTP_Host;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    public PictureResult uploadFile(final MultipartFile uploadFile) throws Exception {
        String path = savePicture(uploadFile);
        PictureResult result = new PictureResult(0, IMAGE_BASE_URL + path, "");
        return result;
    }

    private String savePicture(MultipartFile uploadFile) {
        String result = null;
        try {
            if (uploadFile.isEmpty())
                return null;

            // 上传文件以日期为单位分开存放，可以提高图片的查询速度
            String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date()) + "/"
                    + new SimpleDateFormat("MM").format(new Date()) + "/"
                    + new SimpleDateFormat("dd").format(new Date());

            String originalFilename = uploadFile.getOriginalFilename();// 取原始文件名
            String newFileName = IDUtils.genImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));// 新文件名
            // 转存文件，上传到ftp服务器

            FtpUtil.uploadFile(FtpUtil.createFtpClient(FTP_Host, FTP_PORT, FTP_USERNAME, FTP_PASSWORD),
                    "/home/wwwroot/web1/http/source/images"+filePath,newFileName,uploadFile.getInputStream());

            result = filePath + "/" + newFileName;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
