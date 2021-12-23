package com.xiuxiuguang.niubi.pojo.office.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: Case64ToFile
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Data
public class Base64ToFileParam {
    @NotBlank
    private String base64;
    @NotBlank
    private String suffix;
}