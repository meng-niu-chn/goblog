package cn.iguxue.goblog.handler;

import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public ResultVO handler(Exception e) {
        if (e instanceof GoblogException) {
            GoblogException goblogException = (GoblogException) e;
            return ResultVOUtil.error(goblogException.getCode(),goblogException.getMessage());
        } else {
            logger.info("系统异常");
            return ResultVOUtil.error(-1,"未知错误");
        }
    }

}
