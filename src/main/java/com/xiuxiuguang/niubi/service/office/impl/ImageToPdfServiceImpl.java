package com.xiuxiuguang.niubi.service.office.impl;

import com.itextpdf.text.DocumentException;
import com.xiuxiuguang.niubi.config.OfficeConfig;
import com.xiuxiuguang.niubi.constant.OfficeSuffixConstant;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.service.office.FileTransService;
import com.xiuxiuguang.niubi.util.FileTransUtil;
import com.xiuxiuguang.niubi.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: ImageToPdfServiceImpl
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/22     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Service(value = OfficeSuffixConstant.PDF)
public class ImageToPdfServiceImpl implements FileTransService {

    @Autowired
    private OfficeConfig officeConfig;

    /**
     * 文件转换
     *
     * @param response
     * @param file
     * @param startPage
     * @param length
     */
    @Override
    public String fileTrans(HttpServletResponse response, MultipartFile multipartFile, Integer startPage, Integer length) {
        File file = null;
        String generateFileUrl = null;
        try {
            file = FileTransUtil.multipartFileToFile(multipartFile);
            generateFileUrl = FileTransUtil.imgToPdf(file, officeConfig.getDir(OfficeSuffixConstant.PDF, true));
            log.info(generateFileUrl);
            FilesUtil.deleteTempFile(file);
        } catch (IOException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_FORMAT_ERROR);
        } catch (DocumentException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_FORMAT_ERROR);
        }
        return generateFileUrl;
    }
}