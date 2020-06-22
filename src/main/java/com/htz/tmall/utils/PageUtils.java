package com.htz.tmall.utils;

public class PageUtils {

    public static Integer checkPageNo(String pageNoStr){
        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException ignore) {
        }
        return pageNo;
    }

    public static Integer checkPageSize(String pageSizeStr){
        int pageSize = 5;
        try {
            pageSize = Integer.parseInt(pageSizeStr);
        } catch (NumberFormatException ignore) {
        }
        return pageSize;
    }
}
