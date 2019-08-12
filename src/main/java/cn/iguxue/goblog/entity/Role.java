package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tb_role")
@NoArgsConstructor
public class Role {

    @TableId(type = IdType.AUTO)
    Integer id;

    String role;

    Role(String role) {
        this.role = role;
    }

}
