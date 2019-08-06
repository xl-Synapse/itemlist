function del(infoID) {
    var data = {"infoID" : infoID};
    $.ajax({
        type: "POST",
        cache: false,
        dataType:"json",
        url: "infoOperation_delItem.action" ,
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、刷新表格、
                $('#table').bootstrapTable('refresh');
            }
            else {
                //失败、
                alert("操作失败、请检查、");
            }
        },
        error: function (value) {
            alert("服务器可能发生异常、请联系管理员、")
        }
    });
}

function addPost(row) {
    /*            var row = $('#table').bootstrapTable('getRowByUniqueId',infoID);//行的数据
                alert(row.modelName);*/
    var data = {
        "itemEntity.infoID" : row.infoID,
        "itemEntity.modelName" : row.modelName,
        "itemEntity.toDoItem" : row.toDoItem,
        "itemEntity.progressBar" : row.progressBar,
        "itemEntity.status" : row.status,
        "itemEntity.scheduledTime" : row.scheduledTime,
        "itemEntity.infoLevel" : row.infoLevel,
        "itemEntity.headName" : row.headName,
        "itemEntity.notesContent" : (row.notesContent.trim() == "") ? "无" : row.notesContent
    };

    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "infoOperation_addItem.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#itemModal').modal('hide');
                $('#table').bootstrapTable('refresh');
            }
            else {
                //失败、
                alert("操作失败、请检查、");
            }
        },
        error: function (value) {
            alert("服务器可能发生异常、请联系管理员、")
        }
    });
}

function update(infoID) {
    var row = $('#table').bootstrapTable('getRowByUniqueId',infoID);//行的数据
    var modelIndex = getModelIndex(row.modelName);
    var headIndex = getHeadIndex(row.headName);
    /*数据填充到模态框、并修改操作Code、*/
    /*这里不需要事先清空数据、因为会全部覆盖、*/
    itemOperationCode = 1;
    $('#infoIDInput').val(row.infoID);
    $('#modelCheckItem').selectpicker('val', modelIndex);
    $('#toDoItemInput').val(row.toDoItem);
    $('#notesInput').val(row.notesContent);
    $('#levelCheckItem').selectpicker('val', row.infoLevel);
    $('#headCheckItem').selectpicker('val', headIndex);
    $('#scheduledTimeCheckItem').val(getFormatDate(row.scheduledTime));
    $('#progressBarCheckItem').selectpicker('val', row.progressBar);
    $('#statusCheckItem').selectpicker('val', row.status);
    $('#itemModalTitle').text('修改待办事项');
    $('#itemModal').modal('show');
}

function getModelIndex(modelName) {
    for(var modelIndex in modelListJson){
        if(modelListJson[modelIndex]['modelname'] == modelName){
            return modelIndex;
        }
    }
}

function getHeadIndex(headName) {
    for(var headIndex in headListJson){
        if(headListJson[headIndex]['headname'] == headName){
            return headIndex;
        }
    }
}

function getFormatDate(date) {
    return date.split(' ')[0];
}

function updatePost(row) {
    var data = {
        "itemEntity.infoID" : row.infoID,
        "itemEntity.modelName" : row.modelName,
        "itemEntity.toDoItem" : row.toDoItem,
        "itemEntity.progressBar" : row.progressBar,
        "itemEntity.status" : row.status,
        "itemEntity.scheduledTime" : row.scheduledTime,
        "itemEntity.infoLevel" : row.infoLevel,
        "itemEntity.headName" : row.headName,
        "itemEntity.notesContent" : (row.notesContent.trim() == "") ? "无" : row.notesContent
    };
    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "infoOperation_updateItem.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#itemModal').modal('hide');
                $('#table').bootstrapTable('refresh');
            }
            else {
                //失败、
                alert("操作失败、请检查、");
            }
        },
        error: function (value) {
            alert("服务器可能发生异常、请联系管理员、")
        }
    });
}