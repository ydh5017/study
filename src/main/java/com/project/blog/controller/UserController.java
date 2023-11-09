package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public String  loginPage(@CookieValue(value = "token", required = false) String token) {
        if (token != null) {
            String user_id = jwtTokenProvider.getUserId(token);
            System.out.println(user_id);
        }
        return "/user/login";
    }

    @GetMapping("userReg")
    public String userRegPage() {
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

    @GetMapping("loginProc")
    public String login(UserVO userVO, Model model, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = userService.login(userVO);
        log.info("map : " + map);

        model.addAttribute("token", map.get("token"));
        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        Cookie cookie = new Cookie("token", map.get("token"));
        cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
        response.addCookie(cookie);

        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Model model) {

        // 쿠키 파기
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "/user/login";
    }
}
