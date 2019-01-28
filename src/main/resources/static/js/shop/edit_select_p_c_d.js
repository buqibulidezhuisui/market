$(function (){
// 				alert("123")
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var area = $("#area").val();
    var province = area.substr(0,2)+"0000";

    $.ajax({
        url:"/area/province",
        type:"post",
        data:"",
        dataType:"json",
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        success:function(str){
            // alert(str[0].full_name)
            // var select_E = document.getElementById("s1")
            var strHTML='<option value="-1">-请选择-</option>';
            for (var i = 0; i < str.length; i++) {
                // alert(province);
                if (str[i].id==(province)) {
                    strHTML += '<option selected value="' + str[i].id + '">' + str[i].full_name + '</option>';
                }

                strHTML += '<option value="' + str[i].id + '">' + str[i].full_name + '</option>';
            }
            $("#s1").html(strHTML);


        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }
    })
    var code = area.substr(0,4)+"00";
    $.ajax({
        url:"/area/code/"+code,
        type:"post",
        // data:{'parent':$(this).val()},
        dataType:"json",
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        success:function(str){
            // alert("进入去ajax")
            var strHTML='<option value="-1">-请选择-</option>';
            // alert(str[0].full_name)
            for (var i = 0; i < str.length; i++) {
                strHTML+='<option selected value="'+str[i].id+'">'+str[i].full_name+'</option>';
            }
            $("#s2").html(strHTML);
        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }
    })
    code=area;
    $.ajax({
        url:"/area/code/"+code,
        type:"post",
        // data:{'parent':$(this).val()},
        dataType:"json",
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        success:function(str){
            // alert("进入去ajax")
            var strHTML='<option value="-1">-请选择-</option>';
            // alert(str[0].full_name)
            for (var i = 0; i < str.length; i++) {
                strHTML+='<option selected value="'+str[i].id+'">'+str[i].full_name+'</option>';
            }
            $("#s3").html(strHTML);
        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }
    })
})

//省级改变，触发市级显示
$("#s1").change(function (){
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"/area/parentcode/"+$(this).val(),
        type:"post",
        // data:{'parentcode':$(this).val()},//【重点：这里动态把父级id传到后台，以键值对的方式传值】【data可以键值对传值！！！】
        dataType:"json",
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        success:function(str){
            // alert("进入市ajax")
            var strHTML='<option value="-1">-请选择-</option>';
            // alert(str[0].full_name)
            for (var i = 0; i < str.length; i++) {
                strHTML+='<option value="'+str[i].id+'">'+str[i].full_name+'</option>';
            }
            $("#s2").html(strHTML);
            $("#s3").html("<option value='-1'>-请选择-</option>");
        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }
    })

})

//市级改变，触发县
$("#s2").change(function (){
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"/area/parentcode/"+$(this).val(),
        type:"post",
        // data:{'parent':$(this).val()},
        dataType:"json",
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        success:function(str){
            // alert("进入去ajax")
            var strHTML='<option value="-1">-请选择-</option>';
            // alert(str[0].full_name)
            for (var i = 0; i < str.length; i++) {
                strHTML+='<option value="'+str[i].id+'">'+str[i].full_name+'</option>';
            }
            $("#s3").html(strHTML);
        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }

    })


})

