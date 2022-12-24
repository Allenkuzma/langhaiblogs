package cc.langhai.service.impl;

import cc.langhai.domain.Label;
import cc.langhai.mapper.LabelMapper;
import cc.langhai.service.LabelService;
import cc.langhai.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章标签 service实体类
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

}
