package cc.langhai.controller.minio;

import cc.langhai.domain.Image;
import cc.langhai.domain.User;
import cc.langhai.exception.BusinessException;
import cc.langhai.minio.util.MinioUtils;
import cc.langhai.response.ImageReturnCode;
import cc.langhai.response.MinioReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.ReturnCode;
import cc.langhai.service.ImageService;
import cc.langhai.service.UserService;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * minio控制器
 *
 * @author langhai
 * @date 2023-01-02 22:24
 */
@Controller
@RequestMapping("/minio")
public class MinioController {
 
    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    /**
     * 上传图片到minio服务器系统
     *
     * @param file 上传的文件
     * @return 上传图片的结果
     */
    @ResponseBody
    @PostMapping("/upload")
    public ResultResponse upload(@RequestParam(name = "file", required = true) MultipartFile file) {
        try {
            String url = minioUtils.uploadFile(file, "product");
            return ResultResponse.success(MinioReturnCode.MINIO_UPLOAD_OK_00000, url);
        } catch (Exception e) {
            return ResultResponse.fail(MinioReturnCode.MINIO_UPLOAD_FAIL_00001);
        }
    }


    /**
     * 上传图片到minio服务器系统 给富文本编辑器使用
     *
     * @param file 上传的文件
     * @return 上传图片的结果
     */
    @ResponseBody
    @PostMapping("/upload/wangEditor")
    public JSONObject uploadWangEditor(@RequestParam(name = "file", required = true) MultipartFile file, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errno", 0);
        try {
            String url = minioUtils.uploadFile(file, "product");
            HashMap<String, Object> hashMap = MapUtil.newHashMap();
            hashMap.put("url", "/minio/download?minioName=" + url);
            hashMap.put("alt", file.getOriginalFilename());
            hashMap.put("href", "/minio/download?minioName=" + url);
            jsonObject.put("data", hashMap);
            jsonObject.put("message", "上传图片成功。");
        } catch (Exception e) {
            if(e instanceof BusinessException){
                BusinessException businessException = (BusinessException) e;
                ReturnCode returnCode = businessException.getReturnCode();
                jsonObject.put("message", returnCode.getMessage());
            }else {
                jsonObject.put("message", "上传图片失败。");
            }
        }

        return jsonObject;
    }

    /**
     * 文件下载
     *
     * @param minioName minio存储的文件名字
     * @param response http响应
     * @throws IOException io异常
     */
    @ResponseBody
    @GetMapping("/download")
    public void downloadFile(String minioName, HttpServletResponse response) throws IOException {
        Image image = imageService.getOne(Wrappers.<Image>lambdaQuery()
                .eq(Image::getMinioName, minioName));
        if(ObjectUtil.isNull(image)){
            throw new BusinessException(ImageReturnCode.IMAGE_PARAM_FAIL_00001);
        }
        // 查询图片用户的启用状态
        User user = userService.getUserById(image.getUserId());
        if(ObjectUtil.isNotNull(user)){
            if(Boolean.valueOf(false).equals(user.getEnable())){
                throw new BusinessException(ImageReturnCode.IMAGE_ENABLE_FAIL_00002);
            }
        }
        if (StringUtils.isBlank(minioName)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
            // 关闭输出流
            if(ObjectUtil.isNotNull(ps)){
                ps.close();
            }
            return;
        }
        try {
            // 获取文件对象
            InputStream object = minioUtils.getObject("product", minioName);
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            // response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8"));
            // response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            // 关闭输出流
            if(ObjectUtil.isNotNull(outputStream)){
                outputStream.close();
            }
        } catch (Exception exception) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
            // 关闭输出流
            if(ObjectUtil.isNotNull(ps)){
                ps.close();
            }
        }
    }


    /**
     * 文件删除
     *
     * @param name
     * @return
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public ResultResponse delete(String name) {
        try {
            minioUtils.deleteFile("product", name);
        } catch (Exception e) {
            return ResultResponse.fail(MinioReturnCode.MINIO_DELETE_FAIL_00004);
        }

        return ResultResponse.success(MinioReturnCode.MINIO_DELETE_OK_00003);
    }

}
 