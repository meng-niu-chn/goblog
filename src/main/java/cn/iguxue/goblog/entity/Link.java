package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("tb_link")
public class Link implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    @NotNull
    private String name;
    private String url;
}
