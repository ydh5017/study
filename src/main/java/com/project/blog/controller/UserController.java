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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final IPostService postService;
    private final ICommentService commentService;

    // 회원가입 페이지
    @GetMapping("regist")
    public String userRegPage() {
        return "/user/userReg";
    }

    // 회원가입
    @PostMapping
    public String userRegProc(UserVO userVO, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.info("id : " + userVO.getUserId());
        }

        HashMap<String, String> map = userService.userRegProc(userVO);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));
        return "/redirect";
    }

    // 로그인 페이지
    @GetMapping
    public String  loginPage() {
        return "/user/login";
    }

    // 로그인 & 회원인증
    @PostMapping("/login")
    public String login(UserVO userVO, Model model, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = userService.login(userVO);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        Cookie cookie = new Cookie("token", map.get("token"));
        cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
        cookie.setPath("/");
//        cookie.setHttpOnly();
//        cookie.setSecure();
        // TODO : Redis, LocalStorage, Cookie 차이점, 용도
        response.addCookie(cookie);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Model model) {
        String msg, url;

        try {
            // 쿠키 파기
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            msg = "로그아웃";
            url = "/index";
        } catch (Exception e){
            msg = "로그아웃 실패";
            url = "/index";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return "/redirect";
    }

    // 회원정보 페이지
    @GetMapping("/detail")
    public String userDetail(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/user/userDetail";
    }

    // 회원정보 수정 페이지
    @GetMapping("/modify")
    public String userMod(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        model.addAttribute("userVO", userVO);

        return "/user/userMod";
    }

    // 회원정보 수정
    @PutMapping
    public String userModProc(UserVO userVO, Model model) throws Exception {
        HashMap<String, String> map = userService.userModProc(userVO);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String mypage(Model model) throws Exception {
        UserVO userVO = userService.userInfo();

        List<PostVO> writePostList = postService.getMypagePost("writePost");
        List<PostVO> likePostList = postService.getMypagePost("likePost");
        List<CommentVO> writeCommentList = commentService.getMypageComment("writeComment");
        List<CommentVO> likeCommentList = commentService.getMypageComment("likeComment");

        model.addAttribute("userVO", userVO);
        model.addAttribute("writePostList", writePostList);
        model.addAttribute("likePostList", likePostList);
        model.addAttribute("writeCommentList", writeCommentList);
        model.addAttribute("likeCommentList", likeCommentList);

        return "/user/mypage";
    }

    // 마이페이지 댓글 리스트 페이지
    @GetMapping("/comment")
    public String getCommentList(@RequestParam(defaultValue = "1") int pno, // 페이지 넘버
                              @RequestParam(required = false) String mypageType, // 게시판 타입(주간/일간)
                              Model model) throws Exception {
        // 회원정보
        UserVO userVO = userService.userInfo();

        HashMap<String, Object> map = new HashMap<>();
        map.put("mypageType", mypageType);
        map.put("userId", userVO.getUserId());

        // 댓글 수 count
        int page = pno;
        int listCnt = commentService.commentCount(map);

        // 페이징
        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        int i = paging.getStartList();
        int j = paging.getListSize();
        map.put("startlist", i);
        map.put("listsize", j);

        // 댓글 리스트
        List<CommentVO> commentList = commentService.getMypageList(map);
        // 페이지 리스트
        List<PagingUtil> pageList = paging.list(paging.getPage(), paging.getRangeCnt(), paging.getStartPage());

        model.addAttribute("userVO", userVO);
        model.addAttribute("commentList", commentList);
        model.addAttribute("paging", paging);
        model.addAttribute("pageList", pageList);
        model.addAttribute("mypageType", mypageType);

        return "/user/commentList";
    }

    // 패스워드 변경
    @PutMapping("/password")
    public String passwordModProc(String currentPassword, String newPassword, Model model) throws Exception {
        log.info("cp : " + currentPassword);
        log.info("np : " + newPassword);

        HashMap<String, String> map = userService.passwordModProc(currentPassword, newPassword);

        log.info("map : " + map);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }

    // 임시 비밀번호 발급 페이지
    @GetMapping("/findPw")
    public String findPw() throws Exception {
        return "/user/findPw";
    }

    // 임시 비밀번호 발급
    @PutMapping("/updatePassword")
    public String updatePassword(String userId, Model model) throws Exception {
        HashMap<String, String> map = userService.updatePassword(userId);

        model.addAttribute("msg", map.get("msg"));
        model.addAttribute("url", map.get("url"));

        return "/redirect";
    }
}
