package com.xiuxiuguang.niubi.pojo.office.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DownloadParam
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/17     lixiuguang    Create the current class
 *******************************************************************************/
@Data
public class DownloadParam {
    @NotBlank
    private String url;
}