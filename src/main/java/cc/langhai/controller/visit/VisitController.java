package cc.langhai.controller.visit;

import cc.langhai.domain.Visit;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.VisitReturnCode;
import cc.langhai.service.VisitService;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 用户访问记录控制器
 *
 * @author langhai
 * @date 2023-01-19 21:09
 */
@Controller
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/day")
    @ResponseBody
    public ResultResponse day(){
        HashMap<String, String> map = new LinkedHashMap<>(2);

        // 获取前七天的时间
        for (int i = 6; i >= 0; i--) {
            DateTime dateTime = DateUtil.offsetDay(new Date(), -i);
            String[] split = dateTime.toString().split(" ");

            List<Visit> list = visitService.list(Wrappers.<Visit>lambdaQuery()
                    .ge(Visit::getTime, split[0] + " 00:00:00")
                    .le(Visit::getTime, split[0] + " 23:59:59"));

            map.put(split[0], String.valueOf(list.size()));
        }

        return ResultResponse.success(VisitReturnCode.VISIT_DAY_SUCCESS_00000, map);
    }
}
