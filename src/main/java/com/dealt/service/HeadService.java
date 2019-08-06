package com.dealt.service;

import com.dealt.entity.HeadEntity;

import java.util.List;

public interface HeadService {

    public String getHeadName(long headID);

    public List<HeadEntity> getAllHead();

    public boolean addHead(String headName);

    public boolean delHead(long headID);

    public boolean updateHead(HeadEntity headEntity);
}
