package com.xiuxiuguang.niubi.exception.handler;

import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.pojo.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: NotFoundExceptionHandler
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Controller
public class NotFoundExceptionHandler implements ErrorController {
    //@Override
    //public String getErrorPath() {
    //    return null;
    //}

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public BaseResponse notFoundError(HttpServletRequest request) {
        log.error("NOT_FOUND异常 code:{}, msg:{}", 404, "资源无法找到");
        return BaseResponse.error(ResponseCodeEnum.NOT_FOUND);
    }
}