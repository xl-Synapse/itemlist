package com.dealt.action.operation;

import com.dealt.entity.ModelEntity;
import com.dealt.service.ModelService;
import com.dealt.tool.OperationResult;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller("modelOperationAction")
@Scope("prototype")
public class ModelOperationAction extends ActionSupport {
    @Resource
    private ModelService modelService;

    private List<ModelEntity> modelEntities;

    private ModelEntity modelEntity;

    private OperationResult operationResult;

    private long modelID;

    public long getModelID() {
        return modelID;
    }

    public void setModelID(long modelID) {
        this.modelID = modelID;
    }

    public List<ModelEntity> getModelEntities() {
        return modelEntities;
    }

    public void setModelEntities(List<ModelEntity> modelEntities) {
        this.modelEntities = modelEntities;
    }

    public ModelEntity getModelEntity() {
        return modelEntity;
    }

    public void setModelEntity(ModelEntity modelEntity) {
        this.modelEntity = modelEntity;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private void createOperationResult(boolean isSuccess){
        this.operationResult = new OperationResult();
        this.operationResult.setResultCode((isSuccess) ? 200 : -1);
    }

    public String addModel(){
        boolean isSuccess = modelService.addModel(modelEntity.getModelname());
        createOperationResult(isSuccess);
        return "addModelSuccess";
    }

    public String delModel(){
        boolean isSuccess = modelService.delModel(modelID);
        createOperationResult(isSuccess);
        return "delModelSuccess";
    }

    public String updateModel(){
        boolean isSuccess = modelService.updateModel(modelEntity);
        createOperationResult(isSuccess);
        return "updateModelSuccess";
    }

    public String getAllModel(){
        this.modelEntities = modelService.getAllModel();
        return "getALLModelSuccess";
    }

    public String modelAdminPage(){
        return "modelAdminPage";
    }

    public String returnIndex(){
        return "returnIndex";
    }
}
