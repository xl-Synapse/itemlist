package com.dealt.entity;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于后台封装、前台接收进行显示、
 * 前台亦可按照struts2方式提交到后台处理、
 */
public class ItemEntity {
    private String infoID;
    private String modelName;
    private String toDoItem;
    private String progressBar;
    private String status;
    private String scheduledTime;
    private String infoLevel;
    private String headName;
    private String notesContent;

    public ItemEntity(){

    }

    public ItemEntity(long infoID, String modelName, String toDoItem, Long progressBar, Long status, Date scheduledTime, Long infoLevel, String headName, String notesContent) {
        this.infoID = infoID + "";
        this.modelName = modelName;
        this.toDoItem = toDoItem;
        this.progressBar = progressBar.toString();
        this.status = status.toString();
        this.scheduledTime = dateToStr(scheduledTime);
        this.infoLevel = infoLevel.toString();
        this.headName = headName;
        this.notesContent = notesContent;
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToStr(Date time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            return format.format(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getInfoID() {
        return infoID;
    }

    public long getInfoIDlong(){
        return Long.parseLong(this.infoID);
    }

    public void setInfoID(String infoID) {
        this.infoID = infoID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getToDoItem() {
        return toDoItem;
    }

    public void setToDoItem(String toDoItem) {
        this.toDoItem = toDoItem;
    }

    public String getProgressBar() {
        return progressBar;
    }

    public Long getProgressbarLong(){
        return Long.valueOf(this.progressBar);
    }

    public void setProgressBar(String progressBar) {
        this.progressBar = progressBar;
    }

    public String getStatus() {
        return status;
    }

    public Long getStatusLong(){
        return Long.valueOf(this.status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public Date getScheduledTimeDate(){
        return strToDate(this.scheduledTime);
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getInfoLevel() {
        return infoLevel;
    }

    public Long getInfolevelLong(){
        return Long.valueOf(this.infoLevel);
    }

    public void setInfoLevel(String infoLevel) {
        this.infoLevel = infoLevel;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getNotesContent() {
        return notesContent;
    }

    public void setNotesContent(String notesContent) {
        this.notesContent = notesContent;
    }

}
