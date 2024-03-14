package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.service.IPostService;
import com.project.blog.vo.CommentVO;
import com.project.blog.vo.PostVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiController {

    private final ICommentService commentService;
    private final IPostService postService;

    // 대댓글 리스트 get
    @GetMapping(value = "replyList")
    public List<CommentVO> getReplyList(@RequestParam int commentSeq) throws Exception {
        return commentService.getReplyList(commentSeq);
    }

    // 대댓글 리스트 get
    @GetMapping(value = "PopularPost")
    public List<PostVO> getPopularPost(@RequestParam String postType) throws Exception {
        return postService.getPopularPost(postType, "100");
    }

    // 게시글 좋아요 up
    @PostMapping(value = "postLikeInc")
    public void postLikeInc(@RequestParam int postSeq) throws Exception {
        postService.postLikeInc(postSeq);
    }

    // 게시글 좋아요 down
    @DeleteMapping(value = "postLikeDec")
    public void postLikeDec(@RequestParam int postSeq) throws Exception {
        postService.postLikeDec(postSeq);
    }

    // 댓글 좋아요 up
    @PostMapping(value = "commentLikeInc")
    public void commentLikeInc(@RequestParam int commentSeq) throws Exception {
        commentService.commentLikeInc(commentSeq);
    }

    // 댓글 좋아요 down
    @DeleteMapping(value = "commentLikeDec")
    public void commentLikeDec(@RequestParam int commentSeq) throws Exception {
        commentService.commentLikeDec(commentSeq);
    }

    // 답글인지 확인
    @GetMapping(value = "replyConfirm")
    public int replyConfirm(@RequestParam int commentSeq) throws Exception {
        log.info("commentSeq1 : "  + commentSeq);
        int i =  commentService.replyConfirm(commentSeq);
        log.info("commentSeq2 : "  + i);
        return i;
    }
}
