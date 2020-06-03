package com.wxj.servicebase.ExceptionHandler;



import com.wxj.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
   /* //全局异常
   @ExceptionHandler(Exception.class)
    @ResponseBody //为了能够返回数据
    public R error(Exception e){
       e.fillInStackTrace();
        return R.error().message("执行全局11异常处理");
    }*/
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了能够返回数据
    public R ArithmeticException(Exception e){
        e.fillInStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }
      //自定异常
    @ExceptionHandler(wxjException.class)
    @ResponseBody //为了能够返回数据
    public R wxjException(wxjException e){
        log.error(e.getMessage());
        e.fillInStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
