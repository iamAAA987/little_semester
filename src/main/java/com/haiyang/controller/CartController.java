package com.haiyang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Result;
import com.haiyang.entity.Cart;
import com.haiyang.entity.Goods;
import com.haiyang.mapper.CartMapper;
import com.haiyang.service.CartService;
import com.haiyang.service.GoodsService;
import com.haiyang.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haiyang.common.BaseController;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BusinessService businessService;

    @GetMapping("/listCartByAccountId/{accountId}")
    public Result listCart(@PathVariable String accountId) {
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("account_id",accountId);
        List<Cart> list = cartService.list(qw);
        return Result.success(list);
    }

    //商家详情页面 加载购物车方法
    @GetMapping("/listCart/{accountId}/{businessId}")
    public Result listCart(@PathVariable String accountId,@PathVariable Long businessId){
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("account_id",accountId);
        qw.eq("business_id",businessId);
        List<Cart> list = cartService.list(qw);
        //使用购物车中 goods_id，再次查询商品数据
        list.stream().forEach( cart ->{
            Goods goods = goodsService.getById(cart.getGoodsId());
            cart.setGoods(goods);
        });
        return Result.success(list);
    }

    //插入购物车数据
    @PostMapping("/add")
    public Result add(@RequestBody Cart cart){
        cart.setCreated(LocalDateTime.now());
        cart.setUpdated(LocalDateTime.now());
        cart.setStatu(1);
        cartService.save(cart);
        //cart.getCartId() 得到录入购物车表中 自动生成主键值。
        return Result.success(cart.getCartId());
    }

    //更新购物车数据 （数量）
    @PostMapping("/update")
    public Result update(@RequestBody Cart cart){
        cart.setUpdated(LocalDateTime.now());
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("goods_id",cart.getGoodsId());
        qw.eq("account_id",cart.getAccountId());
        cartService.update(cart,qw);
        return Result.success("购物车数更新成功");
    }
    //删除购物车数据
    @PostMapping("/remove")
    public Result remove(@RequestBody Cart cart){
        cart.setUpdated(LocalDateTime.now());
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("goods_id",cart.getGoodsId());
        qw.eq("account_id",cart.getAccountId());
        cartService.remove(qw);
        return Result.success("购物车数删除成功");
    }

    /**
     * 查询当前用户所有未结算的购物车商品，带商品和商家详情
     */
    @GetMapping("/listAllCartWithDetail/{accountId}")
    public Result listAllCartWithDetail(@PathVariable String accountId) {
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("account_id", accountId);
        // 可根据需要加状态筛选，如qw.eq("statu", 1);
        List<Cart> list = cartService.list(qw);
        list.forEach(cart -> {
            Goods goods = goodsService.getById(cart.getGoodsId());
            cart.setGoods(goods);
            // 查询商家名称
            if (cart.getBusinessId() != null) {
                var business = businessService.getById(cart.getBusinessId());
                if (business != null) {
                    cart.setBusinessName(business.getBusinessName());
                }
            }
        });
        return Result.success(list);
    }

}
