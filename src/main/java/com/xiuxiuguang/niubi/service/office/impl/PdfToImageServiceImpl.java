package com.xiuxiuguang.niubi.service.office.impl;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.xiuxiuguang.niubi.config.OfficeConfig;
import com.xiuxiuguang.niubi.constant.OfficeSuffixConstant;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.service.office.FileTransService;
import com.xiuxiuguang.niubi.util.FileTransUtil;
import com.xiuxiuguang.niubi.util.FilesNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: PdfToImageServiceImpl
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Service(value = OfficeSuffixConstant.JPG)
public class PdfToImageServiceImpl implements FileTransService {

    @Autowired
    private OfficeConfig officeConfig;

    /**
     * PDF转换图片
     *
     * @param response
     * @param file
     */
    @Override
    public String fileTrans(HttpServletResponse response, MultipartFile file, Integer startPage, Integer length) {
        String zipFileUrl = null;
        try {
            String images = FileTransUtil.pdfToImage(file, officeConfig.getDir(OfficeSuffixConstant.JPG, true), startPage, length);
            List<String> imageUrlList = JSONUtil.toList(images, String.class);
            zipFileUrl = FilesNameUtil.getName(officeConfig.getDir(OfficeSuffixConstant.ZIP, true), "JPG", OfficeSuffixConstant.ZIP);
            File zipFile = new File(zipFileUrl);
            List<File> files = new ArrayList<>();
            for (String imageUrl : imageUrlList) {
                File imgFile = new File(imageUrl);
                files.add(imgFile);
            }
            ZipUtil.zip(zipFile, false, files.toArray(new File[files.size()]));
        } catch (IOException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_FORMAT_ERROR);
        }
        return zipFileUrl;
    }
}