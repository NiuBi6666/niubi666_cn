package com.xiuxiuguang.niubi.enums;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: ResponseCodeEnum
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
public enum ResponseCodeEnum {

    OK(200, "OK"),

    BAD_REQUEST_PARAM(400, "请求参数错误"),

    NOT_FOUND(404, "资源无法找到"),

    NOTARY_RESPONSE_ERROR(410, "第三方API请求失败"),

    SYSTEM_ERROR(500, "系统错误"),

    FILE_FORMAT_ERROR(502, "文件转换失败"),

    FILE_SUFFIX_ERROR(503, "上传文件格式不正确"),

    FILE_DOWNLOAD_ERROR(504, "文件下载失败"),

    TEMPLATE_NOT_FOUND_ERROR(310101, "模板未找到"),

    FILE_UPLOAD_FAIL_ERROR(310201, "文件上传失败"),


    FILE_UPLOAD_REPEAT_ERROR(310203, "请勿重复提交文件"),

    EVIDENCE_NOT_FOUND(310301, "存证函未找到"),

    ;


    private int code;
    private String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
