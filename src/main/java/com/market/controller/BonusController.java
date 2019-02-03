package com.market.controller;

import com.market.domain.BonusPool;
import com.market.service.BonusPoolService;
import com.market.util.Arith;
import com.market.util.BonusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
