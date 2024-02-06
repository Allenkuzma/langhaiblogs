package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 首页超链接管理
 *
 * @author langhai
 * @date 2022-01-12 13:04
 */
@Data
@TableName("links")
public class Links implements Comparable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    @NotBlank(message = "h3标签内容不能为空")
    private String title;

    @TableField("href")
    @NotBlank(message = "超链接地址不能为空")
    private String href;

    @TableField("name")
    @NotBlank(message = "网站名字不能为空")
    private String name;

    @TableField("description")
    @NotBlank(message = "网站描述不能为空")
    private String description;

    @TableField("sort")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    @Override
    public int compareTo(Object o) {
        if(o instanceof Links){
            Links links = (Links) o;
            if(Long.compare(Long.valueOf(this.getSort()), Long.valueOf(links.getSort())) == 0){
                return Long.compare(this.getId(), links.getId());
            }else {
                return Long.compare(Long.valueOf(this.getSort()), Long.valueOf(links.getSort()));
            }
        }
        return 0;
    }
}
