package com.xiuxiuguang.niubi.service.office;

import com.xiuxiuguang.niubi.pojo.office.param.Base64ToFileParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileAndBase64TransService
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
public interface FileAndBase64TransService {
    /**
     * Base64转文件
     *
     * @param response
     * @param param
     */
    String base64ToFile(HttpServletResponse response, Base64ToFileParam param);

    /**
     * 文件转Base64
     *
     * @param file
     * @return
     */
    String fileToBase64(MultipartFile file);
}
