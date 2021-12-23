package com.xiuxiuguang.niubi.config;

import cn.hutool.core.date.DateUtil;
import com.xiuxiuguang.niubi.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: OfficeConfig
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Component
public class OfficeConfig {


    @Value("${office.temp-url}")
    public String tempUrl;

    @Value("${office.genreate-url}")
    public String generateUrl;


    /**
     * @param suffix
     * @param fileIsFormal
     * @return
     */
    public String getDir(String suffix, Boolean fileIsFormal) {
        String dir = null;
        //是不是正式文件，true需要保存，false接口走完之后需要删除
        dir = fileIsFormal ? generateUrl + suffix + "/" + DateUtil.formatDate(DateUtil.date()) + "/" : tempUrl + suffix + "/" + DateUtil.formatDate(DateUtil.date()) + "/";
        FilesUtil.exists(dir);
        return dir;
    }
}