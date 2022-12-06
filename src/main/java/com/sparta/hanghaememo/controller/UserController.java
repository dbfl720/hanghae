package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller  //33번 라인, 15라인 지우고 @RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;   //의존성 주입

//
//    @GetMapping("/getsign")
//    public ModelAndView signupPage() {
//        return new ModelAndView("sign");
//    }
//
//    @GetMapping("/getlogin")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
//    }

    @ResponseBody
    @PostMapping("/signup")  //회원가입 구현
    public ResponseMsgDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ResponseMsgDto("회원가입 성공!", HttpStatus.OK.value()); //요청 성공표시.
    }


//    @PostMapping("/login")   //로그인 구현
//    public String login(LoginRequestDto loginRequestDto) {
//        userService.login(loginRequestDto);
//        return "redirect:/api/memos";
//    }

    @ResponseBody   //로그인 구현 , ajax에서 body쪽에 값이 넘어가기 때문에, 아래 requestbody를 써줘야 된다.
    @PostMapping("/login")
    public ResponseMsgDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);   //HttpRequest에서 header가 넘어와서 받아오는 것처럼, Client쪽으로 반환할 때는 response객체를 반환하다.반환 할 response Header에 우리가 만들어준 토큰을 넣어주기 위해서 받아 오고 있다.
        return new ResponseMsgDto("로그인 성공!", HttpStatus.OK.value());
    }



    @GetMapping("user-usernames/{username}exists") //중복된 회원가입 확인
    public ResponseEntity<Boolean> checkUsernameDuplicate(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUsernameDuplicate(username));
    }




}