function delPost(headID) {
    var data = {"headID" : headID};
    $.ajax({
        type: "POST",
        cache: false,
        dataType:"json",
        url: "headOperation_delHead.action" ,
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
        "headEntity.headid" : row.headid,
        "headEntity.headname" : row.headname,
    };

    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "headOperation_addHead.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#headModal').modal('hide');
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

function update(headID) {
    var row = $('#table').bootstrapTable('getRowByUniqueId',headID);//行的数据

    /*数据填充到模态框、并修改操作Code、*/
    /*这里不需要事先清空数据、因为会全部覆盖、*/
    headOperationCode = 1;
    $('#headIDInput').val(row.headid);
    $('#headInput').val(row.headname);
    $('#headModalTitle').text('修改负责人');
    $('#headModal').modal('show');
}

function updatePost(row) {
    var data = {
        "headEntity.headid" : row.headid,
        "headEntity.headname" : row.headname,
    };
    $.ajax({
        type: "POST",
        cathe: false,
        dataType: "json",
        url: "headOperation_updateHead.action",
        data: data,
        success: function (value) {
            //这里的value就是返回数据json、
            if(value["resultCode"] == 200){
                //成功、关闭模态框、刷新表格、
                $('#headModal').modal('hide');
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