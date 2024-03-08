package com.project.blog.service.Impl;

import com.project.blog.mapper.CategoryMapper;
import com.project.blog.mapper.CommentMapper;
import com.project.blog.mapper.LikeMapper;
import com.project.blog.mapper.PostMapper;
import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.util.ResponseMapUtil;
import com.project.blog.vo.CategoryVO;
import com.project.blog.vo.LikeVO;
import com.project.blog.vo.PostVO;
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
public class PostService implements IPostService {

    @Resource
    private final PostMapper postMapper;
    private final IUserService userService;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final CategoryMapper categoryMapper;
    private final ResponseMapUtil responseMapUtil;

    // 게시글 리스트 get
    @Override
    public List<PostVO> getPostList(HashMap<String, Object> map) throws Exception {
        UserVO userVO = userService.userInfo();
        String type = (String) map.get("type");
        String keyword = (String) map.get("keyword");
        String postType = (String) map.get("postType");

        List<PostVO> postList = new ArrayList<>();

        try {
            if (type != null && keyword != null) {
                if (type == "") {
                    // 게시글 검색 타입 없을 시 전체글 리스트 get
                    postList = postMapper.getPostList(map);
                }else {
                    // 검색 타입/키워드에 따라 게시글 리스트 get
                    postList = postMapper.getSearchList(map);
                }
            }else {
                if (postType != null) {
                    if (postType.equals("week") || postType.equals("day")) {
                        // 인기글 리스트 get
                        postList = postMapper.getPopularPost(map);
                    }else {
                        if (postType.equals("likePost")) {
                            List<LikeVO> likeInfo = likeMapper.getLikeInfo(userVO.getUserSeq());
                            map.put("likeInfo", likeInfo);
                            map.put("mypageType", "like");
                        }else {
                            // 마이페이지 게시글 리스트 get
                            map.put("mypageType", "write");
                        }
                        postList = postMapper.getMypagePost(map);
                    }
                }else {
                    // 전체글 리스트 get
                    postList = postMapper.getPostList(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    // 메인페이지 인기글 미리보기 리스트 get
    @Override
    public List<PostVO> getPopularPost(String postType, String cateCode) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cateCode", cateCode);
        map.put("postType", postType);
        return postMapper.getPopularPost(map);
    }

    // 마이페이지 게시글 미리보기 리스트
    @Override
    public List<PostVO> getMypagePost(String mypageType) throws Exception {
        UserVO userVO = userService.userInfo();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId" , userVO.getUserId());
        map.put("cateCode", "100");
        map.put("mypageType", mypageType);

        List<PostVO> postList = new ArrayList<>();

        if (mypageType.equals("write")) {
            // 로그인한 회원이 작성한 게시글 리스트
            postList = postMapper.getMypagePost(map);
        }else if (mypageType.equals("like")) {
            // 로그인한 회원이 좋아요한 게시글 리스트
            List<LikeVO> likeInfo = likeMapper.getLikeInfo(userVO.getUserSeq());
            map.put("likeInfo", likeInfo);
            postList = postMapper.getMypagePost(map);
        }

        return postList;
    }

    // 게시글 개수 count
    @Override
    public int postCount(HashMap<String, Object> map) throws Exception {
        UserVO userVO = userService.userInfo();
        String type = (String) map.get("type");
        String keyword = (String) map.get("keyword");
        String postType = (String) map.get("postType");
        int count;

        if (type != null && keyword != null) {
            if (type == "") {
                // 게시글 검색 타입 없을 시 전체글 count
                count = postMapper.postCount(map);
            }else {
                // 검색 타입/키워드에 따라 게시글 count
                count = postMapper.searchCount(map);
            }
        }else {
            if (postType != null) {
                if (postType.equals("week") || postType.equals("day")) {
                    // 인기글 count
                    count = postMapper.popularCount(map);
                }else {
                    // 마이페이지 게시글 count
                    if (postType.equals("likePost")) {
                        List<LikeVO> likeInfo = likeMapper.getLikeInfo(userVO.getUserSeq());
                        map.put("likeInfo", likeInfo);
                    }
                    count = postMapper.mypageCount(map);
                    log.info("count : " + count);
                }
            } else {
                // 전체글 count
                count = postMapper.postCount(map);
            }
        }

        return count;
    }

    // 게시글 등록
    @Override
    public HashMap<String, String> postAddProc(PostVO postVO) throws Exception {
        HashMap<String, String> map;
        String userId = userService.userInfo().getUserId();

        if (userId != null) {
            postVO.setWriteId(userId);
        } else {
            postVO.setWriteId("익명");
        }

        if (postVO.getCateCode() == 0) {
            return map = responseMapUtil.getResponseMap("post.add.error1", "post.add");
        }

        int result = postMapper.postAddProc(postVO);

        if (result==1) {
            map = responseMapUtil.getResponseMap("post.add", "post.detail", postVO.getPostSeq());
        } else {
            map = responseMapUtil.getResponseMap("post.add.error", "post.add");
        }

        return map;
    }

    // 게시글 상세정보 get
    @Override
    public PostVO postDetail(int postSeq) throws Exception {
        PostVO postVO = postMapper.postDetail(postSeq);
        UserVO userVO = userService.userInfo();

        // 게시글 조회수 증가
        postMapper.viewIncrease(postSeq);

        if (userVO.getUserSeq() != null) {
            // 게시글 좋아요한 회원인지 확인
            postVO.setLikeUser(likeMapper.postLikeCheck(Integer.parseInt(userVO.getUserSeq()), postSeq));
        }

        if (postVO.getWriteId().equals(userVO.getUserId())) {
            // 로그인한 회원과 작성자가 같은지 확인
            postVO.setWriter();
        }

        return postVO;
    }

    // 게시글 삭제
    @Override
    public HashMap<String, String> postDelete(int postSeq) throws Exception {
        HashMap<String, String> map;

        HashMap<String, Object> pMap = new HashMap<>();
        pMap.put("postSeq", postSeq);
        postMapper.postDelete(pMap);
        // 삭제된 게시글의 댓글들 삭제
        commentMapper.commentDelete2(postSeq);

        if (pMap.get("cateCode") != null) {
            map = responseMapUtil.getResponseMap("post.delete", "post.list" , pMap.get("cateCode"));
        } else {
            map = responseMapUtil.getResponseMap("post.delete.error", "post.list");
        }

        return map;
    }

    // 게시글 수정
    @Override
    public HashMap<String, String> postModProc(PostVO postVO) throws Exception {
        HashMap<String, String> map;

        String userId = userService.userInfo().getUserId();
        if (userId != null) {
            postVO.setChgId(userId);
        } else {
            postVO.setChgId("익명");
        }

        int result = postMapper.postModProc(postVO);

        if (result==1) {
            map = responseMapUtil.getResponseMap("post.mod", "post.detail", postVO.getPostSeq());
        } else {
            map = responseMapUtil.getResponseMap("post.mod.error", "post.detail", postVO.getPostSeq());
        }

        return map;
    }

    // 게시글 좋아요 증가
    @Override
    public void postLikeInc(int postSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        postMapper.likeInc(postSeq);
        likeMapper.postLikeInc(userSeq, postSeq);
    }

    // 게시글 좋아요 감소
    @Override
    public void postLikeDec(int postSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        postMapper.likeDec(postSeq);
        likeMapper.postLikeDec(userSeq, postSeq);
    }

    // 게시판 하위 카테고리 리스트 get
    @Override
    public List<CategoryVO> getCateList(String cateCode) throws Exception {
        List<CategoryVO> cateList = categoryMapper.getChildCategory("100");
        for (int i = 0; i < cateList.size(); i++) {
            if (cateList.get(i).getCateCode().equals(cateCode)) {
                cateList.get(i).setCate(true);
            }
            if (cateList.get(i).getCateCode().equals("100")){
                cateList.get(i).setCateName("전체");
            }
        }
        return cateList;
    }
}
