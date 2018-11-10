import com.taotao.common.utils.FtpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-10 17:52
 **/
public class UploadTest {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\juhua.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        FtpUtil.uploadFile(FtpUtil.createFtpClient("192.168.0.201", 21, "web1", "123456"),
                "/home/wwwroot/web1/http/source/images/123","newhuhua.jpg",fileInputStream);
    }
}
