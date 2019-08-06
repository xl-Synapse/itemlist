function delPost(modelID) {
    var data = {"modelID" : modelID};
    $.ajax({
        type: "POST",
        cache: false,
        dataType:"json",
        url: "modelOperation_delModel.action" ,
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
    var data = {
        "modelEntity.infoid" : row.modelid,
        "modelEntity.modelname" : row.modelname,
    };

    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "modelOperation_addModel.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#modelModal').modal('hide');
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

function update(modelID) {
    var row = $('#table').bootstrapTable('getRowByUniqueId',modelID);//行的数据

    /*数据填充到模态框、并修改操作Code、*/
    /*这里不需要事先清空数据、因为会全部覆盖、*/
    modelOperationCode = 1;
    $('#modelIDInput').val(row.modelid);
    $('#modelInput').val(row.modelname);
    $('#modelModalTitle').text('修改模块');
    $('#modelModal').modal('show');
}

function updatePost(row) {
    var data = {
        "modelEntity.modelid" : row.modelid,
        "modelEntity.modelname" : row.modelname,
    };
    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "modelOperation_updateModel.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#modelModal').modal('hide');
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