package com.beyond.basic.b2_board.controller;


import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.CommonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
    //    case 1. @ResponseStatus 어노테이션 사용
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1() {
        return "ok";
    }

    @PostMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public String annotation2() {
        return "ok";
    }

    //    case 2. 메서드 체이닝 방식 : ResponseEntity의 클래스 사용
    @GetMapping("chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("hong", "ho@naver.com", "1q2w3e4r");
//        header에 200 OK, body에 member형태의 json
//        return ResponseEntity.ok(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }


    //    case 3. ResponseEntity 객체를 직접 생성하여 커스텀하는 방식
    @GetMapping("/custom1")
//    Object 자리에 Member, ?도 가능
    public ResponseEntity<Object> custom1() {
        Member member = new Member("hong", "ho@naver.com", "1q2w3e4r");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/custom2")
    public ResponseEntity<?> custom2() {
        Member member = new Member("hong", "ho@naver.com", "1q2w3e4r");
//        header : 상태코드 + 상태메시지, body : 상태코드, 상태메시지, 객체
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "member is found", member), HttpStatus.OK);
    }
}
