package com.xiuxiuguang.niubi.util;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: PagesUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
public class PagesUtil {
    public static Integer getStartPage(Integer startPage, Integer pageTotal) {
        //起始页只需要大于0并且比总页数小即可
        //如果是1的话转为0
        return startPage < 0 ? 0 : (startPage == 1 ? 0 : (startPage >= pageTotal ? pageTotal : startPage));
    }

    public static Integer getPageLength(Integer startPage, Integer length, Integer pageTotal) {
        //length要大于0，小于总页数减起始页
        return length < 0 ? (pageTotal - getStartPage(startPage, pageTotal)) : (length >= (pageTotal - getStartPage(startPage, pageTotal)) ? (pageTotal - getStartPage(startPage, pageTotal)) : length);
    }
}