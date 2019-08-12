package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;


@Data
@ToString
@TableName("tb_log")
public class SysLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String operation;

    private Long time;

    private String method;

    private String params;

    private String ip;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String location;

    /**
     * 用于搜索条件
     */
    private String timeField;
}