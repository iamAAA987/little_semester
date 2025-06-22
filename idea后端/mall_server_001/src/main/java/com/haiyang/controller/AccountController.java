package com.haiyang.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haiyang.common.Const;
import com.haiyang.common.Result;
import com.haiyang.entity.Account;
import com.haiyang.mapper.AccountMapper;
import com.haiyang.service.AccountService;
import com.haiyang.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haiyang.common.BaseController;

import java.time.LocalDateTime;

@RestController //表明这是一个Spring MVC控制器，且所有方法返回值都自动转换为JSON响应
@RequestMapping("/account") //定义控制器的基础路径，所有方法URL都会以此路径为前缀
@Slf4j //Lombok提供的注解，自动生成日志记录器(Logger)，可以使用log.info(), log.error()等方法记录日志
@CrossOrigin //用于跨域，浏览器采用同源策略（请求协议 请求IP地址 端口号 https://xxxx:yyyy但凡有一个不一样就会被拦截）
public class AccountController extends BaseController {

    @Autowired //Spring会查找合适的bean并注入到该字段，自动注入AccountService实例
    private AccountService aService;

    //手机号验证方法
    @GetMapping("/check/{accountId}") //处理HTTP GET请求，路径为/account/check/{accountId}
    public Result check(@PathVariable String accountId){
                Account account = aService.getById(accountId);
                if(account == null){
                    return Result.success("当前手机号可被注册");
                }else{
                    return Result.fail(20005,"手机号码已被注册",null);
                }
    }

    //注册方法
    @PostMapping("/register") //处理HTTP POST请求,路径为/account/register，同样紧跟控制器路径
    //@RequestBody 将提交前端提交的json对象转为java对象。必须两边属性名完全一样。
    public Result register(@RequestBody Account account){
        //对页面提交密码在此提交
        account.setPassword(MD5Utils.md5(account.getPassword()));
        account.setCreated(LocalDateTime.now());
        account.setUpdated(LocalDateTime.now());
        account.setStatu(1); //1正常
        if(account.getAccountSex() == 1){
            account.setAccountImg(Const.DEFAULT_IMG_1);
        }else{
            account.setAccountImg(Const.DEFAULT_IMG_0);
        }
        //注册，插入数据，调用save()
        aService.save(account);
        return Result.success("用户信息注册成功");
    }

    //登录方法
    @PostMapping("/login")
    public Result login(String accountId, String password) {
        log.info("手机号为 {} 用户正在登录APP端--", accountId);
        //定义查询条件
        //手机号在sys_account表中是主键，是不重复的，查询返回就是单一对象
        Account account = aService.getOne(new QueryWrapper<Account>
                ().eq("account_id", accountId));
        if (account == null) {
            return Result.fail("账户手机号码不存在");
        } else {
            //account不等于null，说明查询到该手机号码，继续比较密码是否一致
            String newPwd = MD5Utils.md5(password);  //将登录原文密码 先加密，变为 密文
            if (newPwd.equals(account.getPassword())) {
                if (account.getStatu() == 0) {
                    return Result.fail("该账户被禁用或被注销，暂不可用");
                } else {
                    //登录成功，直接反馈登录账户对象信息
                    return Result.success(account);
                }
            } else {
                return Result.fail("登录密码不正确");
            }
        }
    }
}