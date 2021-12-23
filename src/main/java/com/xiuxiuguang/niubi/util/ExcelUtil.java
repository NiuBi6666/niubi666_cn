package com.xiuxiuguang.niubi.util;

import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.xiuxiuguang.niubi.config.DouBanExcelConfig;
import com.xiuxiuguang.niubi.pojo.reptile.douban.DouBanMovieTop250;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.List;
import java.util.Map;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: ExcelUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/9     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
public class ExcelUtil {
    public static String createExcel(DouBanExcelConfig excelConfig, List<DouBanMovieTop250> list, String excelUrl) {
        //通过构造方法创建writer
        ExcelWriter writer = new ExcelWriter(excelUrl);
        //自定义标题别名
        Map<String, String> headerAlias = excelConfig.getHeaderAlias();
        for (Map.Entry<String, String> entry : headerAlias.entrySet()) {
            writer.addHeaderAlias(entry.getKey(), entry.getValue());
        }
        //跳过当前行，既第一行，非必须，在此演示用
        //writer.passCurrentRow();
        StyleSet style = writer.getStyleSet();
        style.setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        //合并单元格后的标题行，使用默认标题样式
        writer.merge(3, "豆瓣电影Top250");
        //一次性写出内容，强制输出标题
        writer.write(list, true);
        //关闭writer，释放内存
        writer.close();
        log.info("新建Excel成功");
        return "";
    }
}