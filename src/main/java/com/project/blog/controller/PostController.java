package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.util.PagingUtil;
import com.project.blog.vo.CategoryVO;
import com.project.blog.vo.CommentVO;
import com.project.blog.vo.PostVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final IPostService postService;
    private final IUserService userService;
    private final ICommentService commentService;

    // 게시글 리스트 페이지
    @GetMapping
    public String getPostList(@RequestParam(defaultValue = "1") int pno, // 페이지 넘버
                              @RequestParam(required = false) String cateCode, // 카테고리 코드
                              @RequestParam(required = false) String postType, // 게시판 타입(주간/일간)
                              @RequestParam(required = false) String type, // 검색 유형
                              @RequestParam(required = false) String keyword, // 검색 키워드
                              Model model) throws Exception {
        // 회원정보
        UserVO userVO = userService.userInfo();

        HashMap<String, Object> map = new HashMap<>();
        map.put("cateCode", cateCode);
        map.put("postType", postType);
        map.put("type", type);
        map.put("keyword", keyword);

        // 게시글 수 count
        int page = pno;
        int listCnt = postService.postCount(map);

        // 페이징
        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        int i = paging.getStartList();
        int j = paging.getListSize();
        map.put("startlist", i);
        map.put("listsize", j);

        // 게시글 리스트
        List<PostVO> postList = postService.getPostList(map);
        // 하위 카테고리 리스트
        List<CategoryVO> cateList = postService.getCateList(cateCode);
        // 페이지 리스트
        List<PagingUtil> pageList = paging.list(paging.getPage(), paging.getRangeCnt(), paging.getStartPage());

        model.addAttribute("postList", postList);
        model.addAttribute("cateList", cateList);
        model.addAttribute("paging", paging);
        model.addAttribute("pageList", pageList);
        model.addAttribute("userVO", userVO);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("cateCode", cateCode);
        model.addAttribute("postType", postType);

        return "/post/list";
    }

    // 게시글 작성 페이지
    @GetMapping(value = "add")
    public String postAdd(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/post/postAdd";
    }

    // 게시글 작성
    @PostMapping
    public String postAddProc(PostVO postVO, Model model) throws Exception {
        HashMap<String, String> Map = postService.postAddProc(postVO);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }

    // 게시글 상세보기 페이지
    @GetMapping(value = "detail")
    public String postDetail(@RequestParam("no") int postSeq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(postSeq);
        UserVO userVO = userService.userInfo();
        String userId = userVO.getUserId();

        List<CommentVO> commentList = commentService.getCommentList(postSeq);

        if (userId != null) {
            for (int i = 0; i < commentList.size(); i++) {
                commentList.get(i).setWriter(userId.equals(commentList.get(i).getCommentWriteId()));
            }
        }

        boolean deleted = false;
        if (postVO.getIsDeleted().equals("1")){
            deleted = true;
        }

        model.addAttribute("userVO", userVO);
        model.addAttribute("postVO", postVO);
        model.addAttribute("write", postVO.isWriter());
        model.addAttribute("likeUser", postVO.isLikeUser());
        model.addAttribute("commentList", commentList);
        model.addAttribute("deleted", deleted);

        return "/post/postDetail";
    }

    // 게시글 삭제
    @PatchMapping
    public String postDelete(@RequestParam("no") int postSeq, Model model) throws Exception {
        //TODO : RequestParam, PathVariable
        HashMap<String, String> Map = postService.postDelete(postSeq);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }

    // 게시글 수정 페이지
    @GetMapping(value = "modify")
    public String postMod(@RequestParam("no") int postSeq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(postSeq);
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);
        model.addAttribute("postVO",postVO);

        return "/post/postModify";
    }

    // 게시글 수정
    @PutMapping
    public String postModProc(PostVO postVO, Model model) throws Exception {
        HashMap<String, String> Map = postService.postModProc(postVO);

        model.addAttribute("msg", Map.get("msg"));
        model.addAttribute("url", Map.get("url"));

        return "/redirect";
    }
}
