package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.service.IPostService;
import com.project.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiController {

    private final ICommentService commentService;
    private final IPostService postService;

    @GetMapping(value = "replyList")
    public List<CommentVO> getReplyList(@RequestParam int commentSeq) throws Exception {
        return commentService.getReplyList(commentSeq);
    }

    @PostMapping(value = "postLikeInc")
    public void postLikeInc(@RequestParam int postSeq) throws Exception {
        postService.postLikeInc(postSeq);
    }

    @DeleteMapping(value = "postLikeDec")
    public void postLikeDec(@RequestParam int postSeq) throws Exception {
        postService.postLikeDec(postSeq);
    }
}
