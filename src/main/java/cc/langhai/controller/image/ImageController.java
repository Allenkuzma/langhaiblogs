package cc.langhai.controller.image;

import cc.langhai.domain.Image;
import cc.langhai.service.ImageService;
import cc.langhai.utils.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String imagePage(Model model, @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "8") Integer size, String searchImageStr) {
        // 开启分页助手
        PageHelper.startPage(page, size);
        // 获取用户所有图片
        List<Image> list = imageService.getAllImageByUser(UserContext.getUserId(), searchImageStr);
        PageInfo<Image> pageInfo = new PageInfo<>(list);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());
        model.addAttribute("search", searchImageStr);
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
