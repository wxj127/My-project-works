package com.wxj.center.mapper;

import com.wxj.center.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-05-27
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterDay(String day);
}
