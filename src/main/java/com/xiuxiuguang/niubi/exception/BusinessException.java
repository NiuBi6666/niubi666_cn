package com.xiuxiuguang.niubi.exception;

import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: BusinessException
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
public class BusinessException extends RuntimeException {

    private ResponseCodeEnum codeEnum;

    public BusinessException(ResponseCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
    }

    public BusinessException(ResponseCodeEnum codeEnum, String message, Throwable cause) {
        super(message, cause);
        this.codeEnum = codeEnum;
    }

    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }
}