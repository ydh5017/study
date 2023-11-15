package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.Impl.UserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class mainController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping
    public String index(@CookieValue(value = "token", required = false) String token, Model model,
                        HttpServletRequest request) {
        UserVO userVO = new UserVO();
        System.out.println("token : " + token);

        if (token != null) {
            String userId = jwtTokenProvider.getUserId(token);
            System.out.println(userId);
            userVO = userService.userInfo(userId);
            userVO.setReleased();
        }
        System.out.println(userVO.getUserId());
        System.out.println(userVO.isReleased());

        model.addAttribute("userVO", userVO);

        return "/index";
    }
}
