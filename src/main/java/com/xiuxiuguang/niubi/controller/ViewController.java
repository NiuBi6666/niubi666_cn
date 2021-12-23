package com.xiuxiuguang.niubi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: ViewController
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/16     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Controller
public class ViewController {

    @GetMapping("/office/fileTrans")
    public String fileTrans(){
        return "html/office/fileTrans";
    }

    @GetMapping("/office/fileToBase64")
    public String fileToBase64(){
        return "html/office/fileToBase64";
    }

    @GetMapping("/office/base64ToFile")
    public String base64ToFile(){
        return "html/office/base64ToFile";
    }

    //@GetMapping("/office")
    //public String office(){
    //    return "html/office/index";
    //}

    @GetMapping("/resume")
    public String resume(){
        return "html/resume/index";
    }

    @GetMapping("/")
    public String home(){
        return "html/home/index";
    }


    @GetMapping("/clock")
    public String clock(){
        return "html/clock/clock";
    }

    @GetMapping("/login.do")
    public String login(){
        return "html/login/login";
        //return "html/resume/index";
    }
}