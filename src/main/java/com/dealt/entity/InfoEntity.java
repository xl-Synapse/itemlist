package com.dealt.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "INFO", schema = "USER2", catalog = "")
@SequenceGenerator(name="INFO",sequenceName="SEQ_INFO_INFOID")
public class InfoEntity {
    private long infoid;
    private long modelid;
    private String todoitem;
    private Long progressbar;
    private Long status;
    private Date scheduledtime;
    private Long infolevel;
    private long headid;
    private String notes;

    @Id
    @Column(name = "INFOID")
    @GeneratedValue(generator="INFO")
    public long getInfoid() {
        return infoid;
    }

    public void setInfoid(long infoid) {
        this.infoid = infoid;
    }

    @Basic
    @Column(name = "MODELID")
    public long getModelid() {
        return modelid;
    }

    public void setModelid(long modelid) {
        this.modelid = modelid;
    }

    @Basic
    @Column(name = "TODOITEM")
    public String getTodoitem() {
        return todoitem;
    }

    public void setTodoitem(String todoitem) {
        this.todoitem = todoitem;
    }

    @Basic
    @Column(name = "PROGRESSBAR")
    public Long getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(Long progressbar) {
        this.progressbar = progressbar;
    }

    @Basic
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "SCHEDULEDTIME")
    public Date getScheduledtime() {
        return scheduledtime;
    }

    public void setScheduledtime(Date scheduledtime) {
        this.scheduledtime = scheduledtime;
    }

    @Basic
    @Column(name = "INFOLEVEL")
    public Long getInfolevel() {
        return infolevel;
    }

    public void setInfolevel(Long infolevel) {
        this.infolevel = infolevel;
    }

    @Basic
    @Column(name = "HEADID")
    public long getHeadid() {
        return headid;
    }

    public void setHeadid(long headid) {
        this.headid = headid;
    }

    @Basic
    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoEntity that = (InfoEntity) o;

        if (infoid != that.infoid) return false;
        if (modelid != that.modelid) return false;
        if (headid != that.headid) return false;
        if (todoitem != null ? !todoitem.equals(that.todoitem) : that.todoitem != null) return false;
        if (progressbar != null ? !progressbar.equals(that.progressbar) : that.progressbar != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (scheduledtime != null ? !scheduledtime.equals(that.scheduledtime) : that.scheduledtime != null)
            return false;
        if (infolevel != null ? !infolevel.equals(that.infolevel) : that.infolevel != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (infoid ^ (infoid >>> 32));
        result = 31 * result + (int) (modelid ^ (modelid >>> 32));
        result = 31 * result + (todoitem != null ? todoitem.hashCode() : 0);
        result = 31 * result + (progressbar != null ? progressbar.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (scheduledtime != null ? scheduledtime.hashCode() : 0);
        result = 31 * result + (infolevel != null ? infolevel.hashCode() : 0);
        result = 31 * result + (int) (headid ^ (headid >>> 32));
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
