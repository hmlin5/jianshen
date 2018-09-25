package com.gimc.user.common.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ShineU
 * @date 2016年1月4日 上午11:40:53
 * @version 1.0
 */

public class ImageUtil {
    static final Logger Log = LoggerFactory.getLogger(ImageUtil.class);
    
    /**
     * @param root 文件目录
     * @param srcImgName 源图片(*.jpg)
     * @param h 目标图片高
     * @param w 目标图片宽
     * @return 按比例处理图片
     */
    public static String trasImage(String targetDir, String soureDir, String srcImgName, Integer w, Integer h) {
        if (srcImgName == null || "".equals(srcImgName) || w == 0 || h == 0 || targetDir == null) {
            return null;
        }
        
        int index = srcImgName.lastIndexOf(".");
        String newfileName = srcImgName;
        if (index > 0) {
            newfileName = srcImgName.substring(0, index) + "_" + w + "X" + h + ".png";
        }
        String newImageUrl = targetDir + File.separatorChar + newfileName;
        File destFile = new File(newImageUrl);
        // 如果存在该图片直接返回d
        if (destFile.exists()) {
            return newfileName;
        } else {
            try {
                File filepath = new File(targetDir);
                if (filepath.exists() == false) {
                    filepath.mkdirs();
                }
            } catch (Exception e) {
                Log.error("File Error: " + e.getMessage());
            }
        }
        try {
            File soureImgFile = new File(soureDir+File.separatorChar+srcImgName);
            BufferedImage bsf = ImageIO.read(soureImgFile);
            BufferedImage img1 = createResizedCopy(bsf, w, h, true);
            ImageIO.write(img1, "png", destFile);
            return newfileName;
        } catch (Exception e) {
            Log.error("File Error: " + e.getMessage());
        }
        return null;
    }
    
    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight,
        boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        map.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHints(map);
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
    
//    public static byte[] base64StrToImgBytes(String base64Str) throws IOException{
//    	BASE64Decoder decoder = new BASE64Decoder();
//    	// Base64解码
//        byte[] bytes = decoder.decodeBuffer(base64Str);
//        for (int i = 0; i < bytes.length; ++i) {
//            if (bytes[i] < 0) {// 调整异常数据
//                bytes[i] += 256;
//            }
//        }
//        return bytes;
//    }
}
