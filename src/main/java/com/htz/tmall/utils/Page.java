package com.htz.tmall.utils;

import java.util.List;

/**
 * 分页类
 */
public class Page<T> {

    //当前第几页
    private int pageNo;

    //当前页的List
    private List<T> list;

    //每页显示多少条记录
    private int pageSize = 5;

    //一共有多少条记录
    private int totalItemNumber;

    //分页需要的参数
    private String param;

    //构造器中需要对pageNo进行初始化
    public Page(int pageNo){
        this.pageNo = pageNo;
    }

    //需要校验
    public int getPageNo(){
        if(pageNo < 0){
            pageNo = 1;
        }else if(pageNo > getTotalPageNumber()){
            pageNo = getTotalPageNumber();
        }
        return pageNo;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalItemNumber() {
        return totalItemNumber;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    //获取总页数
    public int getTotalPageNumber(){
        int totalPageNumber = totalItemNumber / pageSize;
        if(totalItemNumber % pageSize != 0){
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public void setTotalItemNumber(int totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
    }

    //判断是否有下一页
    public boolean isHasNext(){
        return pageNo < getTotalPageNumber();
    }

    //判断是否有上一页
    public boolean isHasPrev(){
        return pageNo > 1;
    }

    //返回下一页
    public int getNextPage(){
        return isHasNext() ? pageNo + 1 : pageNo;
    }

    //返回上一页
    public int getPrevPage(){
        return isHasPrev() ? pageNo - 1 : pageNo;
    }
}
