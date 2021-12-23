package com.xiuxiuguang.niubi.service.office.impl;

import com.xiuxiuguang.niubi.config.OfficeConfig;
import com.xiuxiuguang.niubi.constant.OfficeSuffixConstant;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.service.office.FileTransService;
import com.xiuxiuguang.niubi.util.FileTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: OfficeFormatServiceImpl
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Service(value = OfficeSuffixConstant.WORD)
public class PdfToWordServiceImpl implements FileTransService {

    @Autowired
    private OfficeConfig officeConfig;

    /**
     * PDF转Word
     *
     * @param file
     * @return
     */
    @Override
    public String fileTrans(HttpServletResponse response, MultipartFile file, Integer startPage, Integer length) {
        String wordUrl = null;
        try {
            wordUrl = FileTransUtil.pdfToWord(file, officeConfig.getDir(OfficeSuffixConstant.WORD, true), startPage, length);
//            File word = new File(wordUrl);
//            FilesUtil.download(response, word);
        } catch (IOException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_FORMAT_ERROR);
        }
        return wordUrl;
    }

}