package com.cy.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class PageController {
        @RequestMapping("doIndexUI")
        public String doIndexUI(){
                return "starter";
        }
        @RequestMapping("log/log_list")
        public String doLogUI() {
        	return "sys/log_list";
        }
        @RequestMapping("doPageUI")
        public String doPageUI() {
        	return "common/page";
        }
        @RequestMapping("menu/menu_list")
        public String doMenuUI() {
        	return "sys/menu_list";
        } 
        @RequestMapping("doLoginUI")
        public String doLoginUI(){
                        return "login";
        }
        //rest风格的url定义
        //语法格式:/{a}/{b}/...;其中{}中的内容可理解为一个变量值
        //@PathVariable注解用于获取url中与方法参数相同的变量值
        @RequestMapping("{module}/{moduleUI}")
        public String doModuleUI(@PathVariable String moduleUI) {
                        return "sys/"+moduleUI;
        }
}
