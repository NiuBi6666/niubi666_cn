package com.xiuxiuguang.niubi.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.xiuxiuguang.niubi.constant.OfficeSuffixConstant;
import com.xiuxiuguang.niubi.enums.ResponseCodeEnum;
import com.xiuxiuguang.niubi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileTransUtil
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
public class FileTransUtil {


    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.writeFromStream(multipartFile.getInputStream(), new File(multipartFile.getOriginalFilename()));
        return file;
    }


    /**
     * PDF转Word模块
     */

    public static String pdfToWord(MultipartFile multipartFile, String wordUrl, Integer startPage, Integer length) throws IOException {
        File file = multipartFileToFile(multipartFile);
        String generateWordFile = pdfToWord(file, wordUrl, startPage, length);
        FilesUtil.deleteTempFile(file);
        return generateWordFile;
    }

    public static String pdfToWord(File pdfFile, String wordUrl, Integer startPage, Integer length) throws IOException {
        checkSuffix(pdfFile, OfficeSuffixConstant.PDF);
        PDDocument pdfDocument = PDDocument.load(pdfFile);
        int pageTotal = pdfDocument.getNumberOfPages();
        //Word第一页，0或者1都可以
        Integer startNum = PagesUtil.getStartPage(startPage, pageTotal);
        Integer lengthNum = PagesUtil.getPageLength(startPage, length, pageTotal);
        return fileTrans(pdfDocument, pdfFile, wordUrl, OfficeSuffixConstant.WORD, startNum, lengthNum);
    }


    /**
     * PDF转图片模块
     */

    public static String pdfToImage(MultipartFile multipartFile, String imageUrl, Integer startPage, Integer length) throws IOException {
        File file = multipartFileToFile(multipartFile);
        String generateImageFileList = pdfToImage(file, imageUrl, startPage, length);
        FilesUtil.deleteTempFile(file);
        return generateImageFileList;
    }


    public static String pdfToImage(File pdfFile, String imageUrl, Integer startPage, Integer length) throws IOException {
        checkSuffix(pdfFile, OfficeSuffixConstant.PDF);
        PDDocument pdfDocument = PDDocument.load(pdfFile);
        int pageTotal = pdfDocument.getNumberOfPages();
        Integer startNum = PagesUtil.getStartPage(startPage, pageTotal);
        if (0 != startNum) {
            startNum--;
        }
        Integer lengthNum = PagesUtil.getPageLength(startPage, length, pageTotal);
        return fileTrans(pdfDocument, pdfFile, imageUrl, OfficeSuffixConstant.JPG, startNum, lengthNum);
    }


    /**
     * PDF转TXT模块
     */

    public static String pdfToTxt(MultipartFile multipartFile, String txtUrl, Integer startPage, Integer length) throws IOException {
        File file = multipartFileToFile(multipartFile);
        String generateTxtFile = pdfToTxt(file, txtUrl, startPage, length);
        FilesUtil.deleteTempFile(file);
        return generateTxtFile;
    }


    public static String pdfToTxt(File pdfFile, String txtUrl, Integer startPage, Integer length) throws IOException {
        checkSuffix(pdfFile, OfficeSuffixConstant.PDF);
        PDDocument pdfDocument = PDDocument.load(pdfFile);
        int pageTotal = pdfDocument.getNumberOfPages();
        //Word第一页，0或者1都可以
        Integer startNum = PagesUtil.getStartPage(startPage, pageTotal);
        Integer lengthNum = PagesUtil.getPageLength(startPage, length, pageTotal);
        return fileTrans(pdfDocument, pdfFile, txtUrl, OfficeSuffixConstant.TXT, startNum, lengthNum);
    }

    private static Boolean checkSuffix(File file, String... suffix) {
        String fileSuffix = FileNameUtil.getSuffix(file);
        List<String> suffixList = Arrays.asList(suffix);
        if (!suffixList.contains(fileSuffix)) {
            throw new BusinessException(ResponseCodeEnum.FILE_SUFFIX_ERROR);
        }
        return true;
    }

    private static String fileTrans(PDDocument document, File file, String generateFileDir, String suffix, Integer startPage, Integer endPage) throws IOException {
        String generateFileUrl = null;
        //生成PDF文档内容剥离器
        PDFTextStripper stripper = new PDFTextStripper();
        //排序
        stripper.setSortByPosition(true);
        if (suffix.equals(OfficeSuffixConstant.JPG)) {
            //目前就图片走这里
            PDFRenderer renderer = new PDFRenderer(document);
            Integer lengthNum = endPage - startPage;
            List<String> generateImageFileList = new ArrayList<>();
            for (int i = 0; i < lengthNum; i++) {
                String generateImageFile = FilesNameUtil.getName(generateFileDir, FileNameUtil.mainName(file) + "_" + i, suffix);
                //Windows native DPI
                BufferedImage image = renderer.renderImageWithDPI(startPage + i, 144);
                //产生缩略图
                //BufferedImage srcImage = resize(image, 240, 240);
                ImageIO.write(image, OfficeSuffixConstant.JPG, new File(generateImageFile));
                log.info("文件转换成功，{}", generateImageFile);
                generateImageFileList.add(generateImageFile);
                generateFileUrl = JSONUtil.toJsonStr(generateImageFileList);
            }
        } else {
            //目前就Word、TXT走这里
            generateFileUrl = FilesNameUtil.getName(generateFileDir, FileNameUtil.mainName(file), suffix);
            //设置转换的开始页
            stripper.setStartPage(startPage);
            //设置转换的结束页
            stripper.setEndPage(endPage);
            Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(generateFileUrl), CharsetUtil.UTF_8);
            stripper.writeText(document, outputStreamWriter);
            outputStreamWriter.close();
        }

        document.close();
        log.info("文件转换成功，{}", generateFileUrl);
        return generateFileUrl;
    }


    /**
     * Image转PDF模块
     */

    public static String imgToPdf(MultipartFile multipartFile, String pdfUrl) throws IOException, DocumentException {
        File file = FileTransUtil.multipartFileToFile(multipartFile);
        String generateFileUrl = imgToPdf(file, pdfUrl);
        FilesUtil.deleteTempFile(file);
        return generateFileUrl;
    }


    /**
     * 将图片转换为PDF文件
     *
     * @param file SpringMVC获取的图片文件
     * @return PDF文件
     * @throws IOException       IO异常
     * @throws DocumentException PDF文档异常
     */
    public static String imgToPdf(File file, String pdfUrl) throws IOException, DocumentException {
        checkSuffix(file, OfficeSuffixConstant.IMAGE_SUFFIX);
        String generateFileUrl = FilesNameUtil.getName(pdfUrl, FileNameUtil.mainName(file), OfficeSuffixConstant.PDF);
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter.getInstance(doc, new FileOutputStream(generateFileUrl));
        doc.open();
        doc.newPage();
        Image image = Image.getInstance(FileUtil.readBytes(file));
        float height = image.getHeight();
        float width = image.getWidth();
        int percent = getPercent(height, width);
        image.setAlignment(Image.MIDDLE);
        image.scalePercent(percent);
        doc.add(image);
        doc.close();
        return generateFileUrl;
    }

    /**
     * 等比压缩，获取压缩百分比
     *
     * @param height 图片的高度
     * @param weight 图片的宽度
     * @return 压缩百分比
     */
    private static int getPercent(float height, float weight) {
        float percent = 0.0F;
        if (height > weight) {
            percent = PageSize.A4.getHeight() / height * 100;
        } else {
            percent = PageSize.A4.getWidth() / weight * 100;
        }
        return Math.round(percent);
    }
}