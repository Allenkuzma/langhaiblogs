package cc.langhai.controller.devlog;

import cc.langhai.domain.DevLog;
import cc.langhai.domain.Label;
import cc.langhai.service.ArticleService;
import cc.langhai.service.DevLogService;
import cc.langhai.service.LabelService;
import cc.langhai.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 开发日志记录控制器
 *
 * @author langhai
 * @date 2022-01-16 18:44
 */
@Controller
@RequestMapping("/devLog")
public class DevLogController {

    @Autowired
    private DevLogService devLogService;

    /**
     * 跳转到 开发日志记录页面
     *
     * @return
     */
    @GetMapping("/devLogPage")
    public String devLogPage(Model model){
        List<DevLog> list = devLogService.list(Wrappers.<DevLog>lambdaQuery().orderByAsc(DevLog::getAddTime));
        model.addAttribute("list", list);
        return "blogs/devLog/devLogList";
    }


}
