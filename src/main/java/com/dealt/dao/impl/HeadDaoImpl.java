package com.dealt.dao.impl;

import com.dealt.dao.HeadDao;
import com.dealt.entity.HeadEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//如果不加注解@Transactional会报错No Hibernate Session bound to thread、查资料知是新版hibernate强制事务管理方面的问题、
@Repository("headDao")
@Transactional
public class HeadDaoImpl implements HeadDao {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria(){
        Criteria criteria =  this.getSession().createCriteria(HeadEntity.class);
        criteria.addOrder(Order.desc("headid"));
        return criteria;
    }

    public void addHead(String headName) {
        HeadEntity headEntity = new HeadEntity();
        headEntity.setHeadid(0);
        headEntity.setHeadname(headName);
        this.getSession().save(headEntity);
    }

    public void delHead(long headID) {
        Session session = this.getSession();
        HeadEntity headEntity = (HeadEntity)session.get(HeadEntity.class, headID);
        if (headEntity != null) {
            session.delete(headEntity);
        }
    }

    public String getHeadName(long headID) {
        HeadEntity headEntity = (HeadEntity)this.getSession().get(HeadEntity.class, headID);
        return headEntity.getHeadname();
    }

    public void updateHead(HeadEntity headEntity) {
        Session session = this.getSession();
        //这里采用load方法而不是get方法、将数据库中一行加载到该对象中、对该对象所做的改动都会映射到数据库中、
        HeadEntity targetHeadEntity = (HeadEntity) session.load(HeadEntity.class, headEntity.getHeadid());
        if (targetHeadEntity != null) {
            //修改
            targetHeadEntity.setHeadname(headEntity.getHeadname());
        }
    }

    public long getHeadID(String headName) {
        List<HeadEntity> headEntities = this.getCriteria().add(Restrictions.eq("headname", headName)).list();
        return (headEntities.size() > 0) ? headEntities.get(0).getHeadid() : -1;
    }

    public boolean isExist(String headName) {
        Criteria criteria = this.getCriteria();
        List list = criteria.add(Restrictions.eq("headname", headName))
                .list();
        return list.size() > 0;
    }

    public boolean isExist(long headID) {
        Criteria criteria = this.getCriteria();
        List list = criteria.add(Restrictions.eq("headid", headID))
                .list();
        return list.size() > 0;
    }

    public List<HeadEntity> getAllHead() {
        return this.getSession().createCriteria(HeadEntity.class).list();
    }

    /**
     * 下方为第一版由自己控制事务管理的代码、后订正为交由spring注解事务管理、
     */
    /*public void addHead(HeadEntity headEntity) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(headEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    public void delHead(long headID) {
        Session session = sessionFactory.openSession();
        HeadEntity headEntity = (HeadEntity)session.get(HeadEntity.class, headID);
        if (headEntity != null) {
            session.beginTransaction();
            //删除提交数据库
            session.delete(headEntity);
            session.getTransaction().commit();
        }
        session.close();

    }

    public String getHead(long headID) {
        Session session = sessionFactory.openSession();
        HeadEntity headEntity = (HeadEntity)session.get(HeadEntity.class, headID);
        session.close();
        return headEntity.getHeadname();
    }

    public void updateHead(long headID, String headName) {
        Session session = sessionFactory.openSession();
        HeadEntity headEntity = (HeadEntity) session.load(HeadEntity.class, headID);

        if (headEntity != null) {
            session.beginTransaction();
            //修改
            headEntity.setHeadname(headName);
            //提交
            session.update(headEntity);
            session.getTransaction().commit();
        }

        session.close();
    }*/
}
