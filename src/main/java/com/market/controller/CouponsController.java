package com.market.controller;

import com.github.pagehelper.PageInfo;
import com.market.domain.Coupons;

import com.market.service.CouponsService;
import com.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @ClassName CouponsController
 * @Description TODO
 * @date 19-1-11 下午3:59
 * @Author hanbing
 */
@RestController
@RequestMapping("/coupons")
public class CouponsController {
    @Autowired
    CouponsService couponsService;

    @Autowired
    UserService userService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/search")
    public ModelAndView findAll(@RequestBody(required = false) Coupons coupons, Model model) {
        coupons = coupons != null ? coupons : new Coupons();
        PageInfo<Coupons> pageInfo = couponsService.findAll(coupons, coupons.getPageIndex());
        List<Coupons> list = pageInfo.getList();

        model.addAttribute("page", pageInfo);
        model.addAttribute("couponsList", list);
        model.addAttribute("pageType", "couponsIndex");
        return new ModelAndView(coupons.isAsync() == true ? "coupons/list :: #mainContainerRepleace" : "main", "couponsModel", model);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/search/{bussId}/{userId}")
    public ModelAndView findById(@PathVariable("bussId") String bussId, @PathVariable("userId") String userId, Model model) {
        if (bussId != null && userId != null) {
            List<Coupons> couponsByBussIdAndUserId = couponsService.findCouponsByBussIdAndUserId(bussId, userId);
        } else if (bussId != null && userId == null) {
            List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(userId);
        } else if (bussId == null && userId != null) {
            List<Coupons> couponsByBussId = couponsService.findCouponsByBussId(bussId);
        } else if (bussId == null && userId == null) {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            User u = (User) auth.getPrincipal();
            com.market.domain.User user = userService.findByUserName(u.getUsername());
            if (user.getAlipayid() != null || user.getOpenid() != null) {
                if (user.getAlipayid() != null) {
                    List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(user.getAlipayid());

                } else if (user.getOpenid() != null) {
                    List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(user.getOpenid());
                } else {
                    List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(user.getId().toString());
                }
            }

        }

        return null;
    }
}
