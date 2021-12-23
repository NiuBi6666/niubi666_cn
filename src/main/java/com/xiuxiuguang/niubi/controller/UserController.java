package com.xiuxiuguang.niubi.controller;


import com.xiuxiuguang.niubi.pojo.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: UserController
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/9/26     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Controller
public class UserController {
    //
    //@PostMapping("/login")
    //public String login(String username, String password) {
    //    log.info("username==" + username);
    //    log.info("password==" + password);
    //    return "html/office/index";
    //}

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    //@RequestMapping("/test1")
    //public String test1() {
    //    return "test1";
    //}
    //
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@RequestMapping("/test2")
    //public String test2() {
    //    return "test2";
    //}
    //
    @GetMapping("/login")
    public String login(@PathVariable(value = "username") String username, @PathVariable(value = "password") String password) {
        System.out.println(username + password);
        return username;
    }
}