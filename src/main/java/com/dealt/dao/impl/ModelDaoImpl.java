package com.dealt.dao.impl;

import com.dealt.dao.ModelDao;
import com.dealt.entity.ModelEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("modelDao")
@Transactional
public class ModelDaoImpl implements ModelDao {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria(){
        Criteria criteria =  this.getSession().createCriteria(ModelEntity.class);
        criteria.addOrder(Order.desc("modelid"));
        return criteria;
    }

    public void addModel(String modelName) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setModelid(0);
        modelEntity.setModelname(modelName);
        this.getSession().save(modelEntity);
    }

    public void delModel(long modelID) {
        Session session = this.getSession();
        ModelEntity modelEntity = (ModelEntity)session.get(ModelEntity.class, modelID);
        if (modelEntity != null) {
            session.delete(modelEntity);
        }
    }

    public String getModelName(long modelID) {
        ModelEntity modelEntity = (ModelEntity)this.getSession().get(ModelEntity.class, modelID);
        return modelEntity.getModelname();
    }

    public void updateModel(ModelEntity modelEntity) {
        Session session = this.getSession();
        //这里采用load方法而不是get方法、将数据库中一行加载到该对象中、对该对象所做的改动都会映射到数据库中、
        ModelEntity targetModelEntity = (ModelEntity) session.load(ModelEntity.class, modelEntity.getModelid());
        if (targetModelEntity != null) {
            //修改
            targetModelEntity.setModelname(modelEntity.getModelname());
        }
    }

    public long getModelID(String modelName) {
        List<ModelEntity> modelEntities = this.getCriteria().add(Restrictions.eq("modelname", modelName)).list();
        return (modelEntities.size() > 0) ? modelEntities.get(0).getModelid() : -1;
    }

    public boolean isExist(long modelID) {
        Criteria criteria = this.getCriteria();
        List list = criteria.add(Restrictions.eq("modelid", modelID))
                .list();
        return list.size() > 0;
    }

    public boolean isExist(String modelName) {
        Criteria criteria = this.getCriteria();
        List list = criteria.add(Restrictions.eq("modelname", modelName))
                .list();
        return list.size() > 0;
    }

    public List<ModelEntity> getAllModel() {
        List list = this.getSession().createCriteria(ModelEntity.class).list();
        return list;
    }
}
