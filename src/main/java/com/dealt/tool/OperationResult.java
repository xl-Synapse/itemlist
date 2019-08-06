package com.dealt.tool;

import com.dealt.entity.ItemEntity;

import java.util.List;

public class OperationResult {
    private int resultCode;

    private String resultStr;

    private List<ItemEntity> rows;

    private int allDataLength;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public List<ItemEntity> getRows() {
        return rows;
    }

    public void setRows(List<ItemEntity> rows) {
        this.rows = rows;
    }

    public int getAllDataLength() {
        return allDataLength;
    }

    public void setAllDataLength(int allDataLength) {
        this.allDataLength = allDataLength;
    }
}
