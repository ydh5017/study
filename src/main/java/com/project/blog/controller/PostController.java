package com.project.blog.controller;

import com.project.blog.service.ICommentService;
import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.util.PagingUtil;
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
    public String getPostList(@RequestParam(defaultValue = "1") int pno,
                              @RequestParam(required = false) String cateCode,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) String keyword, Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        int page = pno;
        int listCnt = 0;

        if (type != null && keyword != null) {
            if (type == "") {
                listCnt = postService.postCount(cateCode);
            }else {
                listCnt = postService.searchCount(type, keyword, cateCode);
            }
        }else {
            listCnt = postService.postCount(cateCode);
        }

        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        HashMap<String, Object> map = new HashMap<>();
        int i = paging.getStartList();
        int j = paging.getListSize();
        map.put("startlist", i);
        map.put("listsize", j);
        map.put("cateCode", cateCode);

        List<PostVO> postList = new ArrayList<>();
        try {
            if (type != null && keyword != null) {
                HashMap<String, Object> sMap = new HashMap<>();
                sMap.put("startlist", i);
                sMap.put("listsize", j);
                sMap.put("cateCode", cateCode);
                sMap.put("type", type);
                sMap.put("keyword", keyword);

                if (type == "") {
                    postList = postService.getPostList(map);
                }else {
                    postList = postService.getSearchList(sMap);
                }
            }else {
                postList = postService.getPostList(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<PagingUtil> pageList = paging.list(paging.getPage(), paging.getRangeCnt(), paging.getStartPage());

        model.addAttribute("postList", postList);
        model.addAttribute("paging", paging);
        model.addAttribute("pageList", pageList);
        model.addAttribute("userVO", userVO);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("cateCode", cateCode);

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

        model.addAttribute("userVO", userVO);
        model.addAttribute("postVO", postVO);
        model.addAttribute("write", postVO.isWriter());
        model.addAttribute("likeUser", postVO.isLikeUser());
        model.addAttribute("commentList", commentList);

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
