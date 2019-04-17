package com.fxyh.mybatis.interceptor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: MyPage
 * @description: 实现分页的辅助类，用于封装用于分页的一些参数
 * @author: fengzhaoquan
 * @create: 2019-04-16 22:02
 * @Version 1.0
 **/
public class MyPage<T> implements Serializable {

    private static final long serialVersionUID = -3900780045673375983L;

    public final static int DEFAULT_PAGE_SIZE = 10; //默认显示的记录数
    public final static int DEFAULT_PAGENO = 1; //默认显示的页数

    private Long total; // 总记录
    private Long pages; // 总页数
    private List<T> rows; //显示的记录
    private int pageNo; // 当前页
    private int pageSize; // 每页显示的记录数
    private int pageStart;//起始记录数
    private boolean useFlag = true; // 是否使用分页
    private boolean checkFlag = true; // 是否检查页码有效性

    /**
     * @Author fengzhaoquan
     * @Description 传入当前页和每页大小
     **/
    public MyPage(Integer pageNo, Integer pageSize) {
        if(pageNo == null || pageNo < 1){
            pageNo = MyPage.DEFAULT_PAGENO;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = MyPage.DEFAULT_PAGE_SIZE;
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageStart = (pageNo - 1) * pageSize ;
    }

    /**
     * @Author fengzhaoquan
     * @Description 传入当前页和页数大小和是否使用分页功能
     * @Date
     * @Param
     * @return
     **/
    public MyPage(Integer pageNo, Integer pageSize, boolean useFlag) {
        this.useFlag = useFlag;
        this(pageNo, pageSize);
    }

    public MyPage(Integer pageNo, Integer pageSize, boolean useFlag, boolean checkFlag){
        this.checkFlag = checkFlag;
        this(pageNo, pageSize, useFlag);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        if (total % pageSize != 0) {
            this.pages = (total / pageSize) + 1;
        } else {
            this.pages = total / pageSize;
        }
        if(total <= 0){
            this.total = 0L;
            this.pages = 0L;
        }
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}
