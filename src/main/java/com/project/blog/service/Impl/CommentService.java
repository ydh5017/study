package com.project.blog.service.Impl;

import com.project.blog.mapper.CommentMapper;
import com.project.blog.service.ICommentService;
import com.project.blog.service.IUserService;
import com.project.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService implements ICommentService {

    @Resource
    private final IUserService userService;
    private final CommentMapper commentMapper;


    @Override
    public HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String userId = userService.userInfo().getUserId();



        if (userId != null) {
            commentVO.setCommentWriteId(userId);
        } else {
            commentVO.setCommentWriteId("익명");
        }

        int result = commentMapper.commentAddProc(commentVO);
        String msg, url;

        if (result==1) {
            msg = "댓글 등록 성공";
            url = "/post/detail?no%3D" + commentVO.getPostSeq();
        } else {
            msg = "댓글 등록 실패";
            url = "/post/detail?no%3D" + commentVO.getPostSeq();
        }
        map.put("msg", msg);
        map.put("url", url);

        return map;
    }
}
