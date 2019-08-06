package com.dealt.entity;

import javax.persistence.*;

@Entity
@Table(name = "HEAD", schema = "USER2", catalog = "")
@SequenceGenerator(name="HEAD",sequenceName="SEQ_HEAD_HEADID")
public class HeadEntity {
    private long headid;
    private String headname;

    @Id
    @Column(name = "HEADID")
    @GeneratedValue(generator="HEAD")
    public long getHeadid() {
        return headid;
    }

    public void setHeadid(long headid) {
        this.headid = headid;
    }

    @Basic
    @Column(name = "HEADNAME")
    public String getHeadname() {
        return headname;
    }

    public void setHeadname(String headname) {
        this.headname = headname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeadEntity that = (HeadEntity) o;

        if (headid != that.headid) return false;
        if (headname != null ? !headname.equals(that.headname) : that.headname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (headid ^ (headid >>> 32));
        result = 31 * result + (headname != null ? headname.hashCode() : 0);
        return result;
    }
}
