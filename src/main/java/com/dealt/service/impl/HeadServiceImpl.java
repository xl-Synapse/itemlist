package com.dealt.service.impl;

import com.dealt.dao.HeadDao;
import com.dealt.entity.HeadEntity;
import com.dealt.service.HeadService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("headService")
public class HeadServiceImpl implements HeadService {
    @Resource
    private HeadDao headDao;

    public String getHeadName(long headID) {
        return headDao.getHeadName(headID);
    }

    public List<HeadEntity> getAllHead() {
        return headDao.getAllHead();
    }

    public boolean addHead(String headName) {
        //判断是否存在、
        if(headDao.isExist(headName)){
            //存在、不允许添加、
            return false;
        }
        headDao.addHead(headName);
        return true;
    }

    public boolean delHead(long headID) {
        //判断是否存在、
        if(!headDao.isExist(headID)){
            //不存在、不允许删除、
            return false;
        }
        headDao.delHead(headID);
        return true;
    }

    public boolean updateHead(HeadEntity headEntity) {
        //判断是否存在、
        if(!headDao.isExist(headEntity.getHeadid())){
            //不存在、不允许更新、
            return false;
        }
        headDao.updateHead(headEntity);
        return true;
    }
}
