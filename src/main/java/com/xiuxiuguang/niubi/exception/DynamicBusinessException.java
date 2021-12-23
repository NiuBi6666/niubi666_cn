package com.xiuxiuguang.niubi.exception;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DynamicBusinessException
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
public class DynamicBusinessException extends RuntimeException {
    private Integer code;
    private String msg;

    public DynamicBusinessException(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public DynamicBusinessException() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}