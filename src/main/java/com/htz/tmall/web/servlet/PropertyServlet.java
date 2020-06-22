package com.htz.tmall.web.servlet;

import com.htz.tmall.pojo.Category;
import com.htz.tmall.pojo.Property;
import com.htz.tmall.service.CategoryService;
import com.htz.tmall.service.PropertyService;
import com.htz.tmall.service.impl.CategoryServiceImpl;
import com.htz.tmall.service.impl.PropertyServiceImpl;
import com.htz.tmall.utils.Page;
import com.htz.tmall.utils.PageUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/propertyServlet")
@SuppressWarnings("all")
public class PropertyServlet extends BaseBackServlet {
    private PropertyService propertyService = new PropertyServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    public String list(HttpServletRequest request){
        //1. 获取参数
        //获取分类对应的cid
        String cidStr = request.getParameter("cid");
        int cid = 0;
        try {
            cid = Integer.parseInt(cidStr);
        } catch (NumberFormatException ignore) {
        }
        if(cid == 0){
            return "@errorBase.jsp";
        }
        //2. 获取category对象
        Category c = categoryService.get(cid);
        if(c == null){
            return "@errorBase.jsp";
        }
        //获取当前页
        String pageNoStr = request.getParameter("pageNo");
        //获取每页大小
        String pageSizeStr = request.getParameter("pageSize");
        //3. 校验参数是否合法
        int pageNo = PageUtils.checkPageNo(pageNoStr);
        int pageSize = PageUtils.checkPageSize(pageSizeStr);
        //4. 获取分页对象
        Page<Property> page = propertyService.getPage(cid, pageNo, pageSize);
        page.setParam("&cid=" + cid);
        //5. 放入request域中
        request.setAttribute("page", page);
        request.setAttribute("c", c);
        //6. 重定向到listProperty.jsp
        return "admin/listProperty.jsp";
    }

    public String add(HttpServletRequest request){
        //1. 获取参数
        //分类对应的cid
        String cidStr = request.getParameter("cid");
        int cid = Integer.parseInt(cidStr);
        //属性名称
        String name = request.getParameter("name");
        //2. 添加属性
        propertyService.add(cid, name);
        //跳转到listProperty.jsp
        return "@admin_property_list?cid=" + cidStr;
    }

    public String delete(HttpServletRequest request){
        //1. 获取参数
        //属性id
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        //分类cid
        String cidStr = request.getParameter("cid");
        int cid = Integer.parseInt(cidStr);
        //2. 调用service执行删除操作，并返回属性对应分类的cid
        propertyService.delete(id);
        //3. 重定向到listProperty.jsp
        return "@admin_property_list?cid=" + cid;
    }

    public String edit(HttpServletRequest request){
        //1. 获取属性id
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        //2. 获取对应的属性
        Property property = propertyService.get(id);
        //3. 放入request域中
        request.setAttribute("p", property);
        //4. 转发到editProperty.jsp
        return "admin/editProperty.jsp";
    }

    public String update(HttpServletRequest request){
        //1. 获取参数
        //property的id
        int id = Integer.parseInt(request.getParameter("id"));
        //property的name
        String name = request.getParameter("name");
        //category 的cid
        int cid = Integer.parseInt(request.getParameter("cid"));
        //2. 更新
        propertyService.update(id, name);
        //3. 重定向到admin_property_list
        return "@admin_property_list?cid=" + cid;
    }
}
