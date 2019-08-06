package com.dealt.dao.impl;

import com.dealt.dao.InfoDao;
import com.dealt.entity.InfoEntity;
import com.dealt.tool.ItemResultData;
import com.dealt.tool.PagingData;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("infoDao")
@Transactional
public class InfoDaoImpl implements InfoDao {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria(){
        Criteria criteria =  this.getSession().createCriteria(InfoEntity.class);

        criteria.addOrder(Order.desc("infoid"));
        return criteria;

    }

    private ItemResultData getItemResultData(Criteria criteria, PagingData pagingData){
        int total = criteria.list().size();
        criteria.setFirstResult(pagingData.getOffset());
        criteria.setMaxResults(pagingData.getLimit());
        List<InfoEntity> infoEntities = criteria.list();

        ItemResultData itemResultData = new ItemResultData();
        itemResultData.setTotal(total);
        itemResultData.setInfoEntities(infoEntities);
        return itemResultData;
    }

    public void addInfo(InfoEntity infoEntity) {
        this.getSession().save(infoEntity);
    }

    public void delInfo(long infoID) {
        Session session = this.getSession();
        InfoEntity infoEntity = (InfoEntity)session.get(InfoEntity.class, infoID);
        if(infoEntity != null){
            session.delete(infoEntity);
        }
    }

    public ItemResultData getAllInfo(PagingData pagingData) {
        Criteria criteria = this.getCriteria();
        return getItemResultData(criteria,pagingData);
    }

    public InfoEntity getInfo(long infoID) {
        return (InfoEntity) this.getSession().get(InfoEntity.class, infoID);
    }

    /**
     * 经过测试发现直接update不可以、现改为较为繁琐的set操作、
     * @param infoEntity
     */
    public void updateInfo(InfoEntity infoEntity) {
        Session session = this.getSession();
        InfoEntity targetInfoEntity = (InfoEntity)session.load(InfoEntity.class, infoEntity.getInfoid());
        if(targetInfoEntity != null){
            targetInfoEntity.setHeadid(infoEntity.getHeadid());
            targetInfoEntity.setInfolevel(infoEntity.getInfolevel());
            targetInfoEntity.setModelid(infoEntity.getModelid());
            targetInfoEntity.setNotes(infoEntity.getNotes());
            targetInfoEntity.setProgressbar(infoEntity.getProgressbar());
            targetInfoEntity.setScheduledtime(infoEntity.getScheduledtime());
            targetInfoEntity.setStatus(infoEntity.getStatus());
            targetInfoEntity.setTodoitem(infoEntity.getTodoitem());
        }
    }

    public boolean isExist(long modelID, String toDoItem) {
        Criteria criteria = this.getCriteria();
        List list = criteria.add(Restrictions.eq("modelid", modelID))
                .add(Restrictions.eq("todoitem", toDoItem))
                .list();

        return list.size() > 0;
    }

    public ItemResultData fuzzyQueryItemByToDoItemOrNotes(String keyWord, PagingData pagingData) {
        keyWord = "%" + keyWord + "%";
        Criteria criteria = this.getCriteria();
        criteria.add(Restrictions.or(Restrictions.like("todoitem", keyWord), Restrictions.like("notes", keyWord)));
/*        List<InfoEntity> infoEntitiesByNotes = criteria.add(Restrictions.like("notes", keyWord)).list();
        infoEntitiesByToDoItem.addAll(infoEntitiesByNotes);*/
        return getItemResultData(criteria, pagingData);
    }

    public List<InfoEntity> labelQueryItemByHead(long headID) {
        return this.getCriteria().add(Restrictions.eq("headid", headID)).list();
    }

    public List<InfoEntity> labelQueryItemByModel(long modelID) {
        return this.getCriteria().add(Restrictions.eq("modelid", modelID)).list();
    }

    public List<InfoEntity> labelQueryItemByLevel(long level) {
        return this.getCriteria().add(Restrictions.eq("infolevel", level)).list();
    }

    public List<InfoEntity> labelQueryItemByStatus(long status) {
        return this.getCriteria().add(Restrictions.eq("status", status)).list();
    }

    public ItemResultData labelQueryItemByMultiple(long headID, long modelID, long level, long status, PagingData pagingData) {
        //这里由业务层保证变量非空、当业务层将某值设为-1时、即可屏蔽筛选、
        Criteria criteria = this.getCriteria();
        //Disjunction disjunction = Restrictions.disjunction();//用于组合一组或、
        Conjunction conjunction = Restrictions.conjunction();  ;//用于组合一组与、
        if(headID != -1){
            conjunction.add(Restrictions.eq("headid", headID));
        }
        if(modelID != -1) {
            conjunction.add(Restrictions.eq("modelid", modelID));
        }
        if(level != -1){
            conjunction.add(Restrictions.eq("infolevel", level));
        }
        if(status != -1){
            conjunction.add(Restrictions.eq("status", status));
        }

        criteria.add(conjunction);
        return getItemResultData(criteria, pagingData);
    }

    private InfoEntity getInfoByNoID(InfoEntity infoEntity){
        Criteria criteria = this.getCriteria();
        List<InfoEntity> list = criteria.add(Restrictions.eq("modelid", infoEntity.getModelid()))
                .add(Restrictions.eq("todoitem", infoEntity.getTodoitem()))
                .list();
        return (list.size() > 0) ? list.get(0) : null;
    }

}
