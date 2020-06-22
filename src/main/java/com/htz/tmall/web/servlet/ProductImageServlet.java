package com.htz.tmall.web.servlet;

import com.htz.tmall.dao.ProductImageDao;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.service.ProductImageService;
import com.htz.tmall.service.impl.ProductImageServiceImpl;
import com.htz.tmall.utils.ImageUtil;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/productImageServlet")
public class ProductImageServlet extends BaseBackServlet {
    private ProductImageService productImageService = new ProductImageServiceImpl();

    public String list(HttpServletRequest request){
        //1. 获取product的pid，以及category的cid
        String pidStr = request.getParameter("pid");
        int pid;
        try {
            pid = Integer.parseInt(pidStr);
        } catch (NumberFormatException ignore) {
            return "@errorBase.jsp";
        }
        //2. 查询product，填充productImage、category信息
        Product product = productImageService.get(pid);
        if(product == null){
            return "@errorBase.jsp";
        }
        //3. 转发到listProductImage.jsp
        request.setAttribute("p", product);
        return "admin/listProductImage.jsp";
    }

    public String add(HttpServletRequest request){
        //1. 解析request请求，封装参数
        Map<String, String> params = new HashMap<>();
        InputStream is = ImageUtil.parseUpload(request, params);
        //2. 获取参数
        String pidStr = params.get("pid");
        int pid = Integer.parseInt(pidStr);
        String type = params.get("type");
        //2. 上传图片
        //图片路径
        ServletContext sc = request.getServletContext();
        String fileFolderPath;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if(type.equals(ProductImageDao.TYPE_SINGLE)){
            fileFolderPath = sc.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_PATH);
            imageFolder_small = sc.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_SMALL_PATH);
            imageFolder_middle = sc.getRealPath(ImageUtil.IMAGE_PRODUCT_SINGLE_MIDDLE_PATH);
        }else{
            fileFolderPath = sc.getRealPath(ImageUtil.IMAGE_PRODUCT_DETAIL_PATH);
        }
        int cid = productImageService.add(is, pid, type, fileFolderPath, imageFolder_small, imageFolder_middle);
        //3. 重定向
        return "@admin_productImage_list?pid=" + pid;
    }

    public String delete(HttpServletRequest request){
        //1. 获取图片的id
        int id = Integer.parseInt(request.getParameter("id"));
        //获取product的pid
        int pid = Integer.parseInt(request.getParameter("pid"));
        //获取图片的类型
        String type = request.getParameter("type");
        //2. 删除
        //删除数据库记录
        //删除图片
        Map<String, List<String>> map = ImageUtil.fillImageFileMap(request);
        productImageService.delete(id, type, map);
        //3. 重定向到admin_list_productImage
        return "@admin_productImage_list?pid=" + pid;
    }
}
