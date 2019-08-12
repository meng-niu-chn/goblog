package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author TyCoding
 * @date 2018/10/17
 */
@Data
@TableName("tb_comment")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pId;
    private Long cId;
    private String articleTitle;
    private Long articleId;
    @NotNull
    private String name;
    private String cName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private String content;
    private String email;
    private String url;
    private Long type;
    private String ip;
    private String device;
    private String address;
}
