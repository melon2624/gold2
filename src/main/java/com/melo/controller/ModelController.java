package com.melo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangxin
 * @date 2025-04-26 21:48
 */

@RequestMapping("/goldPage")
@RestController
public class ModelController {


    @RequestMapping("/price")
    public ModelAndView price(HttpServletRequest request){
        ModelAndView price=new ModelAndView("price");




        return price;
    }

    @RequestMapping("/login")
    public ModelAndView login(){

        ModelAndView login=new ModelAndView("login");

        return login;
    }








}
