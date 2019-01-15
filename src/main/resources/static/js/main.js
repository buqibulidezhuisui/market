"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function() {
    // 菜单事件
    $(".blog-menu .list-group-item").click(function() {
        var url = $(this).attr("url");

        // 先移除其他的点击样式，再添加当前的点击样式
        $(".blog-menu .list-group-item").removeClass("active");
        $(this).addClass("active");

        location.href = url;
    });
});