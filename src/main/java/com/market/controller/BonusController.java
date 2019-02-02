package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.market.base.response.ServerResponse;
import com.market.domain.*;
import com.market.service.AuthorityService;
import com.market.service.BonusPoolService;
import com.market.service.UserService;
import com.market.util.Arith;
import com.market.util.BonusUtil;
import com.market.util.PasswordUtil;
import com.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 奖金
 */
@RestController
@RequestMapping("/bonus")
public class BonusController {

    @Autowired
    private BonusPoolService bonusPoolService;


    @GetMapping("/test")
    public String getBonus() {
        BonusPool bonusPoolQuery = new BonusPool();
        BonusPool bonusPool = bonusPoolService.findT(bonusPoolQuery);
        Assert.notNull(bonusPool,"奖池对象为空");
        Double bonus = BonusUtil.getBonus(bonusPool.getMoney());
        bonusPool.setMoney(Arith.sub(bonusPool.getMoney(),bonus));//更新奖池金额
        bonusPoolService.updateT(bonusPool);
        return  bonus.toString();
    }


}
