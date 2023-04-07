package cc.langhai.service.impl;

import cc.langhai.config.constant.LabelConstant;
import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.ArticleMapper;
import cc.langhai.mapper.LabelMapper;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.service.ArticleService;
import cc.langhai.service.LabelService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文章标签 service实现类
 *
 * @author langhai
 * @date 2022-12-24 10:51
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Label> getAllLabelByUser() {
        Long userId = UserContext.getUserId();

        return labelMapper.getAllLabelByUser(userId);
    }

    @Override
    public List<String> getAllLabelContentByUser() {
        List<Label> labelList = this.getAllLabelByUser();
        // collect 用来存储文章标签的内容
        List<String> collect = labelList.stream().map(label -> label.getContent()).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Label verifyAddLabel(String content) {
        Long userId = UserContext.getUserId();
        // 先判断标签数量是否超过上限
        List<Label> allLabelByUser = labelMapper.getAllLabelByUser(userId);
        if(LabelConstant.LABEL_COUNT_ALL <= allLabelByUser.size()){
            throw new BusinessException(LabelReturnCode.LABEL_COUNT_FAIL_00000);
        }

        // 判断新增的标签是否存在
        Label labelOld = this.labelExist(content);
        if(ObjectUtil.isNotNull(labelOld)){
            return labelOld;
        }

        Label label = new Label();
        label.setUserId(userId);
        label.setAddTime(new Date());
        label.setContent(content);
        labelMapper.insertLabel(label);

        return label;
    }

    @Override
    public void deleteLabel(Long id) {
        // 标签是否有权限操作
        Label label = this.labelPermission(id);

        // 判断标签下是否存在文章
        List<Label> labelList = labelMapper.selectArticleByLabel(label);
        if(CollectionUtil.isNotEmpty(labelList)){
            throw new BusinessException(LabelReturnCode.LABEL_DELETE_FAIL_00004);
        }

        labelMapper.deleteLabel(label);
    }

    @Override
    public void updateLabel(Long id, String content) {
        // 标签是否有权限操作
        Label label = this.labelPermission(id);

        // 判断更新的标签是否存在
        Label labelExist = this.labelExist(content);
        if(ObjectUtil.isNotNull(labelExist)){
            throw new BusinessException(LabelReturnCode.LABEL_UPDATE_FAIL_00006);
        }

        label.setContent(content);
        label.setUpdateTime(new Date());
        labelMapper.updateLabel(label);
    }

    @Override
    public PageInfo<Article> article(Integer page, Integer size, Long id) {
        Long userId = UserContext.getUserId();

        // 判断标签是否有权限操作
        Label label = labelMapper.getLabelById(id);
        if(ObjectUtil.isNull(label)){
            throw new BusinessException(LabelReturnCode.LABEL_ARTICLE_FAIL_00007);
        }

        if(!label.getUserId().equals(userId)){
            throw new BusinessException(LabelReturnCode.LABEL_ARTICLE_FAIL_00007);
        }

        // 开启分页助手
        PageHelper.startPage(page, size);
        List<Article> articleList = labelMapper.article(id);
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return pageInfo;
    }

    @Override
    public Label getById(Long id) {
        Label label = labelMapper.getLabelById(id);
        return label;
    }

    @Override
    public Label labelPermission(Long id) {
        Long userId = UserContext.getUserId();
        // 判断标签是否有权限操作
        Label label = labelMapper.getLabelById(id);
        if(ObjectUtil.isNull(label)){
            throw new BusinessException(LabelReturnCode.LABEL_PARAM_FAIL_00008);
        }

        if(!label.getUserId().equals(userId)){
            throw new BusinessException(LabelReturnCode.LABEL_PERMISSION_FAIL_00009);
        }

        return label;
    }

    @Override
    public Label labelExist(String content) {
        Label labelByUserAndContent = labelMapper.getLabelByUserAndContent(UserContext.getUserId(), content);

        return labelByUserAndContent;
    }

    @Override
    public Set<Label> getLabelPublicShow() {
        HashSet<Label> labelHashSet = new HashSet<>();
        // 获取公开的文章
        List<Article> articleList = articleMapper.getAllArticlePublicShow("", null);
        if(CollectionUtil.isNotEmpty(articleList)){
            for (Article article : articleList) {
                Long labelId = article.getLabelId();
                String labelContent = article.getLabelContent();
                Label label = new Label();
                label.setId(labelId);
                label.setContent(labelContent);
                labelHashSet.add(label);
            }
        }

        return labelHashSet;
    }

}
