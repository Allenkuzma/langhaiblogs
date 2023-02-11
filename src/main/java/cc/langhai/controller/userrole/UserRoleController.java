package cc.langhai.controller.userrole;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserRoleReturnCode;
import cc.langhai.service.RoleService;
import cc.langhai.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户角色 控制器
 *
 * @author langhai
 * @date 2023-01-17 14:03
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到管理员给用户授权页面
     *
     * @return
     */
    @GetMapping("/userRolePage")
    @RequestAuthority(value = {"admin"})
    public String userRolePage(){

        return "blogs/userRole/userRoleList";
    }

    /**
     * 获取用户信息列表页面数据
     *
     * @return
     */
    @GetMapping("/userRoleList")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public JSONObject userRoleList(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page, limit);
        JSONObject jsonObject = new JSONObject();

        List<User> list = userService.getUserList();
        PageInfo<User> userPageInfo = new PageInfo<>(list);

        jsonObject.put("code", 0);
        jsonObject.put("data", list);
        jsonObject.put("count", userPageInfo.getTotal());
        return jsonObject;
    }

    /**
     * 跳转到更新用户授权页面
     *
     * @return
     */
    @GetMapping("/userRoleUpdatePage")
    @RequestAuthority(value = {"admin"})
    public String userRoleUpdatePage(Long id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("id", id);

        Role role = roleService.getRole(id);
        model.addAttribute("role", role);

        List<Role> list = roleService.list();
        model.addAttribute("roleList", list);
        return "blogs/userRole/userRoleUpdate";
    }

    /**
     * 更新用户授权操作
     *
     * @return
     */
    @PostMapping("/updateUserRole")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public ResultResponse updateUserRole(Long id, String name){
        if(ObjectUtil.isNull(id) || StrUtil.isBlank(name)){
            return ResultResponse.fail(UserRoleReturnCode.ROLE_AUTH_FAIL_00000);
        }

        roleService.updateUserRole(id, name);
        return ResultResponse.success(UserRoleReturnCode.ROLE_AUTH_SUCCESS_00001);
    }
}
 