package cc.langhai.controller.visit;

import cc.langhai.domain.User;
import cc.langhai.domain.Visit;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.VisitReturnCode;
import cc.langhai.service.UserService;
import cc.langhai.service.VisitService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
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

    @Autowired
    private UserService userService;

    /**
     * 获取最近七天的用户访问记录
     *
     * @return 最近七天的用户访问记录
     */
    @ResponseBody
    @GetMapping("/day")
    public ResultResponse day() {
        HashMap<String, Integer> map = new LinkedHashMap<>(2);
        // 获取前七天的时间
        for (int i = 6; i >= 0; i--) {
            DateTime dateTime = DateUtil.offsetDay(new Date(), -i);
            String[] split = dateTime.toString().split(" ");
            List<Visit> list = visitService.list(Wrappers.<Visit>lambdaQuery()
                    .ge(Visit::getTime, split[0] + " 00:00:00")
                    .le(Visit::getTime, split[0] + " 23:59:59"));
            map.put(split[0], list.size());
        }
        return ResultResponse.success(VisitReturnCode.VISIT_DAY_SUCCESS_00000, map);
    }

    /**
     * 用户访问记录设备类型
     *
     * @return 用户访问记录设备类型
     */
    @ResponseBody
    @GetMapping("/device")
    public ResultResponse device() {
        HashMap<String, Integer> map = new HashMap<>();
        String[] nowDayScope = cc.langhai.utils.DateUtil.getNowDayScope();
        List<Visit> list = visitService.list(Wrappers.<Visit>lambdaQuery()
                .ge(Visit::getTime, nowDayScope[0])
                .le(Visit::getTime, nowDayScope[1]));
        if (CollUtil.isNotEmpty(list)) {
            for (Visit visit : list) {
                String userAgent = visit.getUserAgent();
                UserAgent ua = UserAgentUtil.parse(userAgent);
                String name = ua.getPlatform().getName();
                if (map.containsKey(name)) {
                    Integer integer = map.get(name);
                    map.put(name, integer + 1);
                } else {
                    map.put(name, 1);
                }
            }
        }
        return ResultResponse.success(VisitReturnCode.DEVICE_DAY_SUCCESS_00001, map);
    }

    /**
     * 后台管理数据，今日访问，总注册人数。
     *
     * @return 后台管理数据
     */
    @ResponseBody
    @GetMapping("/data")
    public ResultResponse data() {
        String[] nowDayScope = cc.langhai.utils.DateUtil.getNowDayScope();
        Integer count = visitService.count(Wrappers.<Visit>lambdaQuery()
                .ge(Visit::getTime, nowDayScope[0])
                .le(Visit::getTime, nowDayScope[1]));
        // 总注册人数
        List<User> userList = userService.getUserList("", "");
        // 返回结果
        ArrayList<Integer> result = new ArrayList<>(4);
        result.add(count);
        result.add(userList.size());
        return ResultResponse.success(VisitReturnCode.ADMIN_DATA_SUCCESS_00002, result);
    }
}
