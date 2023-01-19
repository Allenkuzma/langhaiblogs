package cc.langhai.task;

import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.domain.Visit;
import cc.langhai.service.UserInfoService;
import cc.langhai.service.UserService;
import cc.langhai.service.VisitService;
import cc.langhai.utils.EmailUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;


/**
 * 定时任务
 *
 * @author : langhai
 * @date : 2023-01-16 17:20
 */
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 删除前七天的访问次数 23:23:23执行
     *
     */
    @Scheduled(cron = "23 23 23 * * ?")
    private void deleteVisitTask(){
        // 获取前七天的时间
        DateTime dateTime = DateUtil.offsetDay(new Date(), -7);
        String[] split = dateTime.toString().split(" ");

        List<Visit> list = visitService.list(Wrappers.<Visit>lambdaQuery()
                .ge(Visit::getTime, split[0] + " 00:00:00")
                .le(Visit::getTime, split[0] + " 23:59:59"));
        if(CollUtil.isNotEmpty(list)){
            visitService.remove(Wrappers.<Visit>lambdaQuery()
                    .ge(Visit::getTime, split[0] + " 00:00:00")
                    .le(Visit::getTime, split[0] + " 23:59:59"));
        }
    }

}