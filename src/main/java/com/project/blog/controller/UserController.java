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

    // 회원가입 페이지
    @GetMapping("regist")
    public String userRegPage() {
        return "/user/userReg";
    }

    // 회원가입
    @PostMapping
    public String userRegProc(UserVO userVO, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.info("id : " + userVO.getUserId());
            log.info("password : " + userVO.getPassword());
            log.info("name : " + userVO.getUserName());
        }

        HashMap<String, String> map = userService.userRegProc(userVO);

        if (log.isDebugEnabled()) {
            log.info("map : " + map);
        }

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));
        return "/redirect";
    }

    // 로그인 페이지
    @GetMapping
    public String  loginPage() {
        return "/user/login";
    }

    // 로그인 & 회원인증
    @PostMapping("/login")
    public String login(UserVO userVO, Model model, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = userService.login(userVO);
        log.info("map : " + map);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        Cookie cookie = new Cookie("token", map.get("token"));
        cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
        cookie.setPath("/");
//        cookie.setHttpOnly();
//        cookie.setSecure();
        // TODO : Redis, LocalStorage, Cookie 차이점, 용도
        response.addCookie(cookie);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(@CookieValue(value = "token", required = false) String token,
                         HttpServletResponse response, Model model) {
        String msg, url;

        if (token != null) {
            // 쿠키 파기
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            msg = "로그아웃";
            url = "/index";
        } else {
            msg = "로그아웃 실패";
            url = "/index";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return "/redirect";
    }

    // 회원정보 페이지
    @GetMapping("/detail")
    public String userDetail(@CookieValue(value = "token", required = false) String token, Model model) throws Exception {
        UserVO userVO = new UserVO();
        if (token != null) {
            String userId = jwtTokenProvider.getUserId(token);
            if (userId != null) {
                System.out.println("userId : " + userId);
                userVO = userService.userInfo(userId);
            }
        }

        model.addAttribute("userVO", userVO);
        return "/user/userDetail";
    }

    // 회원정보 수정 페이지
    @GetMapping("/modify")
    public String userMod(@CookieValue(value = "token", required = false) String token, Model model) throws Exception {
        UserVO userVO = new UserVO();
        if (token != null) {
            String userId = jwtTokenProvider.getUserId(token);
            if (userId != null) {
                System.out.println("userId : " + userId);
                userVO = userService.userInfo(userId);
            }
        }

        model.addAttribute("userVO", userVO);
        return "/user/userMod";
    }

    // 회원정보 수정
}
