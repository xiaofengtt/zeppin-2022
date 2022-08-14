package com.bbl.exception;

import com.bbl.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 业务异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Response errorBusinessHandler(BusinessException ex) {
        log.error("BusinessException ===>> ex : {}", ex);
        return Response.failResponce(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Response handleBindException(BindException e) {
        // e.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用e.getAllErrors()
        List<ObjectError> allErrors = e.getAllErrors();
        StringBuilder builder = new StringBuilder();
        builder.append(" { ");
        StringBuilder builder2 = new StringBuilder();
        builder2.append(" ");
        allErrors.forEach(ogj -> {
            FieldError ogj1 = (FieldError) ogj;
            builder.append(ogj1.getField() + ":" + ogj1.getDefaultMessage() + " ");
            builder2.append(ogj1.getDefaultMessage() + " ");
        });
        log.error("BindException == > ", e);
        return Response.failResponce(ErrorResultEnums.PARAMS_ERROR.getCode() + "", "输入的的参数不正确");
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response errorHandler(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof NumberFormatException) {
            log.error("NumberFormatException ******》》》 ex : {}", ex.getMessage());
            return Response.failResponce("7000", "输入的参数类型不是数字");
        } else if (ex instanceof org.springframework.web.method.annotation.MethodArgumentTypeMismatchException) {
            log.error("MethodArgumentTypeMismatchException ******》》》 ex : {}", ex.getMessage());
            return Response.failResponce("7001", "输入的参数类型不正确");
        } else if(ex instanceof redis.clients.jedis.exceptions.JedisConnectionException){
            log.error("JedisConnectionException ===>> ex : {}", ex.getMessage());
            return Response.failResponce("5001", "网络连接失败，请重试");
        }else if(ex instanceof org.springframework.data.redis.RedisConnectionFailureException){
            log.error("RedisConnectionFailureException ===>> ex : {}", ex.getMessage());
            return Response.failResponce("5002", "网络连接失败");
        }else{
            log.error("Exception ******》》》 ex : {}", ex);
            return Response.failResponce("10000", "系统异常");
        }


    }



}
