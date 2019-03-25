package com.pinyougou.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体(封装分页数据)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-28<p>
 */
public class PageResult implements Serializable {
    /** 总记录数 */
    private long total;
    /** 分页数据集合 */
    private List<?> rows;

    public PageResult(){}
    public PageResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
