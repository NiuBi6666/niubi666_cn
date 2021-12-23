package com.xiuxiuguang.niubi.pojo.base;

import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: BaseResponse
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Data
public class BaseResponse implements Serializable {
    public Integer code;
    public String message;
    public Object data;


    public static BaseResponse success(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        return baseResponse;
    }

    public static BaseResponse success() {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("succeed");
        return response;
    }

    public static BaseResponse error(Integer code, String message) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }


    public static BaseResponse error(ResponseCodeEnum responseCodeEnum) {
        BaseResponse response = new BaseResponse();
        response.setCode(responseCodeEnum.getCode());
        response.setMessage(responseCodeEnum.getMsg());
        return response;
    }

    public BaseResponse() {
        this.code = 200;
        this.message = "succeed";
    }
}