package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.service.IUserService;
import com.project.blog.vo.CommentVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final ICommentService commentService;

    // 댓글 작성
    @PostMapping
    public String postAdd(@RequestParam int postSeq, CommentVO commentVO, Model model) throws Exception {
        commentVO.setPostSeq(postSeq);
        HashMap<String, String> Map = commentService.commentAddProc(commentVO);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }
}