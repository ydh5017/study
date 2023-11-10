package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.Impl.UserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class mainController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping
    public String index(@CookieValue(value = "token", required = false) String token, Model model) {
        UserVO userVO = new UserVO();
        System.out.println("token : " + token);
        if (token != null) {
            String user_id = jwtTokenProvider.getUserId(token);
            System.out.println(user_id);
            userVO = userService.userInfo(user_id);
            userVO.setReleased();
        }
        System.out.println(userVO.getUser_id());
        System.out.println(userVO.isReleased());

        model.addAttribute("userVO", userVO);

        return "/index";
    }
}
