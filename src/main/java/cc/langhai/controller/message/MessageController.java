package cc.langhai.controller.message;

import cc.langhai.domain.Message;
import cc.langhai.response.MessageReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.MessageService;
import cc.langhai.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 反馈信息 控制器
 *
 * @author : langhai
 * @date : 2023/01/07 15:48
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    /**
     * 反馈信息提交
     *
     * @return
     */
    @PostMapping("/message")
    @ResponseBody
    public ResultResponse message(HttpServletRequest request,@RequestBody @Validated Message message){

        messageService.save(message, request);

        return ResultResponse.success(MessageReturnCode.MESSAGE_SAVE_SUCCESS_00001);
    }
}
