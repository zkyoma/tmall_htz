package com.htz.tmall.web.servlet;

import com.htz.tmall.pojo.Category;
import com.htz.tmall.service.CategoryService;
import com.htz.tmall.service.impl.CategoryServiceImpl;
import com.htz.tmall.utils.ImageUtil;
import com.htz.tmall.utils.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseBackServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 显示分类信息
     *
     * @param request
     * @param response
     * @return
     */
    public String list(HttpServletRequest request) {
        //1. 获取当前页，每页记录数
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNo = 1;
        int pageSize = 5;
        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException ignore) {
        }
        try {
            pageSize = Integer.parseInt(pageSizeStr);
        } catch (NumberFormatException ignore) {
        }
        //2. 获取page对象
        Page<Category> page = categoryService.getPage(pageNo, pageSize);
        //3. 设置到request域中
        request.setAttribute("page", page);
        //4. 转发到listCategory.jsp
        return "admin/listCategory.jsp";
    }

    /**
     * 增加分类
     *
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request) {
        //1. 解析request，封装表单域的参数
        Map<String, String> params = new HashMap<>();
        InputStream is = ImageUtil.parseUpload(request, params);
        //2. 获取文件上传的目录
        String uploadPath = request.getServletContext().getRealPath(ImageUtil.IMAGE_CATEGORY_PATH);
        //2. 上传文件
        categoryService.add(is, params, uploadPath);
        //3. 重定向到admin_category_list
        return "@admin_category_list";
    }

    /**
     * 删除分类
     *
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request) {
        //1. 获取分类对应的id
        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {
        }
        if(id != 0) {
            //2. 调用service方法删除
            //删除数据库的记录
            //删除分类图片
            //获取图片上传文件夹的路径
            String uploadPath = request.getServletContext().getRealPath(ImageUtil.IMAGE_CATEGORY_PATH);
            categoryService.delete(id, uploadPath);
        }
        //3. 重定向到admin_category_list
        return "@admin_category_list";
    }

    /**
     * 跳转修改页面
     *
     * @param request
     * @param response
     * @return
     */
    public String edit(HttpServletRequest request) {
        //1. 获取分类对应的id
        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {
        }
        if(id == 0){
            return "@errorBase.jsp";
        }
        //2. 根据id获取category对象
        Category c = categoryService.get(id);
        if(c == null){
            return "@errorBase.jsp";
        }
        //3. 存入request域中
        request.setAttribute("c", c);
        //4. 转发到请求页面
        return "admin/editCategory.jsp";
    }

    /**
     * 更新分类
     * @param request
     * @param response
     * @return
     */
    public String update(HttpServletRequest request) {
        //1. 解析request，封装表单域的参数
        Map<String, String> params = new HashMap<>();
        InputStream is = ImageUtil.parseUpload(request, params);
        //2. 获取文件上传的目录
        String uploadPath = request.getServletContext().getRealPath(ImageUtil.IMAGE_CATEGORY_PATH);
        //2. 上传文件
        categoryService.update(is, params, uploadPath);
        //3. 重定向到admin_category_list
        return "@admin_category_list";
    }
}
