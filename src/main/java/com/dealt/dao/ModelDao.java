package com.dealt.dao;

import com.dealt.entity.ModelEntity;

import java.util.List;

public interface ModelDao {

    /**
     * 添加并保存模块、
     * @param modelName
     */
    public void addModel(String modelName);

    /**
     * 通过modelID删除模块、
     * @param modelID
     */
    public void delModel(long modelID);

    /**
     * 通过modelID获得modelName模块名、
     * @param modelID
     */
    public String getModelName(long modelID);

    /**
     * 通过modelID查找并更新modelName模块名、
     * @param modelEntity
     */
    public void updateModel(ModelEntity modelEntity);

    /**
     * 通过modelName获取modelID、
     * @param modelName
     * @return
     */
    public long getModelID(String modelName);

    public boolean isExist(long modelID);

    public boolean isExist(String modelName);

    public List<ModelEntity> getAllModel();

}
