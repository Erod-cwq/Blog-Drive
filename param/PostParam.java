package com.example.jpa_learn.param;

import com.example.jpa_learn.dto.InputConverter;
import com.example.jpa_learn.entity.Post;
import com.example.jpa_learn.entity.enums.PostEditorType;
import com.example.jpa_learn.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
public class PostParam implements InputConverter<Post> {
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 100, message = "文章标题的字符长度不能超过 {max}")
    private String title;

    @Size(max = 255, message = "文章别名的字符长度不能超过 {max}")
    private String slug;


    private String originalContent;

    private String summary;

    @Size(max = 1023, message = "封面图链接的字符长度不能超过 {max}")
    private String thumbnail;

    private Boolean disallowComment = false;

    @Size(max = 255, message = "文章密码的字符长度不能超过 {max}")
    private String password;

    @Size(max = 255, message = "Length of template must not be more than {max}")
    private String template;

    @Min(value = 0, message = "Post top priority must not be less than {value}")
    private Integer topPriority = 0;

    private Date createTime;

    private String metaKeywords;

    private String metaDescription;

    private Set<Integer> tagIds;

    private Set<Integer> pictureIds;

    private Set<Integer> categoryIds;

    private PostEditorType editorType;

    @Override
    public Post convertTo() {

        if (null == thumbnail) {
            thumbnail = "";
        }

        if(null == createTime){
            createTime = DateUtils.now();
        }

        if (null == editorType) {
            editorType = PostEditorType.MARKDOWN;
        }

        return InputConverter.super.convertTo();
    }


}
