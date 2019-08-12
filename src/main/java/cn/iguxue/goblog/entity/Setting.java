package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_setting")
public class Setting implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String siteName;

    private Object siteLinks;

    private Object siteDonation;

    private String siteMusic;
    private String about;

    private String aboutMd;
}
