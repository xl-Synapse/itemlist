package com.dealt.dao;

import com.dealt.entity.HeadEntity;

import java.util.List;

public interface HeadDao {

    /**
     * 添加并保存负责人、
     * @param headName
     */
    public void addHead(String headName);

    /**
     * 通过headID删除负责人、
     * @param headID
     */
    public void delHead(long headID);

    /**
     * 通过headID获得负责人姓名、
     * @param headID
     * @return
     */
    public String getHeadName(long headID);

    /**
     * 更新负责人姓名、
     * @param headEntity
     */
    public void updateHead(HeadEntity headEntity);

    /**
     * 通过headName获取headID、
     * @param headName
     * @return
     */

    public long getHeadID(String headName);

    public boolean isExist(String headName);

    public boolean isExist(long headID);

    public List<HeadEntity> getAllHead();
}
