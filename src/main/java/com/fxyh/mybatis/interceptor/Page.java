package com.fxyh.mybatis.interceptor;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类名: Page <br/>
 * 功能: 分页实体类 (结合jqery easyui) <br/>
 * 原因:  <br/>
 * 日期: 2016年10月31日 上午11:45:26 <br/>
 *
 * @author wangjinping
 * @version @param <T>	分页对象泛型，在本类被调用时指定
 * @since JDK 1.6
 */
public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;

    public final static int DEFAULT_PAGE_SIZE = 10; //默认显示的记录数 

    private Long total; // 总记录 
    private List<T> rows; //显示的记录  

    @JsonIgnore
    private int from;
    @JsonIgnore
    private int size;
    @JsonIgnore
    private int pageNo; // 当前页 
    @JsonIgnore
    private int pages; // 当前页 
    @JsonIgnore
    private int pageSize; // 每页显示的记录数 
    @JsonIgnore
    private Map<String, Object> condition = new HashMap<String, Object>(); //查询条件

    @JsonIgnore
    private String sort = "seq";// 排序字段
    @JsonIgnore
    private String order = "asc";// asc，desc mybatis Order 关键字

    public Page() {
    }

    //构造方法
    public Page(int pageNo, int pageSize) {
        //计算当前页  
        if (pageNo < 0) {
            this.pageNo = 1;
        } else {
            //当前页
            this.pageNo = pageNo;
        }
        //记录每页显示的记录数  
        if (pageSize < 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
        //计算开始的记录和结束的记录  
        this.from = (this.pageNo - 1) * this.pageSize;
        this.size = this.pageSize;
    }

    // 构造方法
    public Page(int pageNo, int pageSize, String sort, String order) {
        // 计算当前页  
        if (pageNo < 0) {
            this.pageNo = 1;
        } else {
            // 当前页
            this.pageNo = pageNo;
        }
        // 记录每页显示的记录数  
        if (pageSize < 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
        // 计算开始的记录和结束的记录  
        this.from = (this.pageNo - 1) * this.pageSize;
        this.size = this.pageSize;
        // 排序字段，正序还是反序
        this.sort = sort;
        this.order = order;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
