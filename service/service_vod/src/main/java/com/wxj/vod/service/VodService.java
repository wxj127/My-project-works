package com.wxj.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAlyVideo(MultipartFile file) throws Exception;

    void removeMoreAlyVideo(List videoIdList);

}
