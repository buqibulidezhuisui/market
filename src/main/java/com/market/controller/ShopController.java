package com.market.controller;

import com.github.pagehelper.PageInfo;
import com.market.domain.Shop;
import com.market.oss.OssAliyunUtil;
import com.market.service.ShopService;
import com.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ShopController
 * @Description TODO
 * @date 19-1-16 上午11:02
 * @Author hanbing
 */


@RestController
@RequestMapping("/shop")
public class ShopController {
            public final String BASE_PATH = "~/pic";
//    model.addAttribute("page",pageInfo);
//        model.addAttribute("couponsList",list);
//        model.addAttribute("pageType","couponsIndex");
//        return new ModelAndView(coupons.isAsync() == true ? "coupons/list :: #mainContainerRepleace" : "main", "couponsModel", model);


    @Autowired
    ShopService shopService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/search")
    public ModelAndView findall(@RequestBody(required = false) Shop shop, Model model) {
        shop = shop != null ? shop : new Shop();
        PageInfo<Shop> likeBussId = shopService.findAll(shop, shop.getPageIndex());
        List<Shop> list = likeBussId.getList();
        model.addAttribute("page", likeBussId);
        model.addAttribute("shopList", list);
        model.addAttribute("pageType", "shopIndex");
        return new ModelAndView(shop.isAsync() == true ? "shop/list :: #mainContainerRepleace" : "main", "shopModel", model);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/searcher")
    public ModelAndView searchByBussId(@RequestBody(required = false) Shop shop, Model model) {
        shop = shop != null ? shop : new Shop();
        PageInfo<Shop> likeBussId = shopService.findLikeBussIdAndAddrAndName(shop, shop.getPageIndex());
        List<Shop> list = likeBussId.getList();
        model.addAttribute("page", likeBussId);
        model.addAttribute("shopList", list);
        model.addAttribute("pageType", "shopIndex");
        return new ModelAndView(shop.isAsync() == true ? "shop/list :: #mainContainerRepleace" : "main", "shopModel", model);
    }


    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("shop", new Shop());
        return new ModelAndView("shop/add", "shopModel", model);
    }

    @PostMapping
    public ResponseEntity<Response> create(Shop shop,@RequestParam("file") MultipartFile file) {

        if (shop.getId() == null) {//新增
            if (!file.isEmpty()){
                String pic_url = OssAliyunUtil.upload(file);
                shop.setPic(pic_url);
            }else{
                shop.setPic("null");
            }
            shop.setModifyAt(shop.getCreateAt());
            shop.setCode(UUID.randomUUID().toString().replace("-","").toUpperCase());
//            shop.setPic(pic_url);
//            shop.setDel(1);//1表示未删除,0表示删除
            try {

                shopService.saveT(shop);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.ok().body(new Response(false, e.getMessage()));
            }
            return ResponseEntity.ok().body(new Response(true, "新增成功"));
        }else {//修改
            shop.setModifyAt(new Date());
            if (!file.isEmpty()){
                String pic_url = OssAliyunUtil.upload(file);
                shop.setPic(pic_url);
            }

            try {

                shopService.updateById(shop);
            } catch (Exception e) {
                System.out.println("sql erro>>>>"+e.getMessage());
                return ResponseEntity.ok().body(new Response(false, e.getMessage()));
            }
            return ResponseEntity.ok().body(new Response(true, "更新成功"));
        }


    }

    @GetMapping(value = "edit/{id}")
    public ModelAndView edit(@PathVariable("id")Long id,Model model){
        Shop shop = shopService.findById(id);
        model.addAttribute("shop",shop);
        return new ModelAndView("shop/edit","shopModel",model);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            shopService.deleteUserById(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "删除成功"));
    }
}
