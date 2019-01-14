package com.market.controller;

import com.github.pagehelper.PageInfo;
import com.market.domain.Authority;
import com.market.domain.User;
import com.market.domain.UserAuthority;
import com.market.service.AuthorityService;
import com.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.market.vo.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.market.util.PasswordUtil;


/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

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

    /**
     * 功能描述：获取添加用户的Form表单界面
     * @param model
     * @return
     * @author caoyong
     * @date 2019/1/11 9:33
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 功能描述：修改用户信息
     * @param id
     * @param model
     * @return
     * @author caoyong
     * @date 2019/1/11 10:00
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }

    /**
     * 功能描述：新增用户
     * @param user
     * @param authorityId
     * @return
     * @author caoyong
     * @date 2019/1/11 14:29
     */
    @PostMapping
    public ResponseEntity<Response> create(User user, Long authorityId) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);

        Date date = new Date();
        user.setModifyAt(date);
        if(user.getId() == null) {//新增用户
            user.setEncodePassword(user.getPassword()); //加密密码
            user.setCreateAt(date);
        } else {//编辑用户
            //判断密码是否修改
            User originUser = userService.findUserById(user.getId());
            boolean isMatch = PasswordUtil.checkPasswordChange(user,originUser);
            if (!isMatch) {
                user.setEncodePassword(user.getPassword());
            } else {
                user.setPassword(user.getPassword());
            }
        }

        try {
            userService.saveT(user);
            User u = userService.findByUserName(user.getUsername());
            authorityService.deleteUserAuthority(u.getId());
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setUserId(u.getId());
            userAuthority.setAuthorityId(authorityId);
            authorityService.saveUserAuthority(userAuthority);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "新增成功"));
    }

    /**
     * 功能描述：删除用户
     * @param id
     * @param model
     * @return
     * @author caoyong
     * @date 2019/1/11 11:00
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.deleteUserById(id);
        } catch(Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "删除成功"));
    }
}
