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
import java.util.List;

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
        int result;

        if (userId != null) {
            commentVO.setCommentWriteId(userId);
        } else {
            commentVO.setCommentWriteId("익명");
        }
        if (commentVO.getCommentSeq() != 0){
            commentVO.setCommentGroup(commentVO.getCommentSeq());
            result = commentMapper.replyCommentAddProc(commentVO);
        }else {
            result = commentMapper.commentAddProc(commentVO);
        }

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

    @Override
    public List<CommentVO> getCommentList(int postSeq) throws Exception {
        return commentMapper.getCommentList(postSeq);
    }

    @Override
    public List<CommentVO> getReplyList(int commentSeq) throws Exception {
        return commentMapper.getReplyList(commentSeq);
    }

    @Override
    public HashMap<String, String> commentModProc(CommentVO commentVO) throws Exception {
        HashMap<String, String> Map = new HashMap<>();

        String userId = userService.userInfo().getUserId();
        if (userId != null) {
            commentVO.setCommentChgId(userId);
        } else {
            commentVO.setCommentChgId("익명");
        }

        log.info("commentVO : " +commentVO.getCommentSeq());

        int result = commentMapper.commentModProc(commentVO);
        String msg, url;

        if (result==1) {
            msg = "수정되었습니다.";
            url = "/post/detail?no%3D" + commentVO.getPostSeq();
        } else {
            msg = "수정 실패";
            url = "/post/detail?no%3D" + commentVO.getPostSeq();
        }
        Map.put("msg", msg);
        Map.put("url", url);

        return Map;
    }

    @Override
    public HashMap<String, String> commentDelete(int commentSeq, int postSeq) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        int result = commentMapper.commentDelete(commentSeq);
        String msg, url;

        if (result==1) {
            msg = " 삭제되었습니다.";
            url = "/post/detail?no%3D" + postSeq;
        } else {
            msg = "삭제 실패";
            url = "/post/detail?no%3D" + postSeq;
        }
        map.put("msg", msg);
        map.put("url", url);

        return map;
    }
}
