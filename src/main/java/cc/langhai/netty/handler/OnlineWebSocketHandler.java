package cc.langhai.netty.handler;

import cc.langhai.config.constant.RoleConstant;
import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.netty.domain.OnlineMessage;
import cc.langhai.service.RegisterService;
import cc.langhai.service.RoleService;
import cc.langhai.service.UserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理请求的handler
 *
 * @author langhai
 * @date 2023-03-23 18:46
 */
@Slf4j
public class OnlineWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static RegisterService registerService;

    private static RoleService roleService;

    private static UserService userService;

    static {

        registerService = SpringUtil.getBean(RegisterService.class);
        roleService = SpringUtil.getBean(RoleService.class);
        userService = SpringUtil.getBean(UserService.class);

    }

    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户名字和用户的channelId绑定
     */
    public static ConcurrentHashMap<String, ChannelId> userMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端建立连接，通道开启！");
        // 添加到channelGroup通道组
        channelGroup.add(ctx.channel());
        // ctx.channel().id();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端断开连接，通道关闭！");
        // 添加到channelGroup 通道组
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 首次连接是FullHttpRequest，把用户id和对应的channel对象存储起来
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            // 校验用户信息参数
            String uri = request.uri();
            String userName = getUrlParams(uri, "userName");
            String userPassword = getUrlParams(uri, "userPassword");
            registerService.verifyUserWebSocket(userName, userPassword);
            // 登录后把用户名字和channel关联上
            userMap.put(userName, ctx.channel().id());
            log.info("登录的用户名字是：{}", userName);
            // 如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                request.setUri(newUri);
            }

        } else if (msg instanceof TextWebSocketFrame) {
            // 正常的TEXT消息类型
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            log.info("客户端收到服务器数据：{}", frame.text());
            OnlineMessage onlineMessage = JSON.parseObject(frame.text(), OnlineMessage.class);
            // 校验用户信息参数
            registerService.verifyUserWebSocket(onlineMessage.getUserName(), onlineMessage.getUserPassword());
            // 判断用户是否为管理员
            User user = userService.getUserByUsername(onlineMessage.getUserName());
            String roleName = roleService.getRole(user.getId()).getName();
            if(!RoleConstant.ADMIN.equals(roleName)){
                List<User> adminList = roleService.getUserByRoleName(RoleConstant.ADMIN);
                if(CollUtil.isNotEmpty(adminList)){
                    for (User adminUser : adminList) {
                        onlineMessage.setAcceptName(adminUser.getUsername());
                        this.sendMessage(onlineMessage);
                    }
                }
            }else {
                // 处理私聊的任务，如果对方也在线,则推送消息
                this.sendMessage(onlineMessage);
            }
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    /**
     * 解析url中的参数
     * @return 获取用户的相关信息
     */
    private String getUrlParams(String url, String name) {
        if (!url.contains("=")) {
            return null;
        }
        String params = url.substring(url.indexOf("?") + 1, url.length());
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return split.get(name);
    }

    /**
     * 私聊发送消息
     *
     * @param onlineMessage 需要发送消息实体
     */
    public void sendMessage(OnlineMessage onlineMessage){
        ChannelId channelId = userMap.get(onlineMessage.getAcceptName());
        if (channelId != null) {
            Channel ct = channelGroup.find(channelId);
            if (ct != null) {
                ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(onlineMessage)));
            }
        }
    }

}
