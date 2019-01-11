package com.market.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.market.domain.Authority;
import com.market.domain.User;
import com.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/search")
    public ModelAndView list(@RequestBody(required = false) User user, Model model) {
        user = user == null ? new User() : user;
        PageInfo<User> page = userService.findAll(user, user.getPageIndex());
        List<User> list = page.getList();    // 当前所在页面数据列表
        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        model.addAttribute("pageType", "usersIndex");
        return new ModelAndView(user.isAsync() == true ? "users/list :: #mainContainerRepleace" : "main", "userModel", model);
    }

}
