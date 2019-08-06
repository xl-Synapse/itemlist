package com.dealt.action.operation;

import com.dealt.entity.ItemEntity;
import com.dealt.service.ItemService;
import com.dealt.tool.ItemResultData;
import com.dealt.tool.LabelQueryData;
import com.dealt.tool.OperationResult;
import com.dealt.tool.PagingData;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller("infoOperationAction")
@Scope("prototype")
public class InfoOperationAction extends ActionSupport {

    @Resource
    private ItemService itemService;

    private ItemEntity itemEntity;

    private String infoID;

    private OperationResult operationResult;

    private List<ItemEntity> itemEntities;

    private String searchStr;

    private LabelQueryData labelQueryData;

    private PagingData pagingData;

    private ItemResultData itemResultData;

    public ItemResultData getItemResultData() {
        return itemResultData;
    }

    public void setItemResultData(ItemResultData itemResultData) {
        this.itemResultData = itemResultData;
    }

    public PagingData getPagingData() {
        return pagingData;
    }

    public void setPagingData(PagingData pagingData) {
        this.pagingData = pagingData;
    }

    public LabelQueryData getLabelQueryData() {
        return labelQueryData;
    }

    public void setLabelQueryData(LabelQueryData labelQueryData) {
        this.labelQueryData = labelQueryData;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public List<ItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(List<ItemEntity> itemEntities) {
        this.itemEntities = itemEntities;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public String getInfoID() {
        return infoID;
    }

    public void setInfoID(String infoID) {
        this.infoID = infoID;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    private void createOperationResult(boolean isSuccess){
        this.operationResult = new OperationResult();
        this.operationResult.setResultCode((isSuccess) ? 200 : -1);
    }

    public String addItem(){
        boolean isAddSuccess = itemService.addItem(itemEntity);
        createOperationResult(isAddSuccess);
        return "addItemSuccess";
    }

    public String delItem(){
        boolean isDelSuccess = this.itemService.delItem(Long.parseLong(this.infoID));
        createOperationResult(isDelSuccess);
        return "delItemSuccess";
    }

    public String updateItem(){
        boolean isUpdateSuccess = itemService.updateItem(this.itemEntity);
        createOperationResult(isUpdateSuccess);
        return "updateItemSuccess";
    }

    public String getAllItemOrSearch(){
/*        if(this.labelQueryData != null){//前端调用了分页、使得部分刷新函数到达此处、需要重定向到正确的筛选函数、
            return labelQueryItem();
        }*/
        //不指定url默认使用上一次url加载、所以在不转变查看模式的情况下、分页使用的url对应的action始终会是正确的、无需重定向、

        if(this.searchStr == null || this.searchStr.trim().equals("")){
            //不存在关键字、或忽略首尾空白后关键字为空白、全部加载、不搜索、
            //this.itemEntities = itemService.getAllItem(this.pagingData);
            this.itemResultData = itemService.getAllItem(this.pagingData);

        }
        else{
            //需要搜索、去掉首尾空格进行搜索、
            //this.itemEntities = itemService.fuzzyQueryItem(this.searchStr.trim(), this.pagingData);
            this.itemResultData = itemService.fuzzyQueryItem(this.searchStr.trim(), this.pagingData);
        }
        return "getAllItemOrSearch";
    }

    public String labelQueryItem(){
        this.itemResultData = itemService.labelQueryItemByMultiple(this.labelQueryData, this.pagingData);//itemService.lableQueryItemBysStatus(this.labelQueryData.getStatus());
        return "labelQueryItem";
    }
}
