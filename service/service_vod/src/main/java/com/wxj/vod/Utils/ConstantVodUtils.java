package com.wxj.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils  implements InitializingBean {

          @Value("${aliyun.vod.file.keyid}")
          private String KeyId;

         @Value("${aliyun.vod.file.keysecret}")
          private String KeySecret;

         public static  String ACCESS_KEY_Secret;
         public static  String ACCESS_KEY_ID;


    @Override
    public void afterPropertiesSet() throws Exception {

        ACCESS_KEY_Secret = KeySecret;
        ACCESS_KEY_ID =KeyId;

    }
}
