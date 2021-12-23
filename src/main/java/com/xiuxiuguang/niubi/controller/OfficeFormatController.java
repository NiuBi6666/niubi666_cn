package com.xiuxiuguang.niubi.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import com.xiuxiuguang.niubi.factory.FileTransFactory;
import com.xiuxiuguang.niubi.pojo.base.BaseResponse;
import com.xiuxiuguang.niubi.pojo.office.param.Base64ToFileParam;
import com.xiuxiuguang.niubi.service.office.FileAndBase64TransService;
import com.xiuxiuguang.niubi.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: OfficeFormatController
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Validated
@RequestMapping("/office/format")
@RestController
public class OfficeFormatController {

    @Autowired
    private FileTransFactory fileTransFactory;

    @Autowired
    private FileAndBase64TransService fileAndBase64TransService;

    @GetMapping(value = "/hello")
    public BaseResponse hello() {
        return BaseResponse.success("现在是北京时间：" + DateUtil.now());
    }


    @PostMapping(value = "/fileTrans")
    public BaseResponse fileTrans(HttpServletResponse response,
                                  @RequestParam(name = "file") MultipartFile file,
                                  @RequestParam(name = "suffix") @NotBlank String suffix,
                                  @RequestParam(name = "startPage") String startPage,
                                  @RequestParam(name = "length") String length) {
        return BaseResponse.success(fileTransFactory.get(suffix).fileTrans(response, file, StrUtil.isBlank(startPage) ? 0 : Integer.valueOf(startPage), StrUtil.isBlank(length) ? Integer.MAX_VALUE : Integer.valueOf(length)));
    }

    @PostMapping(value = "/base64ToFile")
    public BaseResponse base64ToFile(HttpServletResponse response, @RequestBody @Validated Base64ToFileParam param) {
        return BaseResponse.success(fileAndBase64TransService.base64ToFile(response, param));
    }


    @PostMapping(value = "/fileToBase64")
    public BaseResponse fileToBase64(@RequestParam(name = "file") MultipartFile file) {
        return BaseResponse.success(fileAndBase64TransService.fileToBase64(file));
    }

    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, @RequestParam(name = "url") @NotBlank String url) {
        try {
            FilesUtil.download(response, new File(url));
        } catch (IOException e) {
            throw new BusinessException(ResponseCodeEnum.FILE_DOWNLOAD_ERROR);
        }
    }

}