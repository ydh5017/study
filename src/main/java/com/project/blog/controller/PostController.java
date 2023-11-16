package com.project.blog.controller;

import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.util.PagingUtil;
import com.project.blog.vo.PostVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 게시글 리스트 페이지
    @GetMapping
    public String getPostList(@RequestParam int pno, Model model) throws Exception {
        UserVO userVO = userService.userInfo();
        int page = pno;
        int listCnt = postService.postCount();

        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        HashMap<String, Integer> Map = new HashMap<>();
        int i = paging.getStartList();
        int j = paging.getListSize();
        Map.put("startlist", i);
        Map.put("listsize", j);

        List<PostVO> postList = new ArrayList<>();
        try {
            postList = postService.getPostList(Map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<PagingUtil> pageList = paging.list(paging.getPage(), paging.getRangeCnt(), paging.getStartPage());

        model.addAttribute("postList", postList);
        model.addAttribute("paging", paging);
        model.addAttribute("pageList", pageList);
        model.addAttribute("user", userVO.isReleased());

        return "/post/list";
    }

    // 게시글 작성 페이지
    @GetMapping(value = "add")
    public String postAdd() {
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

        model.addAttribute("postVO", postVO);
        model.addAttribute("write", postVO.isWriter());

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
