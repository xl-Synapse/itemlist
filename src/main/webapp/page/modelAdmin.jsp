<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2019/7/29
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>模块管理页面</title>

    <!-- 引入Bootstrap样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/statics/bootstrap/css/bootstrap.min.css">

    <!-- 引入Bootstrap-table样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/statics/dist/bootstraptablemin.css">

</head>
<body>
    <!-- jQuery -->
    <script src="<%=request.getContextPath() %>/statics/js/jquery-3.4.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="<%=request.getContextPath() %>/statics/bootstrap/js/bootstrap.min.js"></script>
    <!-- 加载Bootstrap-table -->
    <script src="<%=request.getContextPath() %>/statics/dist/bootstraptablemin.js"></script>
    <!-- 使支持中文 -->
    <script src="<%=request.getContextPath() %>/statics/dist/locale/bootstraptablezhCNmin.js"></script>

    <script>
        var modelOperationCode = 0;//0代表添加、1代表修改、
    </script>

    <script src="<%=request.getContextPath() %>/statics/js/modelOperation.js"></script>

    <%--导航--%>
    <ul class="nav nav-tabs">
        <li><a href="headOperation_returnIndex">主页</a></li>
        <li class="active"><a href="#">模块管理</a></li>
        <li><a href="headOperation_headAdminPage.action">负责人管理</a></li>
    </ul>

    <%--按钮组--%>
    <div class="btn-group">
        <button type="button" class="btn btn-primary btn-lg" id="addModelButton">新增</button>
    </div>

    <%--按钮组的js--%>
    <script>
        $('#addModelButton').click(
            function () {
                modelOperationCode = 0;
                //清空模态框输入、展示模态框、
                $('#modelIDInput').val("0");
                $('#modelInput').val('');
                $('#modelModalTitle').text('新增模块');
                $('#modelModal').modal('show');
            }
        );
    </script>

    <table id="table"></table>
    <script>
        $('#table').bootstrapTable({
            url: 'modelOperation_getAllModel.action',
            striped: true,
            pageNumber: 1,
            pageSize: 10,
            smartDisplay: true,
            search: false,
            strictSearch: true,
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            queryParams: queryParams,//请求服务器时所传的参数
            // sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            uniqueId: "modelid",

            columns: [{
                field: 'modelid',
                title: '序号',
                align:'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'modelname',
                title: '模块',
                align:'center',
            },{
                title:'操作',
                field:'Button',
                align:'center',
                formatter:AddFunctionAlty//添加按钮
            }],
            responseHandler : function (result) {
                /*                console.log(result);
                                return {
                                    total : 2,
                                    rows : result
                                };*/
                return result;
            }
        });

        //列表行‘操作’按钮
        function AddFunctionAlty(value, row, index) {
            return '<button id="updateItemButton" type="button" class="btn btn-default" onclick="update(' + row.modelid +')">修改</button>' +
                '<button id="TableView" type="button" class="btn btn-default" onclick="delPost(' + row.modelid +')" style="margin-left: 10px;">删除</button>';
        }
        //请求服务数据时所传查询参数
        function queryParams(params){
            return{
                pageSize: params.pageSize,
                pageNum:params.pageNumber,
            }
        }
    </script>

    <%--模态框--%>
    <div class="modal fade" id="modelModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="modelModalTitle">新增模块</h4>
                </div>
                <div class="modal-body">
                    <div style="padding: 10px 100px 10px;">
                        <form class="bs-example bs-example-form" role="form" action="">
                            <input type="hidden" value="0" id="modelIDInput">
                            <div class="input-group">
                                <span class="input-group-addon">模块</span>
                                <input type="text" class="form-control" placeholder="click here" id="modelInput">
                            </div>
                            <br>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <%--这里使用ajax提交表单数据、--%>
                    <button type="button" class="btn btn-primary" id="add-submit">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <script>
        $('#add-submit').click(
            function () {
                //判断是否选择正确、
                if($('#modelInput').val().trim() == ""){//没有选择、
                    alert("您还没有填写完整、请检查、")
                    return;
                }
                switch (modelOperationCode) {
                    case 0:
                        var row = {
                            modelid :     "0",
                            modelname :     $('#modelInput').val()
                        };
                        addPost(row);
                        break;
                    case 1:
                        var row = {
                            modelid :     $('#modelIDInput').val(),
                            modelname :     $('#modelInput').val()
                        };
                        updatePost(row);
                        break;
                }
            }
        );
    </script>
</body>
</html>
