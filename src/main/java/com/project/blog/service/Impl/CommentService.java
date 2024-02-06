package com.project.blog.service.Impl;

import com.project.blog.mapper.CommentMapper;
import com.project.blog.mapper.LikeMapper;
import com.project.blog.service.ICommentService;
import com.project.blog.service.IUserService;
import com.project.blog.util.ResponseMapUtil;
import com.project.blog.vo.CommentVO;
import com.project.blog.vo.UserVO;
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
    private final LikeMapper likeMapper;
    private final ResponseMapUtil responseMapUtil;


    @Override
    public HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String userId = userService.userInfo().getUserId();
        int result;
        String msg, url;

        if (userId != null) {
            commentVO.setCommentWriteId(userId);

            if (commentVO.getCommentSeq() != 0){
                commentVO.setCommentGroup(commentVO.getCommentSeq());
                result = commentMapper.replyCommentAddProc(commentVO);
                commentMapper.replyCount(commentVO.getCommentSeq());
            }else {
                result = commentMapper.commentAddProc(commentVO);
            }

            if (result==1) {
                msg = "댓글 등록 성공";
                url = "/post/detail?no%3D" + commentVO.getPostSeq();
            } else {
                msg = "댓글 등록 실패";
                url = "/post/detail?no%3D" + commentVO.getPostSeq();
            }

        } else {
            msg = "로그인 후 사용해주세요.";
            url = "/post/detail?no%3D" + commentVO.getPostSeq();
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public List<CommentVO> getCommentList(int postSeq) throws Exception {
        List<CommentVO> commentList = commentMapper.getCommentList(postSeq);
        String userSeq = userService.userInfo().getUserSeq();
        int commentSeq;

        if (userSeq != null) {
            for (int i = 0; i < commentList.size(); i++) {
                commentSeq = commentList.get(i).getCommentSeq();
                commentList.get(i).setLiker(likeMapper.commentLikeCheck(Integer.parseInt(userSeq), commentSeq));
            }
        }

        return commentList;
    }

    @Override
    public List<CommentVO> getReplyList(int commentSeq) throws Exception {
        UserVO userVO = userService.userInfo();
        String userId = userVO.getUserId();
        String userSeq = userVO.getUserSeq();

        List<CommentVO> commentList = commentMapper.getReplyList(commentSeq);

        if (userId != null) {
            for (int i = 0; i < commentList.size(); i++) {
                commentList.get(i).setWriter(userId.equals(commentList.get(i).getCommentWriteId()));
                commentSeq = commentList.get(i).getCommentSeq();
                commentList.get(i).setLiker(likeMapper.commentLikeCheck(Integer.parseInt(userSeq), commentSeq));
            }
        }

        return commentList;
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
        String msg, url;
        int result = commentMapper.replyCountTotal(commentSeq);

        if (result > 0) {
            map = responseMapUtil.getResponseMap("comment.delete.reply", "post.detail", postSeq);
        } else {
            result = commentMapper.commentDelete(commentSeq);
            commentMapper.countDelete(commentSeq);
            if (result == 1) {
                map = responseMapUtil.getResponseMap("comment.delete", "post.detail", postSeq);
            } else {
                map = responseMapUtil.getResponseMap("comment.delete.error", "post.detail", postSeq);
            }
        }

        return map;
    }

    @Override
    public void commentLikeInc(int commentSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        commentMapper.likeInc(commentSeq);
        likeMapper.commentLikeInc(userSeq, commentSeq);
    }

    @Override
    public void commentLikeDec(int commentSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        commentMapper.likeDec(commentSeq);
        likeMapper.commentLikeDec(userSeq, commentSeq);
    }
}
