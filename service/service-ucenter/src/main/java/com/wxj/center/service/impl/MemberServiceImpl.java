package com.wxj.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxj.JwtUtils;
import com.wxj.MD5;
import com.wxj.servicebase.ExceptionHandler.wxjException;
import com.wxj.center.entity.Member;
import com.wxj.center.entity.vo.RegisterVo;
import com.wxj.center.mapper.MemberMapper;
import com.wxj.center.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-27
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

   //登录方法
    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机。密码有一个为空抛出异常
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new wxjException(20001,"登录失败");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null){
            throw new wxjException(20001,"手机号不存在");
        }
        //把输入密码加密之后再比较 MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new wxjException(20001,"密码错误");
        }
        if (mobileMember.getIsDisabled()){
            throw new wxjException(20001,"账户异常");
        }
        //登录成功 生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }
  //注册方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new wxjException(20001,"注册失败！");
        }
        //获取redis注册码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)){
            throw new wxjException(20001,"验证码错误！");
        }
        //判断手机号
        QueryWrapper<Member> wrapper =new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw new wxjException(20001,"手机号已经被注册了！");
        }
        Member member = new Member();
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setIsDisabled(false);

         baseMapper.insert(member);

    }

    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {

        return baseMapper.countRegisterDay(day);
    }
}
