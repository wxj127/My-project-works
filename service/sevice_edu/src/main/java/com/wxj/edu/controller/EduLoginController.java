package com.wxj.edu.controller;

import com.wxj.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")

public class EduLoginController {

    @PostMapping("login")
    public R Login(){

      return R.Ok().data("token","admin");
    }
    @GetMapping("info")
    public R info(){

    return R.Ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
