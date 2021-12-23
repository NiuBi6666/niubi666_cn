package com.xiuxiuguang.niubi.exception.handler;

import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.exception.DynamicBusinessException;
import com.xiuxiuguang.niubi.pojo.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: GlobalExceptionHandler
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@ControllerAdvice(basePackages = "com.xiuxiuguang.niubi.controller")
public class GlobalExceptionHandler {

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class, MissingServletRequestPartException.class, BindException.class, ParseException.class})
    @ResponseBody
    public BaseResponse resolveParamValidateException(Throwable ex) {
        return BaseResponse.error(ResponseCodeEnum.BAD_REQUEST_PARAM.getCode(), ResponseCodeEnum.BAD_REQUEST_PARAM.getMsg());
    }

    /**
     * 业务逻辑异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public BaseResponse resolveException(BusinessException ex) {
        ResponseCodeEnum responseCodeEnum = ex.getCodeEnum();
        log.error("业务逻辑异常 code:{}, msg:{}", responseCodeEnum.getCode(), responseCodeEnum.getMsg());
        return BaseResponse.error(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
    }


    /**
     * 动态业务逻辑异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DynamicBusinessException.class)
    @ResponseBody
    public BaseResponse dynamicResolveException(DynamicBusinessException ex) {
        log.error("动态处理业务逻辑异常 code:{}, msg:{}", ex.getCode(), ex.getMsg());
        return BaseResponse.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 除自定义异常之外的其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse othersException(Exception ex) {
        log.error("除自定义异常之外的其他异常 msg:{}", ex.getMessage());
        ex.printStackTrace();
        return BaseResponse.error(ResponseCodeEnum.SYSTEM_ERROR.getCode(), ResponseCodeEnum.SYSTEM_ERROR.getMsg());
    }
}