$(function(){
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    alert("累心")
    $.ajax({
        url:"/shoptype/big",
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
                strHTML+='<option value="'+str[i].id+'">'+str[i].full_name+'</option>';
            }
            $("#t1").html(strHTML);


        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }
    })
})

$("#t1").change(function (){
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"/shoptype/parentcode/"+$(this).val(),
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
            $("#t2").html(strHTML);
        },
        error:function(){
            // alert("系统错误");
            toastr.message("系统错误");
        }

    })
})