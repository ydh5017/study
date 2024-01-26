package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final ICommentService commentService;

    // 댓글 작성
    @PostMapping
    public String commentAddProc(@RequestParam int postSeq, CommentVO commentVO, Model model) throws Exception {
        commentVO.setPostSeq(postSeq);
        HashMap<String, String> Map = commentService.commentAddProc(commentVO);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }

    // 댓글 수정
    @PutMapping
    public String commentModProc(CommentVO commentVO, Model model) throws Exception {
        HashMap<String, String> Map = commentService.commentModProc(commentVO);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }

    // 댓글 삭제
    @PatchMapping
    public String commentDelete(@RequestParam int commentSeq,
                                @RequestParam int postSeq,Model model) throws Exception {
        HashMap<String, String> Map = commentService.commentDelete(commentSeq, postSeq);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }

}
