package info.weifu.chao.edu_service.handler;

import info.weifu.chao.edu_common.R;
import info.weifu.chao.edu_service.exception.EduException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 所有异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R Error(Exception e) {
        e.printStackTrace();
        return R.ERROR().message("系统出异常！");
    }

    /**
     * ArithmeticException特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R Error(ArithmeticException e) {
        e.printStackTrace();
        return R.ERROR().message("0不能为除数！");
    }

    /**
     * EduException自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R Error(EduException e) {
        e.printStackTrace();
        return R.ERROR().message(e.getMessage()).code(e.getCode());
    }

}
