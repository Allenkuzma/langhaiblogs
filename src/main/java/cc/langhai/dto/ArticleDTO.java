package cc.langhai.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

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

    @NotBlank(message = "文章摘要不能为空")
    @Length(min = 1, max = 30, message = "文章摘要长度最大为30个字符")
    private String abstractText;

    private String password;

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
