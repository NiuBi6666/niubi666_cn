package com.xiuxiuguang.niubi.service.office.impl;

import com.xiuxiuguang.niubi.config.OfficeConfig;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.pojo.office.param.Base64ToFileParam;
import com.xiuxiuguang.niubi.service.office.FileAndBase64TransService;
import com.xiuxiuguang.niubi.util.FileAndBase64TransUtil;
import com.xiuxiuguang.niubi.util.FilesNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: Base64ToPDFServiceImpl
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Service
public class FileAndBase64TransServiceImpl implements FileAndBase64TransService {

    @Autowired
    private OfficeConfig officeConfig;

    /**
     * Base64转PDF
     *
     * @param response
     * @param param
     */
    @Override
    public String base64ToFile(HttpServletResponse response, Base64ToFileParam param) {
        String fileUrl = null;
        String dir = officeConfig.getDir(param.getSuffix(), false);
        fileUrl = FilesNameUtil.getName(dir, "base64To" + param.getSuffix(), param.getSuffix());
        FileAndBase64TransUtil.decodeBase64File(param.getBase64(), fileUrl);
        return fileUrl;
    }

    @Override
    public String fileToBase64(MultipartFile file) {
        String base64 = null;
        try {
            base64 = FileAndBase64TransUtil.encodeBase64File(file);
        } catch (IOException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_FORMAT_ERROR);
        }
        return base64;
    }


}