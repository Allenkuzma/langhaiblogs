package cc.langhai.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 文章实体类 传输对象DTO
 *
 * @author langhai
 * @date 2022-01-14 16:07
 */
@Data
public class ArticleDTO implements Serializable {

    private Long id;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String html;

    /**
     * 新增文章标签
     *
     */
    private String content;

    /**
     * 使用存在的文章标签
     *
     */
    private String label;

    /**
     * 文章是否公开： 公开/不公开
     *
     */
    private String publicShow;
}
