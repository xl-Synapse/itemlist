package com.dealt.service;

import com.dealt.entity.ModelEntity;

import java.util.List;

public interface ModelService {

    public List<ModelEntity> getAllModel();

    public boolean addModel(String modelName);

    public boolean delModel(long modelID);

    public boolean updateModel(ModelEntity modelEntity);
}
