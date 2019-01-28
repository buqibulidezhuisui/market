// DOM 加载完再执行
$(function () {

    var _pageSize; // 存储用于搜索

    // 根据用户名、页面索引、页面大小获取用户列表
    function getShopByName(pageIndex, pageSize) {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: "/shop/searcher",
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify({
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "name": $("#searchShopName").val(),
                "code": $("#searchBussId").val(),
                "addr": $("#searchAddr").val(),

            }),
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("查询失败!");
            }
        });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getShopByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function () {

        getShopByName(0, _pageSize);

    });

    // 获取添加用户的界面
    $("#add-btn").click(function () {
        $.ajax({
            url: "/shop/add",
            success: function (data) {
                $("#form-container").html(data);
            },
            error: function (data) {
                toastr.error("添加失败!");
            }
        });
    });

    // 获取编辑用户的界面
    $("#rightContainer").on("click", ".object-edit", function () {
        $.ajax({
            url: "/shop/edit/" + $(this).attr("id"),
            success: function (data) {
                $("#form-container").html(data);
            },
            error: function () {
                toastr.error("更新失败!");
            }
        });
    });

    // 提交变更后，清空表单
    $("#submitEdit").click(function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var form = $('#shopForm')[0];

        var data = new FormData(form);
        var s3 = $("#s3").val();
        var t2 = $("#t2").val();
        data.set("area",s3);
        data.set("type",t2);
        console.log(data)
        // var s = $("#typep").val();
        alert(t2);
        $.ajax({
            url: "/shop",
            type: 'POST',
            data: data,
            contentType:'multipart/form-data',
            processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
            contentType: false,  //必须false才会自动加上正确的Content-Type
            cache: false,
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                $('#shopForm')[0].reset();

                if (data.success) {
                    // 从新刷新主界面
                    getShopByName(0, _pageSize);
                    toastr.message("添加成功")
                } else {
                    toastr.error(data.message);
                }

            },
            error: function () {
                toastr.error("提交失败!");
            }
        });
    });

    // 删除用户
    $("#rightContainer").on("click", ".object-delete", function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");


        $.ajax({
            url: "/shop/" + $(this).attr("id"),
            type: 'DELETE',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                if (data.success) {
                    // 从新刷新主界面
                    getShopByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("删除失败!");
            }
        });
    });


    $(document).on("blur","#mobile",function () {
        var mobile = $("#mobile").val();
        // alert(mobile);
        // var b=/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(mobile);
        // alert(b)
        if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(mobile)){
            toastr.error("联系方式输入有误,请重新输入!")
        }
    })



});