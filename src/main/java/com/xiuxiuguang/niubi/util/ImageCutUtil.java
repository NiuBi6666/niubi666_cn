package com.xiuxiuguang.niubi.util;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.sun.imageio.plugins.common.ImageUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.print.Doc;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: ImageCutController
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/8     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
public class ImageCutUtil {

    //第一张
    private static final Rectangle IQIYI_CUT_ONE = new Rectangle(210, 0, 1920, 1080);
    //其余张
    private static final Rectangle IQIYI_CUT_OTHERS = new Rectangle(210, 880, 1920, 200);

    public static void imageCut() {
        List<String> oneFileUrls = cut("C:\\LiXiuGuang\\Doc\\李修广\\Others\\图片拼接\\第一张", "C:\\LiXiuGuang\\Doc\\李修广\\Others\\图片拼接\\Cut", IQIYI_CUT_ONE);
        List<String> othersFileUrls = cut("C:\\LiXiuGuang\\Doc\\李修广\\Others\\图片拼接\\Others", "C:\\LiXiuGuang\\Doc\\李修广\\Others\\图片拼接\\Cut", IQIYI_CUT_OTHERS);
        Set<String> set = new LinkedHashSet<>();
        List<String> list = new ArrayList<>();
        list.addAll(oneFileUrls);
        list.addAll(othersFileUrls);
        mergeImage(list, 2, "C:\\LiXiuGuang\\Doc\\李修广\\Others\\图片拼接\\Cut\\cut.jpg");
    }

    public static List<String> cut(String sourceFolder, String newFolder, Rectangle rectangle) {
        List<File> files = FileUtil.loopFiles(sourceFolder);
        FilesUtil.exists(newFolder);
        List<String> newFileUrls = new ArrayList<>();
        for (File sourceFile : files) {
            String fileName = FileNameUtil.mainName(sourceFile);
            String newFileUrl = newFolder + "/" + fileName + "_Cut.jpg";
            newFileUrls.add(newFileUrl);
            ImgUtil.cut(sourceFile, FileUtil.file(newFileUrl), rectangle);
            log.info("{}剪辑成功", newFileUrl);
        }
        return newFileUrls;
    }


    /**
     * @param files 要拼接的文件列表
     * @param type  1 横向拼接， 2 纵向拼接
     * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
     */
    public static void mergeImage(List<String> files, int type, String targetFile) {
        int len = files.size();
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files.get(i));
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {
                // 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }
        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0, images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));
            log.info("图片拼接成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        String url = "C:\\LiXiuGuang\\Doc\\李修广\\图片\\1to1\\109951164745068596.jpg";
        String[] urls = {url, url, url, url, url, url, url, url, url};
        weChatGroupHeadPortrait(urls);
    }


    /**
     * 拼接微信群组头像
     * 图片的宽度和画布的宽度比例为25:56
     * 图片的宽度和图片的间隔比例为25:2
     *
     * @param fileUrl
     * @return
     */
    public static void weChatGroupHeadPortrait(String... fileUrl) throws IOException {
        //修改背景宽度的时候需要同时修改图片缩略宽和间隔
        int width = 224;
        int height = 224;
        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        // 压缩图片所有的图片生成尺寸统一的为50x50
        for (int i = 0; i < fileUrl.length; i++) {
            int iHeight = height * 25 / 56;
            int iWidth = width * 25 / 56;
            bufferedImages.add(resize2(fileUrl[i], iHeight, iWidth, true));
        }
        BufferedImage outImage = new BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB);
        // 生成画布
        Graphics g = outImage.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        // 设置背景色
        g2d.setBackground(new Color(231, 231, 231));
        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2d.clearRect(0, 0, width, height);

        // 开始拼凑 根据图片的数量判断该生成那种样式的组合头像目前为4中
        int i2 = 1;
        int i3 = 1;
        for (int i = 1; i <= fileUrl.length; i++) {
            //如果为9宫图，图片间隔:图片宽度:背景宽度=1:8:28
            if (9 == fileUrl.length) {
                int iWidth = width * 8 / 28;
                if (i <= 3) {
                    g2d.drawImage(resize2(fileUrl[i - 1], iWidth, iWidth, true), (width * 8 / 28) * i + (width / 28) * i - (width * 8 / 28), (width / 28), null);
                } else if (i > 3 && i <= 6) {
                    g2d.drawImage(resize2(fileUrl[i - 1], iWidth, iWidth, true), (width * 8 / 28) * i2 + (width / 28) * i2 - (width * 8 / 28), (width * 8 / 28) + (width / 28) * 2, null);
                    i2++;
                } else if (i > 6 && i <= fileUrl.length) {
                    g2d.drawImage(resize2(fileUrl[i - 1], iWidth, iWidth, true), (width * 8 / 28) * i3 + (width / 28) * i3 - (width * 8 / 28), (width * 8 / 28) * 2 + (width / 28) * 3, null);
                    i3++;
                }
            }
            //如果为8张图片，第一排图片间隔:图片宽度:背景宽度=1:2:7，第二三排图片间隔:图片宽度:背景宽度=1:8:28
            //如果为7张图片，第一排图片间隔:图片宽度:背景宽度=5:4:14，第二三排图片间隔:图片宽度:背景宽度=1:8:28
            //如果为6张图片，横排图片间隔:图片宽度:背景宽度=1:8:28，竖排上下11:16:2:16:11
            //如果为5张图片，第一排图片间隔:图片宽度:背景宽度=5:4:14，第二排横排图片间隔:图片宽度:背景宽度=1:8:28，竖排上下11:16:2:16:11
            //如果为4宫图，图片间隔:图片宽度:背景宽度=2:25:56
            //如果为3张图片，第一排图片间隔:图片宽度:背景宽度=31:50:112，第二排横向图片间隔:图片宽度:背景宽度=2:25:56，竖向2:25:2:25:2


            //if (bufferedImages.size() == 4) {
            //    if (i <= 2) {
            //        g2d.drawImage(bufferedImages.get(i - 1), 100 * i + 8 * i - 100, 8, null);
            //    } else {
            //        g2d.drawImage(bufferedImages.get(i - 1), 100 * i2 + 8 * i2 - 100, 116, null);
            //        i2++;
            //    }
            //} else if (bufferedImages.size() == 3) {
            //    if (i <= 1) {
            //        g2d.drawImage(bufferedImages.get(i - 1), 31, 4, null);
            //    } else {
            //        g2d.drawImage(bufferedImages.get(i - 1), 50 * i2 + 4 * i2 - 50, 58, null);
            //        i2++;
            //    }
            //} else if (bufferedImages.size() == 2) {
            //    g2d.drawImage(bufferedImages.get(i - 1), 50 * i + 4 * i - 50, 31, null);
            //
            //} else if (bufferedImages.size() == 1) {
            //    g2d.drawImage(bufferedImages.get(i - 1), 31, 31, null);
            //}
            // 需要改变颜色的话在这里绘上颜色。可能会用到AlphaComposite类
        }
        String outPath = "C:\\LiXiuGuang\\Doc\\李修广\\a.jpg";
        String format = "JPG";
        ImageIO.write(outImage, format, new File(outPath));
    }

    /**
     * 图片缩放
     *
     * @param filePath 图片路径
     * @param height   高度
     * @param width    宽度
     * @param bb       比例不对时是否需要补白
     */
    public static BufferedImage resize2(String filePath, int height, int width,
                                        boolean bb) {
        try {
            double ratio = 0; // 缩放比例
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                // copyimg(filePath, "D:\\img");
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            return (BufferedImage) itemp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}