package com.wxj.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.JwtUtils;
import com.wxj.R;
import com.wxj.orderVo.MemberOrder;
import com.wxj.center.entity.Member;
import com.wxj.center.entity.vo.RegisterVo;
import com.wxj.center.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
   private MemberService memberService;


    //前端登陆
    @PostMapping("login")
    public R loginUser(@RequestBody Member member){
      String token = memberService.login(member);
        return R.Ok().data("token",token);
    }
    //前端注册方法
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
       memberService.register(registerVo);
        return R.Ok();
    }
   //根据token获取用户
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用工具类方法获取id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id查询用户信息
        Member member = memberService.getById(memberId);

        return R.Ok().data("userInfo",member);
    }
    //根据用户id 获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable("id") String id) {
        Member member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        MemberOrder ucenterMemberOrder = new MemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
   //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
      Integer  num = memberService.countRegisterDay(day);
        return R.Ok().data("num",num);
    }

}

