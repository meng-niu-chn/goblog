package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tb_user_role")
@NoArgsConstructor
public class UserRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uId;

    private Integer rId;

    public UserRole(Long uid, Integer rId) {
        this.uId = uid;
        this.rId = rId;
    }
}
