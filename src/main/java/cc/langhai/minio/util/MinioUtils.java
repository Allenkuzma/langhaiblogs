package cc.langhai.minio.util;

import cc.langhai.domain.Image;
import cc.langhai.domain.User;
import cc.langhai.exception.BusinessException;
import cc.langhai.minio.config.MinioProp;
import cc.langhai.response.MinioReturnCode;
import cc.langhai.service.ImageService;
import cc.langhai.service.UserService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

/**
 * minio 工具类
 *
 * @author langhai
 * @date 2023-01-02 22:20
 */
@Slf4j
@Component
public class MinioUtils {
 
    @Autowired
    private MinioClient client;

    @Autowired
    private MinioProp minioProp;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;
 
    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
    }
 
    /**
     * 上传文件
     *
     * @param file       文件
     * @param bucketName 存储桶
     * @return 上传文件结果
     */
    public String uploadFile(MultipartFile file, String bucketName) {
        // 判断上传文件是否为空
        if (ObjectUtil.isNull(file) || file.isEmpty()) {
            throw new BusinessException(MinioReturnCode.MINIO_UPLOAD_NULL_00002);
        }

        // 判断当前用户是否有图库功能
        User user = UserContext.get();
        if(!user.getImage()){
            throw new BusinessException(MinioReturnCode.MINIO_IMAGE_FAIL_00005);
        }

        imageService.size();

        try {
	       	// 判断存储桶是否存在
	        createBucket(bucketName);
	        // 文件名
	        String originalFilename = file.getOriginalFilename();
	        // 新的文件名 = 存储桶名称_时间戳.后缀名
	        String fileName = bucketName + "_" + System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
	        // 开始上传
	        client.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());

            Image image = new Image();
            image.setFileName(originalFilename);
            image.setMinioName(fileName);
            image.setAddTime(new Date());
            image.setFileSize(file.getSize());
            image.setUserId(UserContext.getUserId());
            imageService.saveImage(image);
            // 返回的格式 http://127.0.0.1:xxxx/product/product_1672718197581.jpg
            // return minioProp.getEndpoint() + "/" + bucketName + "/" + fileName;
            return fileName;
		}catch (Exception e) {
            throw new BusinessException(MinioReturnCode.MINIO_UPLOAD_FAIL_00001);
        }

    }


    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }


    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     */
    @SneakyThrows
    public void deleteFile(String bucketName, String objectName){
        if(imageService.power(objectName)){
            client.removeObject(bucketName, objectName);
            imageService.delete(objectName);
        }else {
            throw new BusinessException(MinioReturnCode.MINIO_DELETE_FAIL_00004);
        }
    }


}
 