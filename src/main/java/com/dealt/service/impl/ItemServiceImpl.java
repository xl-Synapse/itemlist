package com.dealt.service.impl;

import com.dealt.dao.HeadDao;
import com.dealt.dao.InfoDao;
import com.dealt.dao.ModelDao;
import com.dealt.entity.InfoEntity;
import com.dealt.entity.ItemEntity;
import com.dealt.service.ItemService;
import com.dealt.tool.ItemResultData;
import com.dealt.tool.LabelQueryData;
import com.dealt.tool.PagingData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("itemService")
public class ItemServiceImpl implements ItemService {
    @Resource
    private HeadDao headDao;

    @Resource
    private InfoDao infoDao;

    @Resource
    private ModelDao modelDao;

    public ItemResultData getAllItem(PagingData pagingData) {
        ItemResultData itemResultData = infoDao.getAllInfo(pagingData);
/*        List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
        String modelName = null;
        String notesContent = null;
        String headName = null;
        long notesID = -1;
        for (InfoEntity infoEntity : infoEntities){
//            //对于每个info、需要查询他所在模块名、负责人、备注、
//
//            //查询模块名、
//            modelName = modelDao.getModelName(infoEntity.getModelid());
//
//            //查询负责人、
//            headName = headDao.getHeadName(infoEntity.getHeadid());
//
//            ItemEntity itemEntity = new ItemEntity(
//              infoEntity.getInfoid(),
//              modelName,
//              infoEntity.getTodoitem(),
//              infoEntity.getProgressbar(),
//              infoEntity.getStatus(),
//              infoEntity.getScheduledtime(),
//              infoEntity.getInfolevel(),
//              headName,
//                    infoEntity.getNotes()
//            );
            ItemEntity itemEntity = infoToItem(infoEntity);
            itemEntities.add(itemEntity);
        }*/
        List<InfoEntity> infoEntities = itemResultData.getInfoEntities();
        itemResultData.setRows(this.infoesToItems(infoEntities));
        return itemResultData;
    }

    public boolean addItem(ItemEntity itemEntity) {
        //转化为info、
        InfoEntity infoEntity = itemToInfo(itemEntity);
        //判断库中是否存在相同记录、通过模块+待办事项唯一确定一行、
        long modelID = infoEntity.getModelid();
        long headID = infoEntity.getHeadid();
        if(modelID != -1 && headID != -1){
            //存在该模块和负责人、需要继续判断是否存在相同待办事项、
            if(!infoDao.isExist(modelID, infoEntity.getTodoitem())){
                //不存在该行、允许添加、
                //已确认模块和负责人都存在、只需要操作info表、
                infoDao.addInfo(infoEntity);
                return true;
            }
            //存在该行、不允许添加、
        }
        //不存在该模块或负责人、不允许添加、
        return false;
    }


    private InfoEntity itemToInfo(ItemEntity itemEntity){
        //添加时没有infoID、若该实体存在infoID则使用其infoID、不存在则设定为-1、
        InfoEntity infoEntity = new InfoEntity();
        infoEntity.setInfoid((itemEntity.getInfoID() != null) ? itemEntity.getInfoIDlong() : -1);

        infoEntity.setModelid(
                modelDao.getModelID(itemEntity.getModelName())
        );
        infoEntity.setTodoitem(itemEntity.getToDoItem());
        infoEntity.setProgressbar(itemEntity.getProgressbarLong());
        infoEntity.setStatus(itemEntity.getStatusLong());
        infoEntity.setScheduledtime(itemEntity.getScheduledTimeDate());
        infoEntity.setInfolevel(itemEntity.getInfolevelLong());

        infoEntity.setHeadid(
                headDao.getHeadID(itemEntity.getHeadName())
        );

        infoEntity.setNotes(itemEntity.getNotesContent());

        return infoEntity;
    }

    private ItemEntity infoToItem(InfoEntity infoEntity){
        String modelName = modelDao.getModelName(infoEntity.getModelid());
        String headName = headDao.getHeadName(infoEntity.getHeadid());
        return new ItemEntity(
                infoEntity.getInfoid(),
                modelName,
                infoEntity.getTodoitem(),
                infoEntity.getProgressbar(),
                infoEntity.getStatus(),
                infoEntity.getScheduledtime(),
                infoEntity.getInfolevel(),
                headName,
                infoEntity.getNotes()
        );
    }

    private List<ItemEntity> infoesToItems(List<InfoEntity> infoEntities){
        List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
        for (InfoEntity infoEntity : infoEntities){
            ItemEntity itemEntity = infoToItem(infoEntity);
            itemEntities.add(itemEntity);
        }
        return itemEntities;
    }

    public boolean delItem(long infoID) {
        //必须从前端传来正确的id值、判断该id、
        if(infoDao.getInfo(infoID) != null){
            infoDao.delInfo(infoID);
            return true;
        }
        return false;
    }

    public boolean updateItem(ItemEntity itemEntity) {
        //必须从前端传来正确的id值、判断该id、
        if(infoDao.getInfo(itemEntity.getInfoIDlong()) != null){
            InfoEntity infoEntity = itemToInfo(itemEntity);
            infoDao.updateInfo(infoEntity);
            return true;
        }
        return false;
    }

    public ItemResultData fuzzyQueryItem(String keyWord, PagingData pagingData) {
        //判断关键字状态、
        if(keyWord == null || keyWord.trim().equals("")){
            //不存在关键字、不搜索、
            return this.getAllItem(pagingData);
        }
        //存在关键字、启动搜索、
        ItemResultData itemResultData = infoDao.fuzzyQueryItemByToDoItemOrNotes(keyWord, pagingData);
        //return this.infoesToItems(infoEntities);
        itemResultData.setRows(this.infoesToItems(itemResultData.getInfoEntities()));
        return itemResultData;
    }

    public List<ItemEntity> lableQueryItemByModel(String modelName) {
        //标签搜索的值不由用户键入、无需判断有无、签、
        long modelID = modelDao.getModelID(modelName);
        List<InfoEntity> infoEntities = infoDao.labelQueryItemByModel(modelID);
        return infoesToItems(infoEntities);
    }

    public List<ItemEntity> lableQueryItemBysStatus(long status) {
        //状态值由前端转化为0、1、2、
        List<InfoEntity> infoEntities = infoDao.labelQueryItemByStatus(status);
        return infoesToItems(infoEntities);
    }

    public List<ItemEntity> lableQueryItemByLevel(long level) {
        //等级值由前端转化为0、1、2、
        List<InfoEntity> infoEntities = infoDao.labelQueryItemByLevel(level);
        return infoesToItems(infoEntities);
    }

    public List<ItemEntity> lableQueryItemByHead(String headName) {
        //标签搜索的值不由用户键入、无需判断有无、签、
        long headID = headDao.getHeadID(headName);
        List<InfoEntity> infoEntities = infoDao.labelQueryItemByHead(headID);
        return infoesToItems(infoEntities);
    }

    public ItemResultData labelQueryItemByMultiple(LabelQueryData labelQueryData, PagingData pagingData) {
        //需要判断各个标签字段、若没有需要设置为-1以屏蔽搜索、
        ItemResultData itemResultData = infoDao.labelQueryItemByMultiple(
                (labelQueryData.getHeadID() != 0) ? labelQueryData.getHeadID() : -1,
                (labelQueryData.getModelID() != 0) ? labelQueryData.getModelID() : -1,
                (labelQueryData.getInfoLevel() != 0) ? labelQueryData.getInfoLevel() : -1,
                (labelQueryData.getStatus() != 0) ? labelQueryData.getStatus() : -1,
                pagingData
        );
        itemResultData.setRows(this.infoesToItems(itemResultData.getInfoEntities()));
        return itemResultData;
    }


}
