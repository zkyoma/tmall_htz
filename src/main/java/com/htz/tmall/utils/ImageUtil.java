package com.htz.tmall.utils;

import com.htz.tmall.dao.ProductImageDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class ImageUtil {

    public static final String IMAGE_CATEGORY_PATH = "/img/category/";
    public static final String IMAGE_PRODUCT_DETAIL_PATH = "/img/productDetail/";
    public static final String IMAGE_PRODUCT_SINGLE_PATH = "/img/productSingle/";
    public static final String IMAGE_PRODUCT_SINGLE_MIDDLE_PATH = "/img/productSingle_middle/";
    public static final String IMAGE_PRODUCT_SINGLE_SMALL_PATH = "/img/productSingle_small/";

    public static Map<String, List<String>> fillImageFileMap(HttpServletRequest request){
        Map<String, List<String>> imgFilePathMap = new HashMap<>();
        ServletContext servletContext = request.getServletContext();
        List<String> singleList = new ArrayList<>();
        singleList.add(servletContext.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_PATH));
        singleList.add(servletContext.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_MIDDLE_PATH));
        singleList.add(servletContext.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_SMALL_PATH));
        imgFilePathMap.put(ProductImageDao.TYPE_SINGLE, singleList);
        imgFilePathMap.put(ProductImageDao.TYPE_DETAIL, Collections.singletonList(servletContext.getRealPath(ImageUtil.IMAGE_PRODUCT_DETAIL_PATH)));
        return imgFilePathMap;
    }

    public static void deleteImage(String imgFilePath, int id){
        File imgFile = new File(imgFilePath, id + ".jpg");
        imgFile.delete();
    }

    public static void uploadImage(InputStream is, String uploadPath, int id, String imageFolder_small, String imageFolder_middle){
        //4. 图片上传路径
        String fileName = id + ".jpg";
        File file = new File(uploadPath, fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream bis = null;
        try {
            bis = new BufferedOutputStream(new FileOutputStream(file));
            if(is != null && is.available() != 0){
                byte[] buffer = new byte[1024*1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1){
                    bis.write(buffer, 0, len);
                }
                bis.flush();
                //通过如下代码，把文件保存为jpg格式
                BufferedImage img = ImageUtil.change2jpg(file);
                if(null !=  img) {
                    ImageIO.write(img, "jpg", file);
                }
                if(uploadPath.contains("productSingle")){
                    File f_small = new File(imageFolder_small, fileName);
                    File f_middle = new File(imageFolder_middle, fileName);
                    ImageUtil.resizeImage(file, 56, 56, f_small);
                    ImageUtil.resizeImage(file, 217, 190, f_middle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bis){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用于图片上传
     * 1. 解析request请求，封装表单域的参数信息为Map集合
     * 2. 返回图片上传的输入流
     * @param request
     * @param params
     * @return
     */
    public static InputStream parseUpload(HttpServletRequest request, Map<String, String> params){
        InputStream is = null;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置图片上传大小
        // 设置上传文件的大小限制为10M
        factory.setSizeThreshold(1024 * 10240);
        //解析请求
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> iter = fileItems.iterator();
            while (iter.hasNext()){
                FileItem fileItem = iter.next();
                if(fileItem.isFormField()){
                    //表单类型，获取name值和value
                    String paramName = fileItem.getFieldName();
                    String paramValue = fileItem.getString();
                    //改变编码格式，防止乱码
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }else{
                    //非表单域，获取上传项的输入流
                    is = fileItem.getInputStream();
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 将图片格式转化为 .jpg 格式
     * @param f
     * @return
     */
    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = { 0xFF0000, 0xFF00, 0xFF };
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resizeImage(File srcFile, int width,int height, File destFile) {
        try {
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);
            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image resizeImage(Image srcImage, int width, int height) {
        try {
            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
