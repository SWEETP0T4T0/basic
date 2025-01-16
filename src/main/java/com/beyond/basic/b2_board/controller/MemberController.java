package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    //        DefendencyInjection = DI
//    의존성 주입(DI) 방법 1. Autowired 어노테이션 사용 : 필드 주입
//    @Autowired
//    private MemberService memberService;

//    의존성 주입(DI) 방법 2. 생성자주입방식(가장 많이 사용)
//    장점 1 : final을 통해 상수로써 사용 가능 -> 재할당 불가능하므로 안정성이 향상됨
//    장점 2: 다형성 구현 가능  ->  추후 다른 java데이터로 간단하게 대체 가능
//    장점 3 : 순환 창조 컴파일 타입에 check
//    private final MemberService memberService;
////    싱글톤 객체로 만들어지는 시점에 아래 생성자가 호출, 생성자가 하나밖에 없을때에는 Autowired 생략 가능
////    @Autowired
//    public MemberController(MemberService memberService){
//        this.memberService = memberService;
//    }

//    의존성 주입(DI) 방법 3. requiredArgs 어노테이션 사용방법
//    RequiredArhs : 반드시 초기화 되어야 하는 필드(final 키워드 등)를 대상으로 생성자를 자동으로 만들어주는 어노테이션.

    private final MemberService memberService;

    @GetMapping("")
    public String memberHome(){
        return "member/home";
    }

    //    회원목록조회
    @GetMapping("/list")
    public String memberList(Model model){
        List<MemberListRes> memberListResList= memberService.findAll();
        model.addAttribute("memberList", memberListResList);
        return "member/member-list";
    }

//    회원상세조회
    @GetMapping("/detail/{id}")
    public String memberDetail(@PathVariable Long id, Model model) throws Exception {
//        name, email, password
        try {
            MemberDetailDto dto =  memberService.findById(id);
            model.addAttribute("member", dto);
            return "member/member-detail";

        }catch (Exception error){
            model.addAttribute("errorMessage", error.getMessage());
            return "/member/member-error";
        }
    }

//    회원가입
    @GetMapping("/create")
    public String memberCreate(){
        return "member/member-create";
    }

    @PostMapping("/create")
    public String memberCreateDto(MemberCreateDto memberCreateDto, Model model) throws Exception {
        try {
            memberService.save(memberCreateDto);
            return "redirect:/member/list";
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
//        화면 리턴이 아닌 url 재 호출을 통해 redirect
    }
}