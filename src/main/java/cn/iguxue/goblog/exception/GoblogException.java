package cn.iguxue.goblog.exception;

import cn.iguxue.goblog.enums.ResultEnum;
import lombok.Getter;
import org.apache.catalina.filters.RequestDumperFilter;

@Getter
public class GoblogException extends RuntimeException {

    private Integer code;

    public GoblogException(Integer code) {
        this.code = code;
    }

    public GoblogException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
