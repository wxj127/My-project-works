package com.wxj.center.service;

import com.wxj.center.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxj.center.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-27
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
