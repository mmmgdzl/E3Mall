package com.mmmgdzl.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private Long recordCount;
    private Integer totalPages;
    private List<SearchItem> itemList;

    public Long getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
