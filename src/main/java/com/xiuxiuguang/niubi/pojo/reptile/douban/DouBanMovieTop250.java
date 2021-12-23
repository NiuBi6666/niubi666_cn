package com.xiuxiuguang.niubi.pojo.reptile.douban;

import lombok.Data;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DouBanMovieTop250
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/9     lixiuguang    Create the current class
 *******************************************************************************/
@Data
public class DouBanMovieTop250 {
    //电影名
    private String movieName;
    //电影详情
    private String movieInfo;
    //电影引用
    private String movieQuote;
    //电影评分
    private String movieScore;
}