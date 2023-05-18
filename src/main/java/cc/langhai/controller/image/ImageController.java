package cc.langhai.controller.image;

import cc.langhai.domain.Image;
import cc.langhai.service.ImageService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片控制器
 *
 * @author langhai
 * @date 2023-01-04 10:20
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 跳转到图库页面
     *
     * @return 图库页面
     */
    @RequestMapping("/imagePage")
    public String imagePage(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "8") Integer size){
        // 开启分页助手
        PageHelper.startPage(page, size);
        // 获取用户所有图片
        List<Image> list = imageService.getAllImageByUser(UserContext.getUserId());
        PageInfo<Image> pageInfo = new PageInfo<>(list);
        // 前端展示图片地址
        if(CollectionUtil.isNotEmpty(list)){
            for (Image image : list) {
                // 图片地址带域名 需要带上域名 放开注释即可
                /*StringBuffer requestURL = request.getRequestURL();
                String urlPrefix = requestURL.substring(0, requestURL.length() - request.getRequestURI().length());
                image.setUrl(urlPrefix + "/minio/download?minioName=" + image.getMinioName());*/
                image.setUrl("/minio/download?minioName=" + image.getMinioName());
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());

        return "blogs/image/imageList";
    }

    /**
     * 跳转到添加图片页面
     *
     * @return 添加图片页面
     */
    @RequestMapping("/imageAddPage")
    public String imageAddPage(Model model){
        model.addAttribute("size", imageService.space());

        return "blogs/image/imageAdd";
    }

}
