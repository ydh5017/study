package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.Impl.UserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class MainController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/index";
    }
}
