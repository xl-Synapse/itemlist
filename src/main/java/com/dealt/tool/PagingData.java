package com.dealt.tool;

public class PagingData {
    private int limit;

    private int offset;//已经越过的值、

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
