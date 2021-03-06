package com.xiuxiuguang.niubi.service.reptile.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.xiuxiuguang.niubi.config.DouBanExcelConfig;
import com.xiuxiuguang.niubi.pojo.reptile.douban.DouBanMovieTop250;
import com.xiuxiuguang.niubi.service.reptile.DouBanService;
import com.xiuxiuguang.niubi.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DouBan
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/8     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Service
public class DouBanServiceImpl implements DouBanService {

    private static final String BASE_URL = "https://movie.douban.com/top250?start=";
    private static final Integer PAGE_TOTAL = 10;
    private static final Integer PAGE_SIZE = 25;

    private static Map<String, String> HEADER = new HashMap<>();

    static {
        HEADER.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
    }

    private String askURL(String url) {
        HttpRequest httpRequest = HttpUtil.createGet(url);
        httpRequest.addHeaders(HEADER);
        HttpResponse execute = httpRequest.execute();
        String html = execute.body();
        return html;
    }

    @Override
    public void douBanMovieTop250(String excelUrl) {
        List<DouBanMovieTop250> lists = new ArrayList<>();
        for (int i = 0; i < PAGE_TOTAL; i++) {
            String html = askURL(BASE_URL + (i * PAGE_SIZE));
            List<DouBanMovieTop250> rows = jsoupParse(html);
            log.info("???{}???", i + 1);
            lists.addAll(rows);
        }
        ExcelUtil.createExcel(new DouBanExcelConfig(), lists, excelUrl);
    }

    public static void main(String[] args) {
        new DouBanServiceImpl().douBanMovieTop250("C:\\LiXiuGuang\\Doc\\????????????\\Test_File\\abcd.xls");
    }

    private List<DouBanMovieTop250> jsoupParse(String html) {
        List<DouBanMovieTop250> rows = new ArrayList<>();
        //6.Jsoup??????html
        Document document = Jsoup.parse(html);
        //???js???????????????class ??????????????????????????????
        Elements postItems = document.getElementsByClass("info");
        int item = 1;
        //????????????????????????
        for (Element postItem : postItems) {
            List<String> row = new ArrayList<>();
            //?????????
            String movieName = "";
            //????????????
            String movieInfo = "";
            //????????????
            String movieQuote = "";
            //????????????
            String movieScore = "";
            log.info("???{}???", item);
            item++;
            Elements movieNames = postItem.select("div[class='hd']");
            for (Element element : movieNames) {
                movieName += element.text();
            }
            log.info("?????????:" + movieName);
            Elements movieInfos = postItem.select("div[class='bd'] p[class='']");
            for (Element element : movieInfos) {
                movieInfo += element.text();
            }
            log.info("????????????:" + movieInfo);
            Elements movieQuotes = postItem.select("div[class='bd'] p[class='quote']");
            for (Element element : movieQuotes) {
                movieQuote += element.text();
            }
            log.info("????????????:" + movieQuote);
            Elements movieScores = postItem.select("div[class='star'] span[class='rating_num']");
            for (Element element : movieScores) {
                movieScore += element.text();
            }
            log.info("????????????:" + movieScore);
            DouBanMovieTop250 douBanMovieTop250 = new DouBanMovieTop250();
            douBanMovieTop250.setMovieName(movieName);
            douBanMovieTop250.setMovieInfo(movieInfo);
            douBanMovieTop250.setMovieQuote(movieQuote);
            douBanMovieTop250.setMovieScore(movieScore);
            rows.add(douBanMovieTop250);
        }
        return rows;
    }
}