package cc.langhai.service.impl;

import cc.langhai.config.constant.LabelConstant;
import cc.langhai.domain.Label;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.LabelMapper;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.service.LabelService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<Label> getAllLabelByUser() {
        Long userId = UserContext.getUserId();
        return labelMapper.getAllLabelByUser(userId);
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
        Label labelByUserAndContent = labelMapper.getLabelByUserAndContent(userId, content);
        if(ObjectUtil.isNotNull(labelByUserAndContent)){
            return labelByUserAndContent;
        }

        Label label = new Label();
        label.setUserId(userId);
        label.setAddTime(new Date());
        label.setContent(content);
        labelMapper.insertLabel(label);

        return label;
    }

}
