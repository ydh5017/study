package com.project.blog.service.Impl;

import com.project.blog.mapper.CommentMapper;
import com.project.blog.mapper.LikeMapper;
import com.project.blog.service.ICommentService;
import com.project.blog.service.IUserService;
import com.project.blog.util.ResponseMapUtil;
import com.project.blog.vo.CommentVO;
import com.project.blog.vo.LikeVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    // 댓글 등록
    @Override
    public HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception {
        HashMap<String, String> map;
        String userId = userService.userInfo().getUserId();
        int result;

        if (userId != null) {
            commentVO.setCommentWriteId(userId);

            // 답글일 경우 모댓글 등록
            if (commentVO.getCommentSeq() != 0){
                commentVO.setCommentGroup(commentVO.getCommentSeq());
                result = commentMapper.replyCommentAddProc(commentVO);
                commentMapper.replyCount(commentVO.getCommentSeq());
            }else {
                result = commentMapper.commentAddProc(commentVO);
            }

            if (result==1) {
                map = responseMapUtil.getResponseMap("comment.add", "post.detail", commentVO.getPostSeq());
            } else {
                map = responseMapUtil.getResponseMap("comment.add.error", "post.detail", commentVO.getPostSeq());
            }

        } else {
            map = responseMapUtil.getResponseMap("user.login.error", "post.detail", commentVO.getPostSeq());
        }

        return map;
    }

    // 댓글 리스트 get
    @Override
    public List<CommentVO> getCommentList(int postSeq) throws Exception {
        List<CommentVO> commentList = commentMapper.getCommentList(postSeq);
        String userSeq = userService.userInfo().getUserSeq();
        int commentSeq;

        if (userSeq != null) {
            for (int i = 0; i < commentList.size(); i++) {
                commentSeq = commentList.get(i).getCommentSeq();
                // 좋아요한 회원인지 확인
                commentList.get(i).setLiker(likeMapper.commentLikeCheck(Integer.parseInt(userSeq), commentSeq));
            }
        }

        return commentList;
    }

    // 답글 리스트 get
    @Override
    public List<CommentVO> getReplyList(int commentSeq) throws Exception {
        UserVO userVO = userService.userInfo();
        String userId = userVO.getUserId();
        String userSeq = userVO.getUserSeq();

        List<CommentVO> commentList = commentMapper.getReplyList(commentSeq);

        if (userId != null) {
            for (int i = 0; i < commentList.size(); i++) {
                // 답글 작성자인지 확인
                commentList.get(i).setWriter(userId.equals(commentList.get(i).getCommentWriteId()));
                commentSeq = commentList.get(i).getCommentSeq();
                // 좋아요한 회원인지 확인
                commentList.get(i).setLiker(likeMapper.commentLikeCheck(Integer.parseInt(userSeq), commentSeq));
            }
        }

        return commentList;
    }

    // 마이페이지 댓글 미리보기 리스트
    @Override
    public List<CommentVO> getMypageComment(String mypageType) throws Exception {
        UserVO userVO = userService.userInfo();
        HashMap<String, Object> map = new HashMap<>();
        map.put("mypageType", mypageType);
        map.put("userId", userVO.getUserId());

        List<CommentVO> commentList = new ArrayList<>();

        if (mypageType.equals("writeComment")) {
            // 로그인한 회원이 작성한 댓글 리스트
            commentList = commentMapper.getMypageComment(map);
        }else if (mypageType.equals("likeComment")) {
            // 로그인한 회원이 좋아요한 게시글 리스트
            map.put("userSeq", userVO.getUserSeq());
            List<LikeVO> likeInfo = likeMapper.getLikeInfo(map);
            map.put("likeInfo", likeInfo);
            commentList = commentMapper.getMypageComment(map);
        }

        return commentList;
    }

    // 마이페이지 댓글 미리보기 리스트
    @Override
    public List<CommentVO> getMypageList(HashMap<String, Object> map) throws Exception {
        List<CommentVO> commentList = new ArrayList<>();
        UserVO userVO = userService.userInfo();
        String mypageType = (String) map.get("mypageType");

        if (mypageType.equals("writeComment")) {
            // 로그인한 회원이 작성한 댓글 리스트
            commentList = commentMapper.getMypageComment(map);
        }else if (mypageType.equals("likeComment")) {
            // 로그인한 회원이 좋아요한 게시글 리스트
            map.put("userSeq", userVO.getUserSeq());
            List<LikeVO> likeInfo = likeMapper.getLikeInfo(map);
            map.put("likeInfo", likeInfo);
            commentList = commentMapper.getMypageComment(map);
        }

        return commentList;
    }

    // 마이페이지 댓글 count
    @Override
    public int commentCount(HashMap<String, Object> map) throws Exception {
        int count = 0;
        UserVO userVO = userService.userInfo();
        String mypageType = (String) map.get("mypageType");

        if (mypageType.equals("writeComment")) {
            // 로그인한 회원이 작성한 댓글 count
            count = commentMapper.commentCount(map);
        }else if (mypageType.equals("likeComment")) {
            // 로그인한 회원이 좋아요한 게시글 count
            map.put("userSeq", userVO.getUserSeq());
            List<LikeVO> likeInfo = likeMapper.getLikeInfo(map);
            map.put("likeInfo", likeInfo);
            log.info("like size : " + likeInfo.size());
            count = commentMapper.commentCount(map);
        }
        return count;
    }

    // 댓글 수정
    @Override
    public HashMap<String, String> commentModProc(CommentVO commentVO) throws Exception {
        HashMap<String, String> map;

        String userId = userService.userInfo().getUserId();

        if (userId != null) {
            commentVO.setCommentChgId(userId);
        } else {
            commentVO.setCommentChgId("익명");
        }

        int result = commentMapper.commentModProc(commentVO);

        if (result==1) {
            map = responseMapUtil.getResponseMap("comment.mod", "post.detail", commentVO.getPostSeq());
        } else {
            map = responseMapUtil.getResponseMap("comment.mod.error", "post.detail", commentVO.getPostSeq());
        }

        return map;
    }

    // 댓글 삭제
    @Override
    public HashMap<String, String> commentDelete(int commentSeq, int postSeq, String location) throws Exception {
        HashMap<String, String> map;

        int result = commentMapper.replyCountTotal(commentSeq);

        if (location == "postDetail"){
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
        }else {
            if (result > 0) {
                map = responseMapUtil.getResponseMap("comment.delete.reply", "user.mypage");
            } else {
                result = commentMapper.commentDelete(commentSeq);
                commentMapper.countDelete(commentSeq);
                if (result == 1) {
                    map = responseMapUtil.getResponseMap("comment.delete", "user.mypage");
                } else {
                    map = responseMapUtil.getResponseMap("comment.delete.error", "user.mypage");
                }
            }
        }

        return map;
    }

    // 댓글 좋아요 증가
    @Override
    public void commentLikeInc(int commentSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        commentMapper.likeInc(commentSeq);
        likeMapper.commentLikeInc(userSeq, commentSeq);
    }

    // 댓글 좋아요 감소
    @Override
    public void commentLikeDec(int commentSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        commentMapper.likeDec(commentSeq);
        likeMapper.commentLikeDec(userSeq, commentSeq);
    }

    // 답글인지 확인
    @Override
    public int replyConfirm(int commentSeq) throws Exception {
        return commentMapper.replyConfirm(commentSeq);
    }
}
