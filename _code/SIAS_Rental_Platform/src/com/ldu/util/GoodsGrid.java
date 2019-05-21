package com.ldu.util;

import javax.xml.bind.annotation.XmlRootElement;

import com.ldu.pojo.Goods;

import java.util.List;

/**
 * 
 */

@XmlRootElement
public class GoodsGrid {
    private int current;//Current page number
    private int rowCount;//Number of lines per page
    private int total;//Total number of rows
    private List<Goods> rows;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Goods> getRows() {
        return rows;
    }

    public void setRows(List<Goods> rows) {
        this.rows = rows;
    }
}
