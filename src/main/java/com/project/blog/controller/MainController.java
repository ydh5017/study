package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.Impl.PostService;
import com.project.blog.service.Impl.UserService;
import com.project.blog.vo.PostVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/index")
public class MainController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping
    public String index(Model model) throws Exception {
        UserVO userVO = userService.userInfo();
        List<PostVO> postList = postService.getPopularPost("week", "100");

        model.addAttribute("userVO", userVO);
        model.addAttribute("postList", postList);

        return "index";
    }
}
