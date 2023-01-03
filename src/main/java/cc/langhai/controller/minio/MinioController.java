package cc.langhai.controller.minio;

import cc.langhai.minio.config.MinioProp;
import cc.langhai.minio.util.MinioUtils;
import cc.langhai.response.MinioReturnCode;
import cc.langhai.response.ResultResponse;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * minio 控制器
 *
 * @author langhai
 * @date 2023-01-02 22:24
 */
@Controller
@RequestMapping("/minio")
public class MinioController {
 
    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传图片到minio服务器系统
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
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
     * @param file
     * @return
     */
    @PostMapping("/upload/wangEditor")
    @ResponseBody
    public JSONObject uploadWangEditor(@RequestParam(name = "file", required = true) MultipartFile file, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errno", 0);
        try {
            String url = minioUtils.uploadFile(file, "product");
            StringBuffer requestURL = request.getRequestURL();
            String urlPrefix = String.valueOf(requestURL.substring(0, requestURL.length() - request.getRequestURI().length()));
            HashMap<String, Object> hashMap = MapUtil.newHashMap();
            hashMap.put("url", urlPrefix + "/minio/download?minioName=" + url);
            hashMap.put("alt", file.getOriginalFilename());
            hashMap.put("href", urlPrefix + "/minio/download?minioName=" + url);
            jsonObject.put("data", hashMap);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("errno", 1);
            jsonObject.put("message", "上传失败");
            return jsonObject;
        }

    }

    /**
     * 文件下载
     *
     * @param minioName
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    @ResponseBody
    public void downloadFile(String minioName, HttpServletResponse response) throws IOException {
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
        } catch (Exception ex) {
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
    /*@DeleteMapping("/delete")
    @ResponseBody
    public String delete(String name) {
        try {
            MinioClient minioClient = new MinioClient(minioProp.getEndpoint(), minioProp.getAccessKey(), minioProp.getSecretKey());
            minioClient.removeObject("product", name);
        } catch (Exception e) {
            return "删除失败"+e.getMessage();
        }
        return "删除成功";
    }*/

}
 