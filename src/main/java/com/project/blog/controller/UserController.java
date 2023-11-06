package com.project.blog.controller;

import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @GetMapping
    public String  login() {
        return "/user/login";
    }

    @GetMapping("userReg")
    public String userReg() {
        return "/user/userReg";
    }

    @PostMapping
    public String userRegProc(UserVO userVO, Model model) throws Exception {
        log.info("id : " + userVO.getUser_id());
        log.info("password : " + userVO.getPassword());
        log.info("name : " + userVO.getUser_name());
        HashMap<String, String> map = userService.userRegProc(userVO);
        log.info("map : " + map);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));
        return "/redirect";
    }
}
