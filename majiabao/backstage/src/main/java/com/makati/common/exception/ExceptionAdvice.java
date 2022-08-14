package com.makati.common.exception;

import com.makati.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

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

//    @ExceptionHandler(value = java.lang.IllegalStateException.class)
//    public Response errorHandler(MethodArgumentTypeMismatchException ex) {
//        log.error("Exception ******》》》 ex : {}", ex);
//        return Response.failResponce("9998", "类型错误");
//    }

//    @ExceptionHandler(value = org.springframework.http.converter.HttpMessageNotReadableException.class)
//    public Response errorHandler(HttpMessageNotReadableException ex) {
//        log.error("HttpMessageNotReadableException ******》》》 ex : {}", ex.getMessage());
//        return Response.failResponce("9998", "请下载最新安装包1");
//    }
//
//    @ExceptionHandler(value = org.springframework.web.HttpMediaTypeNotSupportedException.class)
//    public Response errorHandler(HttpMediaTypeNotSupportedException ex) {
//        log.error("HttpMediaTypeNotSupportedException ******》》》 ex : {}", ex.getMessage());
//        return Response.failResponce("9998", "请下载最新安装包2");
//    }

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
        if (ex instanceof java.lang.NumberFormatException) {
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

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Response resolveConstraintViolationException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }

            return Response.failResponce("9000", errorMessage);

        }
        return Response.failResponce("9000", ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }

            return Response.failResponce("9001", errorMessage);
        }
        return Response.failResponce("9001", ex.getMessage());
    }

}
