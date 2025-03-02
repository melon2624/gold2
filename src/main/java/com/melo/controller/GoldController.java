package com.melo.controller;

import com.melo.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxin
 * @date 2025-03-02 17:05
 */

@RequestMapping("/gold")
@RestController
@Slf4j
public class GoldController {


    @RequestMapping("/test")
    public  void  test(){
        System.out.println("qqqqqqqqqqqqqqqqqqqqqqq");
    }

    @Autowired
    EmailUtil emailUtil;

    @RequestMapping("/sendEmail")
    public  void sendEmail(){
        emailUtil.sendMessage("1415458002@qq.com","test","test");
    }


}
