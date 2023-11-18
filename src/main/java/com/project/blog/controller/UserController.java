package com.project.blog.controller;

import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

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
        }

        HashMap<String, String> map = userService.userRegProc(userVO);

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
    public String logout(HttpServletResponse response, Model model) {
        String msg, url;

        try {
            // 쿠키 파기
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            msg = "로그아웃";
            url = "/index";
        } catch (Exception e){
            msg = "로그아웃 실패";
            url = "/index";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return "/redirect";
    }

    // 회원정보 페이지
    @GetMapping("/detail")
    public String userDetail(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/user/userDetail";
    }

    // 회원정보 수정 페이지
    @GetMapping("/modify")
    public String userMod(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/user/userMod";
    }

    // 회원정보 수정
    @PutMapping
    public String userModProc(UserVO userVO, Model model) throws Exception {
        HashMap<String, String> map = userService.userModProc(userVO);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }

    @GetMapping("pcpage")
    public String pcpage() {
        return "/user/passwordChg";
    }

    // 패스워드 변경
    @PutMapping("password")
    public String passwordModProc(String currentPassword, String newPassword, Model model) throws Exception {
        log.info("cp : " + currentPassword);
        log.info("np : " + newPassword);

        HashMap<String, String> map = userService.passwordModProc(currentPassword, newPassword);

        log.info("map : " + map);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }
}
