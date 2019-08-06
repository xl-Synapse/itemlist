package com.dealt.action.operation;

import com.dealt.entity.HeadEntity;
import com.dealt.service.HeadService;
import com.dealt.tool.OperationResult;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller("headOperationAction")
@Scope("prototype")
public class HeadOperationAction extends ActionSupport {
    @Resource
    private HeadService headService;

    private OperationResult operationResult;

    private HeadEntity headEntity;

    private List<HeadEntity> headEntities;

    private long headID;

    public long getHeadID() {
        return headID;
    }

    public void setHeadID(long headID) {
        this.headID = headID;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public HeadEntity getHeadEntity() {
        return headEntity;
    }

    public void setHeadEntity(HeadEntity headEntity) {
        this.headEntity = headEntity;
    }

    public List<HeadEntity> getHeadEntities() {
        return headEntities;
    }

    public void setHeadEntities(List<HeadEntity> headEntities) {
        this.headEntities = headEntities;
    }

    private void createOperationResult(boolean isSuccess){
        this.operationResult = new OperationResult();
        this.operationResult.setResultCode((isSuccess) ? 200 : -1);
    }

    public String addHead(){
        boolean isSuccess = headService.addHead(headEntity.getHeadname());
        createOperationResult(isSuccess);
        return "addHeadSuccess";
    }

    public String delHead(){
        boolean isSuccess = headService.delHead(headID);
        createOperationResult(isSuccess);
        return "delHeadSuccess";
    }

    public String updateHead(){
        boolean isSuccess = headService.updateHead(headEntity);
        createOperationResult(isSuccess);
        return "addHeadSuccess";
    }

    public String getAllHead(){
        this.headEntities = headService.getAllHead();
        return "getALLHeadSuccess";
    }

    public String headAdminPage(){
        return "headAdminPage";
    }

    public String returnIndex(){
        return "returnIndex";
    }
}
