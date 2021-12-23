package com.xiuxiuguang.niubi.service.office;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileTransService
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
public interface FileTransService {

    /**
     * 文件转换
     *
     * @param response
     * @param file
     * @param startPage
     * @param length
     */
    String fileTrans(HttpServletResponse response, MultipartFile file, Integer startPage, Integer length);


}
