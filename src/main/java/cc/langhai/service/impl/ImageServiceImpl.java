package cc.langhai.service.impl;

import cc.langhai.config.constant.ImageConstant;
import cc.langhai.config.constant.RoleConstant;
import cc.langhai.domain.Image;
import cc.langhai.domain.Role;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.ImageMapper;
import cc.langhai.response.ImageReturnCode;
import cc.langhai.service.ImageService;
import cc.langhai.service.RoleService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 图片service接口实现类
 *
 * @author langhai
 * @date 2022-01-03 11:30
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public Long size() {
        // 获取用户的所有图片
        List<Image> allImageByUser = imageMapper.getAllImageByUser(UserContext.getUserId(), "");
        // 所有图片占用空间
        Long sum = 0L;
        if(CollectionUtil.isNotEmpty(allImageByUser)){
            for (Image image : allImageByUser) {
                sum += image.getFileSize();
            }
        }
        // 不同角色存储空间不同
        Role role = roleService.getRole();
        if(role.getName().equals(RoleConstant.ADMIN) || role.getName().equals(RoleConstant.VIP)){
            // 默认100M
            if(sum >= ImageConstant.IMAGE_COUNT_ADMIN_VIP_ALL){
                throw new BusinessException(ImageReturnCode.IMAGE_UPLOAD_FAIL_00000);
            }
        }else {
            // 默认50M
            if(sum >= ImageConstant.IMAGE_COUNT_ALL){
                throw new BusinessException(ImageReturnCode.IMAGE_UPLOAD_FAIL_00000);
            }
        }
        return sum;
    }

    @Override
    public void saveImage(Image image) {
        imageMapper.insertImage(image);
    }

    @Override
    public List<Image> getAllImageByUser(Long userId, String searchImageStr) {
        List<Image> imageList = imageMapper.getAllImageByUser(userId, searchImageStr);
        // 前端展示图片地址
        if (CollectionUtil.isNotEmpty(imageList)) {
            for (Image image : imageList) {
                // 图片地址带域名 需要带上域名 放开注释即可
                /*StringBuffer requestURL = request.getRequestURL();
                String urlPrefix = requestURL.substring(0, requestURL.length() - request.getRequestURI().length());
                image.setUrl(urlPrefix + "/minio/download?minioName=" + image.getMinioName());*/
                image.setUrl("/minio/download?minioName=" + image.getMinioName());
            }
        }
        // 返回用户所有图片集合
        return imageList;
    }

    @Override
    public String space() {
        Long size = this.size();
        BigDecimal result = new BigDecimal(0);
        Role role = roleService.getRole();
        if(role.getName().equals(RoleConstant.ADMIN) || role.getName().equals(RoleConstant.VIP)){
            if(size != 0L){
                result = NumberUtil.div(Long.valueOf(size), Long.valueOf(ImageConstant.IMAGE_COUNT_ADMIN_VIP_ALL), 2, RoundingMode.UP);
                result = result.multiply(new BigDecimal(100));
            }
        }else {
            if(size != 0L){
                result = NumberUtil.div(Long.valueOf(size), Long.valueOf(ImageConstant.IMAGE_COUNT_ALL), 2, RoundingMode.UP);
                result = result.multiply(new BigDecimal(100));
            }
        }
        return result.toString() + "%";
    }

    @Override
    public boolean power(String objectName) {
        if (StrUtil.isBlank(objectName)) {
            return false;
        }
        // 判断图片的拥有者
        Long userId = UserContext.getUserId();
        Image imageByMinioName = imageMapper.getImageByMinioName(objectName);
        if (ObjectUtil.isNotNull(imageByMinioName)) {
            if (imageByMinioName.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(String objectName) {
        imageMapper.deleteImage(objectName);
    }
}
