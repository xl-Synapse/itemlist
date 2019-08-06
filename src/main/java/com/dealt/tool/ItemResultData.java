package com.dealt.tool;

import com.dealt.entity.InfoEntity;
import com.dealt.entity.ItemEntity;

import java.util.List;

public class ItemResultData {
    private int total;

    private List<InfoEntity> infoEntities;

    private List<ItemEntity> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<InfoEntity> getInfoEntities() {
        return infoEntities;
    }

    public void setInfoEntities(List<InfoEntity> infoEntities) {
        this.infoEntities = infoEntities;
    }

    public List<ItemEntity> getRows() {
        return rows;
    }

    public void setRows(List<ItemEntity> rows) {
        this.rows = rows;
    }
}
