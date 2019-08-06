package com.dealt.dao;

import com.dealt.entity.InfoEntity;
import com.dealt.tool.ItemResultData;
import com.dealt.tool.PagingData;

import java.util.List;

public interface InfoDao {

    /**
     * 添加并保存info、
     * @param infoEntity
     */
    public void addInfo(InfoEntity infoEntity);

    /**
     * 删除info、
     * @param infoID
     */
    public void delInfo(long infoID);

    /**
     * 获取所有info、
     * @return
     */
    public ItemResultData getAllInfo(PagingData pagingData);

    /**
     * 通过infoID获取info实体、
     * @param infoID
     * @return
     */
    public InfoEntity getInfo(long infoID);

    /**
     * 通过info实体更新一行、
     * @param infoEntity
     */
    public void updateInfo(InfoEntity infoEntity);

    /**
     * 判断是否存在该行记录、由模块和事项唯一确定一行、
     * @param modelID
     * @param toDoItem
     * @return
     */
    public boolean isExist(long modelID, String toDoItem);

    /**
     * 模糊查询、响应todoitem字段和notes字段、
     * @param keyWord
     * @return
     */
    public ItemResultData fuzzyQueryItemByToDoItemOrNotes(String keyWord, PagingData pagingData);

    public List<InfoEntity> labelQueryItemByHead(long headID);

    public List<InfoEntity> labelQueryItemByModel(long modelID);

    public List<InfoEntity> labelQueryItemByLevel(long level);

    public List<InfoEntity> labelQueryItemByStatus(long status);

    /**
     * 多条件查询、传入0则会被屏蔽该条件、
     * @param headID
     * @param modelID
     * @param level
     * @param status
     * @param pagingData 用于分页的信息、
     * @return
     */
    public ItemResultData labelQueryItemByMultiple(long headID, long modelID, long level, long status, PagingData pagingData);

}
