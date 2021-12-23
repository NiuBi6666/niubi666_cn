package com.xiuxiuguang.niubi.service.reptile;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DouBanService
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/9     lixiuguang    Create the current class
 *******************************************************************************/
public interface DouBanService {
    /**
     * 爬取豆瓣电影Top250
     *
     * @param excelUrl 生成Excel目录
     */
    void douBanMovieTop250(String excelUrl);
}
