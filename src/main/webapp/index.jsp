<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2019/7/24
  Time: 11:42
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
    <title>表格主页</title>

    <!-- 引入Bootstrap样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/statics/bootstrap/css/bootstrap.min.css">

    <!-- 引入Bootstrap-table样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/statics/dist/bootstraptablemin.css">

    <!-- 引入Bootstrap-selected样式-->
    <link href="<%=request.getContextPath() %>/statics/bootstrapselect/css/bootstrap-select.min.css" rel="stylesheet" />

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

    <script src="<%=request.getContextPath() %>/statics/bootstrapselect/js/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath() %>/statics/bootstrapselect/js/i18n/defaults-zh_CN.min.js"></script>

    <script>
        var itemOperationCode = 0;//0代表添加、1代表修改、
        var modelListJson = {};
        var headListJson = {};


        //以下全局变量需要在每次转换查看模式时清空、
        var isQueryChange = false;//表示查看模式改变、需要重置分页、
        var searchStr = "";
        var labelQueryData = {
            status: 0,
            infoLevel: 0,
            modelID: 0,
            headID: 0
        };

        function reSetScanModel(){
            isQueryChange = true;
            searchStr = "";
            labelQueryData.status = 0;
            labelQueryData.infoLevel = 0;
            labelQueryData.modelID = 0;
            labelQueryData.headID = 0;
            $("#table").bootstrapTable("refreshOptions",{pageNumber:1});//改变查看模式后、重置分页到第一页、
        }

        $(function () {
            //页面加载完成后执行、否则会出现页面未加载完成找不到modelCheck等下拉框标签、页面渲染出错的bug、

            $.ajax({//获取所有model、
                type: "POST",
                cathe: false,
                dataType: "json",
                url: "modelOperation_getAllModel.action",
                success: function (value) {
                    //这里的value就是返回数据json、
                    modelListJson = value;
                    for(var modelIndex in value){
                        //index+1、防止出现默认选择、
                        var newOption = '<option value="'+ modelIndex + '">'+ value[modelIndex]['modelname'] +'</option>';
                        $('#modelCheck').append(newOption);
                        $('#modelCheckItem').append(newOption);
                    }
                    $('#modelCheck').selectpicker('refresh');
                    $('#modelCheck').selectpicker('render');

                    $('#modelCheckItem').selectpicker('refresh');
                    $('#modelCheckItem').selectpicker('render');
                },
                error: function (value) {
                    alert("服务器可能发生异常、请联系管理员、")
                }
            });

            $.ajax({//获取所有head、
                type: "POST",
                cathe: false,
                dataType: "json",
                url: "headOperation_getAllHead.action",
                success: function (value) {
                    //这里的value就是返回数据json、
                    headListJson = value;
                    for(var headIndex in value){
                        //index+1、防止出现默认选择、
                        var newOption = '<option value="'+ headIndex + '">'+ value[headIndex]['headname'] +'</option>';
                        $('#headCheck').append(newOption);
                        $('#headCheckItem').append(newOption);
                    }
                    $('#headCheck').selectpicker('refresh');
                    $('#headCheck').selectpicker('render');

                    $('#headCheckItem').selectpicker('refresh');
                    $('#headCheckItem').selectpicker('render');
                },
                error: function (value) {
                    alert("服务器可能发生异常、请联系管理员、")
                }
            });
        });

    </script>
    <script src="<%=request.getContextPath() %>/statics/js/infoOperation.js"></script>

    <%--导航--%>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">主页</a></li>
        <li><a href="modelOperation_modelAdminPage.action">模块管理</a></li>
        <li><a href="headOperation_headAdminPage.action">负责人管理</a></li>
    </ul>

    <%--按钮组--%>
    <div class="btn-group">
        <button type="button" class="btn btn-primary btn-lg" id="addItemButton">新增</button>
        <button type="button" class="btn btn-primary btn-lg" id="labelQueryButton">筛选</button>
    </div>

    <%--按钮组对应的js--%>
    <script>
        $('#addItemButton').click(
            function () {
                itemOperationCode = 0;
                /*这里需要先清空数据、可能有残余数据在input中、*/
                $('#infoIDInput').val('');
                $('#modelCheckItem').selectpicker('val', '');
                $('#toDoItemInput').val('');
                $('#notesInput').val('');
                $('#levelCheckItem').selectpicker('val', '');
                $('#headCheckItem').selectpicker('val', '');
                $('#scheduledTimeCheckItem').val("2019-07-30");//row.scheduledTime);
                $('#progressBarCheckItem').selectpicker('val', '');
                $('#statusCheckItem').selectpicker('val', '');
                $('#itemModalTitle').text('修改待办事项');
                $('#itemModal').modal('show');
            }
        );
        $('#labelQueryButton').click(
            function () {
                $('#labelQueryModal').modal('show');
            }
        );
    </script>

    <%--搜索框--%>
    <div class="input-group" style="padding: 20px 500px 20px;">
        <input type="text" class="form-control" id="searchStrInput" placeholder="" onkeydown="onKeyDown(event)">
        <span class="input-group-btn">
						<button class="btn btn-default" type="button" id="searchButton">
							search
						</button>
        </span>
    </div>

    <%--搜索框js--%>
    <script type="text/javascript">
        $('#searchButton').click(
            function () {
                reSetScanModel();
                searchStr = $('#searchStrInput').val();
                searchPost();
            }
        );
        function onKeyDown(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==13){ // enter 键
                reSetScanModel();
                searchStr = $('#searchStrInput').val();
                searchPost();
            }
        }

        function searchPost() {
            //构造搜索json、
            var keyWord = $('#searchStrInput').val();
            var params = {
                url: 'infoOperation_getAllItemOrSearch.action',
                query : {
                    searchStr : keyWord
                }
            };
            //刷新table、
            $('#table').bootstrapTable('refresh', params);
        }

    </script>

    <%--筛选栏--%>
    <div class="labelQuery well well-sm">
    </div>

    <table id="table"></table>
    <script>
        $('#table').bootstrapTable({
            url: 'infoOperation_getAllItemOrSearch.action',
            striped: true,
            pageNumber: 1,
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
            queryParamsType:'limit',//查询参数组织方式
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            showColumns: true,
            fixedColumns: true,
            fixedNumber: 1,
            uniqueId: "infoID",

            columns: [{
                field: 'infoID',
                title: '序号',
                align:'center',
                formatter: function (value, row, index) {
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    return (pageNumber - 1) * pageSize + index + 1;
                    // return index + 1;
                }
            }, {
                field: 'modelName',
                title: '模块',
                align:'center',
                // sortable: true,
            }, {
                field: 'toDoItem',
                title: '待办事项',
                align:'center',
            },{
                field: 'progressBar',
                title: '进度',
                align:'center',
                // sortable: true,
                formatter: function (value, row, index) {
                    return '<div class="progress progress-striped">\n' +
                        '    <div class="progress-bar" role="progressbar" aria-valuenow="60" \n' +
                        '        aria-valuemin="0" aria-valuemax="100" style="width: ' + value * 10 + '%;">\n' +
                        '    </div>\n' +
                        '</div>\n' +
                        '<p style="margin: 0px 20px 0px 20px;">' + value * 10 + '%</p>';
                }
            },{
                field: 'scheduledTime',
                title: '预计完成时间',
                align:'center',
                // sortable: true,
                formatter: function (value, row, index) {
                    return getFormatDate(value);
                }
            },{
                field: 'status',
                title: '状态',
                align:'center',
                // sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case "1":
                            return "未启动";
                        case "2":
                            return "进行中";
                        case "3":
                            return "已完成";
                        default:
                            return "出错";
                    }
                }
            },{
                field: 'notesContent',
                title: '备注',
                align:'center',
            },{
                field: 'infoLevel',
                title: '优先级',
                align:'center',
                // sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case "1":
                            return '<p class="text-primary">普通</p>';
                        case "2":
                            return '<p class="text-warning">紧急</p>';
                        case "3":
                            return '<p class="text-danger">最紧急</p>';
                        default:
                            return "出错";
                    }
                }

            },{
                field: 'headName',
                title: '负责人',
                align:'center',
                // sortable: true,
            },{
                title:'操作',
                field:'Button',
                align:'center',
                formatter:AddFunctionAlty//添加按钮
            }],
            responseHandler : function (result) {
                //获得服务器返回数据后、可以在这里进行预处理、
                //筛选后关闭筛选模态框、
                $('#labelQueryModal').modal('hide');
                console.log(result);
                return result;
            }
        });

        //列表行‘操作’按钮
        function AddFunctionAlty(value, row, index) {
            return '<button id="updateItemButton" type="button" class="btn btn-default" onclick="update(' + row.infoID +')">修改</button>' +
                '<button id="TableView" type="button" class="btn btn-default" onclick="del(' + row.infoID +')" style="margin-left: 10px;">删除</button>';

        }
        //请求服务数据时所传查询参数
        function queryParams(params){
            var params = {
                'pagingData.offset' : (isQueryChange) ? 0 : params.offset,
                'pagingData.limit' : (isQueryChange) ? $('#table').bootstrapTable('getOptions').pageSize : params.limit,
                'searchStr' : searchStr,
                'labelQueryData.status' : labelQueryData.status,
                'labelQueryData.infoLevel' : labelQueryData.infoLevel,
                'labelQueryData.modelID' : labelQueryData.modelID,
                'labelQueryData.headID' : labelQueryData.headID
            };
            isQueryChange = false;//重置标志、
            return params;
        }

/*        $('#table').on('load-success.bs.table', function (data) {
            // bootstrap table 加载完成之后才执行的函数、在这里为表头添加下拉框进行标签筛选、
            console.log(data);

            //清除上一次加载的option、
            $("#modelCheck > option").each(
                function () {
                    var id = $(this).attr("value");
                    if(id !=-1){
                        this.remove();
                    }
                }
            );
            //获取
        });*/
    </script>


    <!-- 信息模态框（Modal） -->
    <div class="modal fade" id="itemModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="itemModalTitle">新增待办事项</h4>
                </div>
                <div class="modal-body">
                    <div style="padding: 10px 100px 10px;">
                        <form class="bs-example bs-example-form" role="form" action="">
                            <input type="hidden" value="0" id="infoIDInput">
                            <div class="input-group">
                                <span class="input-group-addon">模块</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="modelInput">--%>
                                <select class="selectpicker form-control" id="modelCheckItem" data-style="btn-info" data-live-search="true">
                                    <option style='display: none'></option>
                                </select>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">待办事项</span>
                                <input type="text" class="form-control" placeholder="click here" id="toDoItemInput">
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">备注</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="notesInput">--%>
                                <textarea class="form-control" rows="3" id="notesInput"></textarea>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">优先级</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="levelInput">--%>
                                <select class="selectpicker form-control" id="levelCheckItem" data-style="btn-info">
                                    <option style='display: none'></option>
                                    <option value="1">普通</option>
                                    <option value="2">紧急</option>
                                    <option value="3">最紧急</option>
                                </select>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">负责人</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="headInput">--%>
                                <select class="selectpicker form-control" id="headCheckItem" data-style="btn-info" data-live-search="true">
                                    <option style='display: none'></option>
                                </select>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">预计完成时间</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="scheduledTimeInput">--%>
                                <input type="date" class="form-control" id="scheduledTimeCheckItem" value="2019-07-30" max="2099-12-31" min="2019-07-30"/>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">进度</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="progressbarInput">--%>
                                <select class="selectpicker form-control" id="progressBarCheckItem" data-style="btn-info">
                                    <option style='display: none'></option>
                                    <option value="0">0%</option>
                                    <option value="1">10%</option>
                                    <option value="2">20%</option>
                                    <option value="3">30%</option>
                                    <option value="4">40%</option>
                                    <option value="5">50%</option>
                                    <option value="6">60%</option>
                                    <option value="7">70%</option>
                                    <option value="8">80%</option>
                                    <option value="9">90%</option>
                                    <option value="10">100%</option>
                                </select>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon">状态</span>
<%--                                <input type="text" class="form-control" placeholder="click here" id="statusInput">--%>
                                <select class="selectpicker form-control" id="statusCheckItem" data-style="btn-info">
                                    <option style='display: none'></option>
                                    <option value="1">未启动</option>
                                    <option value="2">进行中</option>
                                    <option value="3">已完成</option>
                                </select>
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

        /*jquery注册点击事件、便于使用this、*/
        /*点击了提交按钮、*/
        $('#add-submit').click(
            function () {
                //判断是否选择正确、
                if($('#modelCheckItem').val() == "" || $('#headCheckItem').val() == "" || $('#levelCheckItem').val() == "" || $('#statusCheckItem').val() == "" || $('#toDoItemInput').val().trim() == ""){//没有选择、
                    console.log($('#modelCheckItem').val());
                    console.log($('#headCheckItem').val());
                    alert("您还没有填写完整、请检查、")
                    return;
                }
                //通过全局变量获取操作类型、
                switch(itemOperationCode){
                    case 0:
                        var row = {
                            infoID :     "0",
                            modelName :     modelListJson[$('#modelCheckItem').val()]['modelname'],
                            toDoItem :     $('#toDoItemInput').val(),
                            notesContent :     $('#notesInput').val(),
                            infoLevel :     $('#levelCheckItem').val(),
                            headName : headListJson[$('#headCheckItem').val()]['headname'],
                            scheduledTime :     $('#scheduledTimeCheckItem').val() + " 00:00:00",
                            progressBar :     $('#progressBarCheckItem').val(),
                            status :     $('#statusCheckItem').val()
                        };
                        addPost(row);
                        break;
                    case 1:
                        var row = {
                            infoID :     $('#infoIDInput').val(),
                            modelName :     modelListJson[$('#modelCheckItem').val()]['modelname'],
                            toDoItem :     $('#toDoItemInput').val(),
                            notesContent :     $('#notesInput').val(),
                            infoLevel :     $('#levelCheckItem').val(),
                            headName : headListJson[$('#headCheckItem').val()]['headname'],
                            scheduledTime :     $('#scheduledTimeCheckItem').val() + " 00:00:00",
                            progressBar :     $('#progressBarCheckItem').val(),
                            status :     $('#statusCheckItem').val()
                        };
                        updatePost(row);
                        break;
                }
            }
        );

        //选择进度自动生成状态、
        $('#progressBarCheckItem').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
            // do something...
            //这里的index不是value、从1开始、
            if(clickedIndex == null){
                return;
            }
            if(clickedIndex == 1){
                //未启动、
                $('#statusCheckItem').selectpicker('val', 1);
            }
            else if(clickedIndex == 11){
                //已完成、
                $('#statusCheckItem').selectpicker('val', 3);
            }
            else {
                //进行中、
                $('#statusCheckItem').selectpicker('val', 2);
            }
        });

        //选择状态、检查进度、提示修改、
        $('#statusCheckItem').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
            // do something...
            //这里的index不是value、从1开始、
            if(clickedIndex == null){
                return;
            }
            if(clickedIndex == 1){
                //未启动、
                $('#progressBarCheckItem').selectpicker('val', 0);
            }
            else if(clickedIndex == 3){
                //已完成、
                $('#progressBarCheckItem').selectpicker('val', 10);
            }
            else {
                //进行中、
                $('#progressBarCheckItem').selectpicker('val', 5);
            }
        });
    </script>

    <!-- 标签筛选模态框（Modal） -->
    <div class="modal fade" id="labelQueryModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="labelQueryModalTitle">标签筛选</h4>
                </div>
                <div class="modal-body">
                    <div style="padding: 10px 100px 10px;">
                        <div style="margin-bottom: 10px;">
                            <span style="width: 100px; display:-moz-inline-box; /* css注释：for ff2 */ display:inline-block;">状态：</span>
                            <select class="selectpicker" id="statusCheck" data-style="btn-info">
                                <option style='display: none'></option>
                                <option value="1">未启动</option>
                                <option value="2">进行中</option>
                                <option value="3">已完成</option>
                            </select>
                        </div>
                        <div style="margin-bottom: 10px;">
                            <span style="width: 100px; display:-moz-inline-box; /* css注释：for ff2 */ display:inline-block;">优先级：</span>
                            <select class="selectpicker" id="levelCheck" data-style="btn-info">
                                <option style='display: none'></option>
                                <option value="1">普通</option>
                                <option value="2">紧急</option>
                                <option value="3">最紧急</option>
                            </select>
                        </div>
                        <div style="margin-bottom: 10px;">
                            <span style="width: 100px; display:-moz-inline-box; /* css注释：for ff2 */ display:inline-block;">模块：</span>
                            <select class="selectpicker" id="modelCheck" data-style="btn-info" data-live-search="true">
                                <option style='display: none'></option>
                            </select>
                        </div>
                        <div style="margin-bottom: 10px;">
                            <span style="width: 100px; display:-moz-inline-box; /* css注释：for ff2 */ display:inline-block;">负责人：</span>
                            <select class="selectpicker" id="headCheck" data-style="btn-info" data-live-search="true">
                                <option style='display: none'></option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <%--重置按钮--%>
                    <button type="button" class="btn btn-danger" id="labelReset">重置</button>
                    <%--这里使用ajax提交表单数据、--%>
                    <button type="button" class="btn btn-primary" id="labelQuerySubmit">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!--标签筛选模态框的js-->
    <script>
        $('#labelQuerySubmit').click(
            function () {
                var status = $('#statusCheck').val();
                var infoLevel = $('#levelCheck').val();
                var modelID = ($('#modelCheck').val() != '') ? modelListJson[$('#modelCheck').val()]['modelid'] : -1;
                var headID = ($('#headCheck').val() != '') ? headListJson[$('#headCheck').val()]['headid'] : -1;
                //构造标签json、
                var params = {
                    url: 'infoOperation_labelQueryItem.action',
                    query : {
                        'labelQueryData.status' : status,
                        'labelQueryData.infoLevel' : infoLevel,
                        'labelQueryData.modelID' : modelID,
                        'labelQueryData.headID' : headID

                    }
                };
                reSetScanModel();
                labelQueryData.status = status;
                labelQueryData.infoLevel = infoLevel;
                labelQueryData.modelID = modelID;
                labelQueryData.headID = headID;
                //刷新table、
                $('#table').bootstrapTable('refresh', params);
            }
        );

        $('#labelReset').click(
            function () {
                $('#statusCheck').selectpicker('val', '');
                $('#levelCheck').selectpicker('val', '');
                $('#modelCheck').selectpicker('val', '');
                $('#headCheck').selectpicker('val', '');
            }
        );



    </script>

</body>
</html>