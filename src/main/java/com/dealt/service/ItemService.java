package com.dealt.service;


import com.dealt.entity.HeadEntity;
import com.dealt.entity.ItemEntity;
import com.dealt.entity.ModelEntity;
import com.dealt.tool.ItemResultData;
import com.dealt.tool.LabelQueryData;
import com.dealt.tool.PagingData;

import java.util.List;

public interface ItemService {

    public ItemResultData getAllItem(PagingData pagingData);

    /**
     * 添加item需要注意、前端模块、负责人只能通过点选方式、即这里默认如果可以添加、模块、负责人一定是存在的、
     * @param itemEntity
     * @return
     */
    public boolean addItem(ItemEntity itemEntity);

    public boolean delItem(long infoID);

    public boolean updateItem(ItemEntity itemEntity);

    public ItemResultData fuzzyQueryItem(String keyWord, PagingData pagingData);

    public List<ItemEntity> lableQueryItemByModel(String modelName);

    public List<ItemEntity> lableQueryItemBysStatus(long status);

    public List<ItemEntity> lableQueryItemByLevel(long level);

    public List<ItemEntity> lableQueryItemByHead(String headName);

    public ItemResultData labelQueryItemByMultiple(LabelQueryData labelQueryData, PagingData pagingData);

}
