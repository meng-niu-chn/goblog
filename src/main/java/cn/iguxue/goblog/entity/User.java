package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("tb_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String salt;
    private String avatar;
    private String introduce;
    private String remark;

    @TableField(exist = false)
    private String role;

//    @TableField(exist = false)
//    private String checkPass;
}
