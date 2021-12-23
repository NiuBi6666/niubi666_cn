package com.xiuxiuguang.niubi.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileNameUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
public class FilesNameUtil {

    public static String getName(String dir, String mainName, String suffix) {
        return dir + mainName + "_" + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_PATTERN) + "." + suffix;
    }
}