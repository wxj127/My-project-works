package com.wxj.cmc.controller;

import com.wxj.R;
import com.wxj.cmc.service.MsmService;
import com.wxj.cmc.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发生短信方法 根据手机发送验证码
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //1.从rides中获取验证，获取不了运行发送方法
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.Ok();
        }


        String Code= RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",Code);
       boolean isSend  = msmService.send(param,phone);
       if (isSend){
           redisTemplate.opsForValue().set(phone,Code,5, TimeUnit.MINUTES);//存入Rides并设置有效时间5分钟
           return R.Ok();
       }else {
           return R.error().message("短信发送失败！");
       }




    }

}
