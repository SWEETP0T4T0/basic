package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.*;
import com.beyond.basic.b2_board.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
//모든 메서드에 ResponseBody붙음
@RestController
@RequestMapping("/member/rest")
public class MemberRestController {

    private final MemberService memberService;
    public MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    //    회원목록조회
    @GetMapping("/list")
    public ResponseEntity<?> memberList() {
        List<MemberListRes> memberListResList = memberService.findAll();
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "memberlist is found", memberListResList), HttpStatus.OK);
    }

    //    회원상세조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id) {
        try{
            MemberDetailDto dto = memberService.findById(id);
            return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value()
                    ,"member is found"
                    , dto)
                    , HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value()
                    , e.getMessage())
                    , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> memberCreatePost(@RequestBody MemberCreateDto dto) {
        try{
            Member member = memberService.save2(dto);
            return new ResponseEntity<>(new CommonDto(HttpStatus.CREATED.value()
                    ,"member is created"
                    , member.getId()), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value()
                    ,e.getMessage())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    //    get:조회, post:등록, patch:부분수정, put:대체, delete:삭제
//    axios.patch
    @PatchMapping("/update/pw")
    public String updatePw(@RequestBody MemberUpdateDto dto){
        memberService.updatePw(dto);
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id){
        memberService.delete(id);
        return "ok";
    }
}