package cc.langhai.controller.message;

import cc.langhai.domain.Message;
import cc.langhai.response.MessageReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 反馈信息控制器
 *
 * @author langhai
 * @date 2023-01-07 15:48
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 反馈信息提交
     *
     * @return 反馈信息提交结果
     */
    @ResponseBody
    @PostMapping("/message")
    public ResultResponse message(HttpServletRequest request, @RequestBody @Validated Message message){
        messageService.save(message, request);

        return ResultResponse.success(MessageReturnCode.MESSAGE_SAVE_SUCCESS_00001);
    }

}
