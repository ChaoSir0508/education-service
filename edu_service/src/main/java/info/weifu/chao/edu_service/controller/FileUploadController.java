package info.weifu.chao.edu_service.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/edu_service/oss")
@CrossOrigin
public class FileUploadController {

    /**
     * 头像上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadAvatar")
    public R uploadAvatar(@RequestParam("file") MultipartFile file) {
        return this.upload("avatar", file);
    }

    /**
     * 上传封面
     * @param file
     * @return
     */
    @PostMapping("/uploadCover")
    public R uploadCover(@RequestParam("file") MultipartFile file) {
        return this.upload("cover", file);
    }

    /**
     * 上传文件的核心
     *
     * @param type
     * @param file
     * @return
     */
    private R upload(String type, MultipartFile file) {
        //连接oss准备工作
        String endpoint = ConstantPropertiesUtils.ENDPOINT;//地域节点
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;//密钥ID
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;//密钥
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;//仓库名
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);//开启连接
        try {
            //文件路径加名称
            String filename = type + "/" + new DateTime().toString("yyyy/MM/dd") + "/" + UUID.randomUUID().toString() + file.getOriginalFilename();//设置日期格式
            //输入流
            InputStream inputStream = file.getInputStream();
            //文件上传
            ossClient.putObject(bucketName, filename, inputStream);
            //数据回显
            return R.OK().data("imageUrl", "https://" + bucketName + "." + endpoint + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ERROR();
        } finally {
            ossClient.shutdown();
        }
    }

}
