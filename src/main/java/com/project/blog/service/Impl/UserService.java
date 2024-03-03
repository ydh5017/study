package com.project.blog.service.Impl;

import com.project.blog.config.jwt.CustomUserDetails;
import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.mapper.UserMapper;
import com.project.blog.service.IUserService;
import com.project.blog.util.ResponseMapUtil;
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
    private final ResponseMapUtil responseMapUtil;

    // 회원가입
    @Override
    public HashMap<String, String> userRegProc(UserVO userVO) throws Exception {
        int result;
        HashMap<String, String> map;

        if (userMapper.idCheck(userVO.getUserId())) {
            map = responseMapUtil.getResponseMap("user.exist", "user.reg");
        }else {
            // 패스워드 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));

            result = userMapper.userRegProc(userVO);

            if (result==1) {
                map = responseMapUtil.getResponseMap("user.reg", "user");
            } else {
                map = responseMapUtil.getResponseMap("user.reg.error", "user");
            }
        }

        return map;
    }

    // 로그인
    @Override
    public HashMap<String, String> login(UserVO userVO) throws Exception {
        String userId = userVO.getUserId();
        String password = userVO.getPassword();
        HashMap<String, String> map;

        if (userMapper.idCheck(userId)) {
            userVO = userMapper.loginInfo(userId);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // 패스워드 일치 확인
            if (passwordEncoder.matches(password, userVO.getPassword())) {
                // 로그인 정보 토큰 발급
                String token = jwtTokenProvider.createToken(userVO);

                map = responseMapUtil.getResponseMap("user.login", "main");
                map.put("token", token);
            } else {
                map = responseMapUtil.getResponseMap("user.password.error", "user");
            }
        } else {
            map = responseMapUtil.getResponseMap("user.exist.error", "user");
        }

        return map;
    }

    // 회원정보 get
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

    // 회원정보 수정
    @Override
    public HashMap<String, String> userModProc(UserVO userVO) throws Exception {
        UserVO loginUser = userInfo();
        if (loginUser != null) {
                userVO.setChgId(loginUser.getUserId());
        } else {
            userVO.setChgId("익명");
        }

        int result = userMapper.userModProc(userVO);
        HashMap<String, String> map;

        if (result == 1) {
            map = responseMapUtil.getResponseMap("user.mod", "user.detail");
        }else {
            map = responseMapUtil.getResponseMap("user.mod.error", "user.detail");
        }

        return map;
    }

    // 패스워드 변경
    @Override
    public HashMap<String, String> passwordModProc(String currentPassword, String newPassword) throws Exception {
        HashMap<String, String> map;
        UserVO userVO = userInfo();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 현재 비밀번호 일치 확인
        if (passwordEncoder.matches(currentPassword, userVO.getPassword())) {
            // 현재 비밀번호와 새 비밀번호 다른지 확인
            if (!currentPassword.equals(newPassword)) {
                userVO.setPassword(passwordEncoder.encode(newPassword));

                int result = userMapper.passwordModProc(userVO);

                if (result == 1) {
                    map = responseMapUtil.getResponseMap("user.password.mod", "user.modify");
                } else {
                    map = responseMapUtil.getResponseMap("user.password.mod.error1", "user.modify");
                }

            } else {
                map = responseMapUtil.getResponseMap("user.password.mod.error2", "user.modify");
            }
        } else {
            map = responseMapUtil.getResponseMap("user.password.mod.error3", "user.modify");
        }

        return map;
    }

    // 임시 비밀번호 발급
    @Override
    public HashMap<String, String> updatePassword(String userId) throws Exception {
        HashMap<String, String> map;

        if (userMapper.idCheck(userId)) {
            String password = getTemporaryPassword();

            map = responseMapUtil.getResponseMap("user.password.temporary", password,"user");

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);

            int result = userMapper.updatePassword(userId, password);

            if (result != 1) {
                map = responseMapUtil.getResponseMap("user.password.temporary.error", "user");
            }
        } else {
            map = responseMapUtil.getResponseMap("user.exist.error", "user");
        }

        return map;
    }

    // 임시 비밀번호 get
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
