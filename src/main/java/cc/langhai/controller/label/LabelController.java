package cc.langhai.controller.label;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
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

import java.util.List;

/**
 * 标签控制器
 *
 * @author langhai
 * @date 2023-01-05 11:39
 */
@Controller
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到标签管理页面
     *
     * @return
     */
    @GetMapping("/labelPage")
    public String labelPage(){

        return "blogs/label/labelList";
    }

    /**
     * 跳转到标签新增页面
     *
     * @return
     */
    @GetMapping("/labelAddPage")
    public String labelAddPage(){

        return "blogs/label/labelAdd";
    }

    /**
     * 跳转到标签更新页面
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
     * 获取标签列表页面数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/labelList")
    public JSONObject labelList(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page, limit);
        // 查询用户标签数据
        List<Label> labelList = labelService.getAllLabelByUser();
        PageInfo<Label> labelPageInfo = new PageInfo<>(labelList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", labelList);
        jsonObject.put("count", labelPageInfo.getTotal());

        return jsonObject;
    }

    /**
     * 新增标签
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/addLabel")
    public ResultResponse addLabel(String content){
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
    @ResponseBody
    @DeleteMapping("/deleteLabel")
    public ResultResponse deleteLabel(Long id){
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
    @ResponseBody
    @PostMapping("/updateLabel")
    public ResultResponse updateLabel(Long id, String content){
        if(ObjectUtil.isNull(id) || StrUtil.isBlank(content)){
            return ResultResponse.fail(LabelReturnCode.LABEL_UPDATE_FAIL_00006);
        }
        // 标签执行更新操作
        labelService.updateLabel(id, content);

        return ResultResponse.success(LabelReturnCode.LABEL_UPDATE_SUCCESS_00005);
    }

    /**
     * 跳转到用户该标签下所有的文章页面
     *
     * @return
     */
    @GetMapping("/articleLabelPage")
    public String articleLabelPage(Model model,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Long id){
        PageInfo<Article> pageInfo = labelService.article(page, size, id);
        model.addAttribute("list", articleService.getArticleHeat(pageInfo.getList()));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());
        model.addAttribute("search", id);

        Label label = labelService.getById(id);
        model.addAttribute("label", label);

        return "blogs/label/articleLabel";
    }

}
 