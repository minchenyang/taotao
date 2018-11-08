package com.taotao.pojo;

import java.util.List;

/**
 * @program: taotao
 * @description:
 * @author: mcy
 * @create: 2018-11-08 18:32
 **/
public class EUDataGridResult {
    private long total;
    private List<?> rows;
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
