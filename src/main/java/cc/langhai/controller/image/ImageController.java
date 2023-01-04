package cc.langhai.controller.image;

import cc.langhai.domain.Article;
import cc.langhai.domain.Image;
import cc.langhai.domain.User;
import cc.langhai.service.ImageService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 图片 控制器
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
     * 跳转到 图库页面
     *
     * @return
     */
    @RequestMapping("/imagePage")
    public String imagePage(Model model, HttpServletRequest request,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "8") Integer size){
        // 开启分页助手
        PageHelper.startPage(page, size);

        Long userId = UserContext.getUserId();
        List<Image> list = imageService.getAllImageByUser(userId);
        PageInfo<Image> pageInfo = new PageInfo<>(list);
        String url = "";

        if(CollectionUtil.isNotEmpty(list)){
            for (Image image : list) {
                StringBuffer requestURL = request.getRequestURL();
                String urlPrefix = String.valueOf(requestURL.substring(0, requestURL.length() - request.getRequestURI().length()));
                url = urlPrefix + "/minio/download?minioName=" + image.getMinioName();
                image.setUrl(url);
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());
        return "blogs/image/imageList";
    }

    /**
     * 跳转到 添加图片页面
     *
     * @return
     */
    @RequestMapping("/imageAddPage")
    public String imageAddPage(Model model, HttpServletRequest request){
        String space = imageService.space();

        model.addAttribute("size", space);
        return "blogs/image/imageAdd";
    }

}
