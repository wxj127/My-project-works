package com.wxj.edu.Client.Impl;

import com.wxj.R;
import com.wxj.edu.Client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//远程调用出错时执行
/*@FeignClient(name = "service-vod",fallback = vodClientImpl.class)*/
public class vodClientImpl implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除出错了");
    }
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个出错了");
    }
}
