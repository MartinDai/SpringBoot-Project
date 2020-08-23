package com.doodl6.springboot.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by daixiaoming on 2018/8/29.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PPTUtil {

    public static List<String> convertPPTToImages(String pptFilePath, String imageFolderPath) throws IOException {
        File imageFolderFile = new File(imageFolderPath);
        if (imageFolderFile.exists()) {
            imageFolderFile.delete();
        }
        imageFolderFile.mkdirs();

        if (StringUtils.isBlank(pptFilePath)) {
            throw new IllegalArgumentException("PPT文件路径未指定");
        }

        List<String> imagePathList;
        if (pptFilePath.endsWith(".ppt")) {
            imagePathList = convertPPT2003ToImages(pptFilePath, imageFolderPath);
        } else if (pptFilePath.endsWith(".pptx")) {
            imagePathList = convertPPT2007ToImages(pptFilePath, imageFolderPath);
        } else {
            throw new IllegalArgumentException("不合法的文件格式");
        }

        log.info("PPT转图片完成");

        return imagePathList;
    }

    /**
     * 转换2003版（.ppt）格式的PPT文件为图片
     */
    private static List<String> convertPPT2003ToImages(String pptFilePath, String imageFolderPath) throws IOException {
        List<String> imagePathList = Lists.newArrayList();

        FileInputStream fis = new FileInputStream(pptFilePath);
        HSLFSlideShow ppt = new HSLFSlideShow(fis);
        fis.close();

        Dimension dimension = ppt.getPageSize();
        List<HSLFSlide> slideList = ppt.getSlides();
        int index = 0;
        for (HSLFSlide slide : slideList) {

            log.info("正在转换PPT第" + (++index) + "页");

            File imageFile = new File(imageFolderPath + "/" + (index) + ".png");

            convertSlideToImage(slide, dimension, imageFile);

            imagePathList.add(imageFile.getAbsolutePath());
        }

        return imagePathList;
    }

    /**
     * 转换2007版（.pptx）格式的PPT文件为图片
     */
    private static List<String> convertPPT2007ToImages(String pptFilePath, String imageFolderPath) throws IOException {
        List<String> imagePathList = Lists.newArrayList();

        FileInputStream fis = new FileInputStream(pptFilePath);
        XMLSlideShow ppt = new XMLSlideShow(fis);
        fis.close();

        Dimension dimension = ppt.getPageSize();
        List<XSLFSlide> slideList = ppt.getSlides();
        int index = 0;
        for (XSLFSlide slide : slideList) {

            log.info("正在转换PPT第" + (++index) + "页");

            File imageFile = new File(imageFolderPath + "/" + (index) + ".png");

            convertSlideToImage(slide, dimension, imageFile);

            imagePathList.add(imageFile.getAbsolutePath());
        }

        return imagePathList;
    }

    /**
     * 转化单页PPT为图片
     */
    private static void convertSlideToImage(Sheet sheet, Dimension dimension, File imageFile) {
        BufferedImage img = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        graphics.setColor(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, dimension.width, dimension.height));

        //把单页PPT渲染成图像
        sheet.draw(graphics);

        //保存图片
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            ImageIO.write(img, "png", out);
        } catch (IOException e) {
            log.error("保存PPT图片异常", e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> imagePathList = convertPPTToImages("/Users/martin/Desktop/Logback实战.ppt", "/Users/martin/Desktop/ppt");
        System.out.println("转换完成");
        System.out.println(JSON.toJSONString(imagePathList));
    }

}
