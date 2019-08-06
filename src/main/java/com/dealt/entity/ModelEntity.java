package com.dealt.entity;

import javax.persistence.*;

@Entity
@Table(name = "MODEL", schema = "USER2", catalog = "")
@SequenceGenerator(name="MODEL",sequenceName="SEQ_MODEL_MODELID")
public class ModelEntity {
    private long modelid;
    private String modelname;

    @Id
    @Column(name = "MODELID")
    @GeneratedValue(generator="MODEL")
    public long getModelid() {
        return modelid;
    }

    public void setModelid(long modelid) {
        this.modelid = modelid;
    }

    @Basic
    @Column(name = "MODELNAME")
    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelEntity that = (ModelEntity) o;

        if (modelid != that.modelid) return false;
        if (modelname != null ? !modelname.equals(that.modelname) : that.modelname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (modelid ^ (modelid >>> 32));
        result = 31 * result + (modelname != null ? modelname.hashCode() : 0);
        return result;
    }
}
