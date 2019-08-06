package com.dealt.service.impl;

import com.dealt.dao.ModelDao;
import com.dealt.entity.ModelEntity;
import com.dealt.service.ModelService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("modelService")
public class ModelServiceImpl implements ModelService {
    @Resource
    private ModelDao modelDao;

    public List<ModelEntity> getAllModel() {
        return modelDao.getAllModel();
    }

    public boolean addModel(String modelName) {
        //判断存在、
        if(modelDao.isExist(modelName)){
            //存在、不允许添加、
            return false;
        }
        modelDao.addModel(modelName);
        return true;
    }

    public boolean delModel(long modelID) {
        //判断存在、
        if(!modelDao.isExist(modelID)){
            //不存在、不允许删除、
            return false;
        }
        modelDao.delModel(modelID);
        return true;
    }

    public boolean updateModel(ModelEntity modelEntity) {
        //判断存在、
        if(!modelDao.isExist(modelEntity.getModelid())){
            //不存在、不允许更新、
            return false;
        }
        modelDao.updateModel(modelEntity);
        return true;
    }
}
