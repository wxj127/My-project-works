package com.wxj.edu.Client;

import com.wxj.orderVo.MemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")  //生成者的注册服务名称
@Component
public interface test {
    @GetMapping("/ucenter/member/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
