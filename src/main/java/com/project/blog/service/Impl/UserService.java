package com.project.blog.service.Impl;

import com.project.blog.config.jwt.CustomUserDetails;
import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.mapper.UserMapper;
import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    @Resource
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public HashMap<String, String> userRegProc(UserVO userVO) throws Exception {
        int result;
        String msg, url;
        HashMap<String, String> map = new HashMap<>();

        if (userMapper.idCheck(userVO.getUserId())) {
            msg = "중복된 아이디입니다.";
            url = "/user/regist";
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));

            result = userMapper.userRegProc(userVO);

            if (result==1) {
                msg = "회원가입 성공";
                url = "/user";
            } else {
                msg = "회원가입 실패";
                url = "/user";
            }
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public HashMap<String, String> login(UserVO userVO) throws Exception {
        String userId = userVO.getUserId();
        String password = userVO.getPassword();
        String msg, url;
        HashMap<String, String> map = new HashMap<>();

        if (userMapper.idCheck(userId)) {
            userVO = userMapper.loginInfo(userId);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(password, userVO.getPassword())) {
                String token = jwtTokenProvider.createToken(userVO);

                msg = "로그인 성공";
                url = "/index";

                map.put("token", token);
            } else {
                msg = "비밀번호가 잘못되었습니다.";
                url = "/user";
            }
        } else {
            msg = "존재하지 않는 계정입니다.";
            url = "/user";
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public UserVO userInfo() {
        CustomUserDetails customUserDetails;
        try {
            customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            customUserDetails.getUserVO().setReleased();
        } catch (Exception e) {
            return new UserVO();
        }

        return customUserDetails.getUserVO();
    }

    @Override
    public HashMap<String, String> userModProc(UserVO userVO) throws Exception {
        UserVO loginUser = userInfo();
        if (loginUser != null) {
                userVO.setChgId(loginUser.getUserId());
        } else {
            userVO.setChgId("익명");
        }

        int result = userMapper.userModProc(userVO);
        String msg, url;
        HashMap<String, String> map = new HashMap<>();

        if (result == 1) {
            msg = "회원정보 수정 성공";
            url = "/user/detail";
        }else {
            msg = "회원정보 수정 실패";
            url = "/user/detail";
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public HashMap<String, String> passwordModProc(String currentPassword, String newPassword) throws Exception {
        String msg, url;
        HashMap<String, String> map = new HashMap<>();
        UserVO userVO = userInfo();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(currentPassword, userVO.getPassword())) {
            if (!currentPassword.equals(newPassword)) {
                userVO.setPassword(passwordEncoder.encode(newPassword));

                int result = userMapper.passwordModProc(userVO);

                if (result == 1) {
                    msg = "패스워드 변경 성공";
                    url = "/user/modify";
                } else {
                    msg = "패스워드 변경 실패";
                    url = "/user/modify";
                }

            } else {
                msg = "입력한 새 비밀번호가 현재 비밀번호와 동일합니다.";
                url = "/user/modify";
            }
        } else {
            msg = "현재 비밀번호가 틀렸습니다.";
            url = "/user/modify";
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public HashMap<String, String> updatePassword(String userId) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String msg, url;

        if (userMapper.idCheck(userId)) {
            String password = getTemporaryPassword();

            msg = "임시 비밀번호 : " + password;
            url = "/user";

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);

            int result = userMapper.updatePassword(userId, password);

            if (result != 1) {
                msg = "실패";
                url = "/user";
            }
        } else {
            msg = "존재하지 않는 계정입니다.";
            url = "/user";
        }

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public String getTemporaryPassword() throws Exception {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
