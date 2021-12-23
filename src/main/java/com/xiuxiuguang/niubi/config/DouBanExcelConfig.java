package com.xiuxiuguang.niubi.config;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DouBanExcelConfig
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/9     lixiuguang    Create the current class
 *******************************************************************************/
@Data
public class DouBanExcelConfig {


    /**
     * 表头
     */
    public String content = "豆瓣电影Top250";
    /**
     * 列名
     */
    public Map<String, String> headerAlias = new LinkedHashMap<>();
    /**
     * lastColumn
     */
    public Integer lastColumn = 3;

    {
        headerAlias.put("movieName", "电影名");
        headerAlias.put("movieInfo", "电影详情");
        headerAlias.put("movieQuote", "电影引用");
        headerAlias.put("movieScore", "电影评分");
    }

//    public DouBanExcelConfig() {
//        content = "豆瓣电影Top250";
//        lastColumn = 3;
//        headerAlias.put("movieName", "电影名");
//        headerAlias.put("movieInfo", "电影详情");
//        headerAlias.put("movieQuote", "电影引用");
//        headerAlias.put("movieScore", "电影评分");
//    }
}