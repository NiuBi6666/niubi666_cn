package com.xiuxiuguang.niubi.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.CharsetUtil;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FilesUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
public class FilesUtil {


    /**
     * 使用HttpServletResponse创建下载文件接口
     *
     * @param response
     * @param file
     * @throws IOException
     */
    public static void download(HttpServletResponse response, File file) throws IOException {
        byte[] bytes = FileUtil.readBytes(file);
        String fileName = FileNameUtil.mainName(file) + "." + FileNameUtil.getSuffix(file);
        //获取输出流
        OutputStream stream = response.getOutputStream();
        try {
            //清空下载文件的空白行（空白行是因为有的前端代码编译后产生的）
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            //设置响应头，把文件名字设置好
            response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, CharsetUtil.UTF_8));
            //输出流开始写出文件
            stream.write(bytes);
            //刷新流
            stream.flush();
        } finally {
            //关闭流
            stream.close();
        }
    }


    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }


    /**
     * 查看目录是否存在，若不存在就新建个目录
     */
    public static void exists(String dirUrl) {
        //文件存储路径
        File pfile = new File(dirUrl);
        //判断文件夹是否存在
        if (!pfile.exists()) {
            log.info("{}文件夹不存在，新建-成功", dirUrl);
            //不存在时,创建文件夹
            boolean mkdirs = pfile.mkdirs();
            if (!mkdirs) {
                log.error("新建文件夹：{}失败", dirUrl);
                throw new BusinessException(ResponseCodeEnum.FILE_UPLOAD_FAIL_ERROR);
            }
        }
    }


    /**
     * 删除文件
     *
     * @param fileUrl
     * @return
     */
    public static Boolean deleteFile(String fileUrl) {
        boolean del = FileUtil.del(fileUrl);
        log.info("删除{}文件-{}", fileUrl, del ? "成功" : "失败");
        return del;
    }

}