package com.project.blog.controller;

import com.project.blog.service.IPostService;
import com.project.blog.util.PagingUtil;
import com.project.blog.vo.PostVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final IPostService postService;

    @RequestMapping(value = "list")
    public String getPostList(HttpServletRequest request, Model model) throws Exception {
        int page = Integer.parseInt("1");
        int listCnt = postService.postCount();
        log.info("pno : " + page);
        log.info("listCnt : " + listCnt);

        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        HashMap<String, Integer> hMap = new HashMap<>();
        int i = paging.getStartPage();
        int j = paging.getListSize();
        hMap.put("startlist", i-1);
        hMap.put("listsize", j);
        log.info("hMap : " + hMap);

        List<PostVO> pList = new ArrayList<>();
        try {
            pList = postService.getPostList(hMap);
            log.info("pList : " + pList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("pList", pList);
        model.addAttribute("paging", paging);

        return "/post/List";
    }

    @RequestMapping(value = "postAdd")
    public String postAdd() {
        return "/post/postAdd";
    }
    @RequestMapping(value = "postAddProc")
    public String postAddProc(HttpServletRequest request, Model model) throws Exception {
        log.info("request : " + request);

        PostVO postVO = new PostVO();
        postVO.setTitle(request.getParameter("title"));
        postVO.setContent(request.getParameter("content"));
        log.info(postVO.getTitle());
        log.info(postVO.getContent());

        HashMap<String, String> hMap = postService.postAddProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    @RequestMapping(value = "postDetail")
    public String postDetail(HttpServletRequest request, Model model) throws Exception {
        int post_seq = Integer.parseInt(request.getParameter("no"));

        PostVO postVO = postService.postDetail(post_seq);
        model.addAttribute("postVO",postVO);

        return "/post/postDetail";
    }

    @RequestMapping(value = "postDelete")
    public String postDelete(HttpServletRequest request, Model model) throws Exception {
        log.info("request : " + request);


        int post_seq = Integer.parseInt(request.getParameter("post_seq"));
        log.info(String.valueOf(post_seq));

        HashMap<String, String> hMap = postService.postDelete(post_seq);

        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    @RequestMapping(value = "postMod")
    public String postMod(HttpServletRequest request, Model model) {
        int post_seq = Integer.parseInt(request.getParameter("post_seq"));

        return "/post/postMod";
    }
    @RequestMapping(value = "postModProc")
    public String postModProc(HttpServletRequest request, Model model) throws Exception {
        log.info("request : " + request);

        PostVO postVO = new PostVO();
        postVO.setTitle(request.getParameter("title"));
        postVO.setContent(request.getParameter("content"));
        log.info(postVO.getTitle());
        log.info(postVO.getContent());

        HashMap<String, String> hMap = postService.postAddProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }
}
