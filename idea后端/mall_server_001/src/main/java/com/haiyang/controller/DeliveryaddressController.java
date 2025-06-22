package com.haiyang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Result;
import com.haiyang.entity.Deliveryaddress;
import com.haiyang.service.DeliveryaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;
import java.util.List;

@RestController
@RequestMapping("/deliveryaddress")
@CrossOrigin
public class DeliveryaddressController extends BaseController {
    @Autowired
    private DeliveryaddressService dyService;

    @GetMapping("/list/{accountId}")
    public Result list(@PathVariable String accountId){
        QueryWrapper<Deliveryaddress> qw = new QueryWrapper<>();
        qw.eq("account_id",accountId);
        List<Deliveryaddress> list = dyService.list(qw);
        if(list == null){
            return Result.fail("配送信息加载失败");
        }else{
            return Result.success(list);
        }
    }
} 