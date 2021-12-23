package com.xiuxiuguang.niubi.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileAndBase64TransUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
public class FileAndBase64TransUtil {


    /**
     * 文件转化成base64字符串，将文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param fileUrl 待处理的文件
     * @return
     */
    public static String encodeBase64File(String fileUrl) {
        byte[] readBytes = FileUtil.readBytes(fileUrl);
        //返回Base64编码过的字节数组字符串
        return Base64.encode(readBytes);
    }


    public static String encodeBase64File(MultipartFile multipartFile) throws IOException {
        File file = FileTransUtil.multipartFileToFile(multipartFile);
        byte[] readBytes = FileUtil.readBytes(file);
        //返回Base64编码过的字节数组字符串
        String encode = Base64.encode(readBytes);
        FilesUtil.deleteTempFile(file);
        return encode;
    }

    /**
     * base64字符串转化成文件，对字节数组字符串进行Base64解码并生成文件
     *
     * @param base64FileStr
     * @param newFileUrl
     */
    public static void decodeBase64File(String base64FileStr, String newFileUrl) {
        byte[] decode = Base64.decode(base64FileStr);
        FileUtil.writeBytes(decode, newFileUrl);
        log.info("base64字符串转化成文件成功：{}", newFileUrl);
    }
}