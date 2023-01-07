package cc.langhai.controller.label;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.LabelService;
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
import java.util.stream.Collectors;

/**
 * 标签 控制器
 *
 * @author langhai
 * @date 2023-01-05 11:39
 */
@Controller
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 跳转到 标签管理页面
     *
     * @return
     */
    @GetMapping("/labelPage")
    public String labelPage(HttpSession session, Model model){
        List<Label> labelList = labelService.getAllLabelByUser();

        model.addAttribute("labelList", labelList);

        return "blogs/label/labelList";
    }

    /**
     * 跳转到 标签新增页面
     *
     * @return
     */
    @GetMapping("/labelAddPage")
    public String labelAddPage(HttpSession session, Model model){


        return "blogs/label/labelAdd";
    }

    /**
     * 跳转到 标签更新页面
     *
     * @return
     */
    @GetMapping("/labelUpdatePage")
    public String labelUpdatePage(Long id, Model model, String content){

        model.addAttribute("id", id);
        model.addAttribute("content", content);
        return "blogs/label/labelUpdate";
    }

    /**
     * 获取 标签 列表页面数据
     *
     * @return
     */
    @GetMapping("/labelList")
    @ResponseBody
    public JSONObject articleList(HttpSession session, Model model) throws InterruptedException {
        JSONObject jsonObject = new JSONObject();

        List<Label> labelList = labelService.getAllLabelByUser();

        jsonObject.put("code", 0);

        jsonObject.put("data", labelList);

        jsonObject.put("count", labelList.size());

        return jsonObject;
    }

    /**
     * 新增标签
     *
     * @return
     */
    @PostMapping("/addLabel")
    @ResponseBody
    public ResultResponse addLabel(String content, Model model){
        if(StrUtil.isBlank(content)){
            return ResultResponse.fail(LabelReturnCode.LABEL_ADD_FAIL_00002);
        }
        labelService.verifyAddLabel(content);

        return ResultResponse.success(LabelReturnCode.LABEL_ADD_SUCCESS_00001);
    }


    /**
     * 删除标签
     *
     * @return
     */
    @PostMapping("/deleteLabel")
    @ResponseBody
    public ResultResponse deleteLabel(Long id, Model model){
        if(ObjectUtil.isNull(id)){
            return ResultResponse.fail(LabelReturnCode.LABEL_DELETE_FAIL_00004);
        }
        labelService.deleteLabel(id);
        return ResultResponse.success(LabelReturnCode.LABEL_DELETE_SUCCESS_00003);
    }

    /**
     * 更新标签
     *
     * @return
     */
    @PostMapping("/updateLabel")
    @ResponseBody
    public ResultResponse updateLabel(Long id, Model model, String content){
        if(ObjectUtil.isNull(id) || StrUtil.isBlank(content)){
            return ResultResponse.fail(LabelReturnCode.LABEL_UPDATE_FAIL_00006);
        }
        labelService.updateLabel(id, content);
        return ResultResponse.success(LabelReturnCode.LABEL_UPDATE_SUCCESS_00005);
    }

}
 