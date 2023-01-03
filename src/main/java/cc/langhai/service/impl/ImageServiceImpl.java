package cc.langhai.service.impl;

import cc.langhai.config.constant.ImageConstant;
import cc.langhai.domain.Image;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.ImageMapper;
import cc.langhai.response.ImageReturnCode;
import cc.langhai.service.ImageService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 图片 service接口实现类
 *
 * @author langhai
 * @date 2022-01-03 11:30
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public void size() {
        Long userId = UserContext.getUserId();
        List<Image> allImageByUser = imageMapper.getAllImageByUser(userId);

        Long sum = 0L;
        if(CollectionUtil.isNotEmpty(allImageByUser)){
            for (Image image : allImageByUser) {
                Long fileSize = image.getFileSize();
                sum += fileSize;
            }
        }

        // 默认50M
        if(sum >= ImageConstant.IMAGE_COUNT_ALL){
            throw new BusinessException(ImageReturnCode.IMAGE_UPLOAD_FAIL_00000);
        }
    }

    @Override
    public void saveImage(Image image) {
        imageMapper.insertImage(image);
    }
}
