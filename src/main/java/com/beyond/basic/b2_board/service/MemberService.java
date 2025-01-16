//package com.beyond.basic.b2_board.service;
//
//import com.beyond.basic.b2_board.domain.Member;
//import com.beyond.basic.b2_board.dtos.MemberCreateDto;
//import com.beyond.basic.b2_board.dtos.MemberDetailDto;
//import com.beyond.basic.b2_board.dtos.MemberListRes;
//import com.beyond.basic.b2_board.repository.MemberJpaRepository;
//import com.beyond.basic.b2_board.repository.MemberMemoryRepository;
//import com.beyond.basic.b2_board.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Service
//// 아래 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고, 만약 예외(unchecked)가 발생 시 롤백처리가 자동으로 된다.
//@Transactional
//public class MemberService {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    public List<MemberListRes> findAll(){
//        List<Member> members = memberRepository.findAll();
//        List<MemberListRes> memberListRes = new ArrayList<>();
//        for (Member m : members) {
//            memberListRes.add(m.listFromEntity());
//        }
//        return memberListRes;
////        return memberMemoryRepository.findAll().stream().map(m->m.listFromEntity()).collect(Collectors.toList());
//    }
//
//    public void save(MemberCreateDto memberCreateDto) throws IllegalArgumentException, RuntimeException {
//        if (!memberRepository.findByEmail(memberCreateDto.getEmail()).equals("")){
//            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//        }
//        if (memberCreateDto.getPassword().length()<8){
//            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
//        }else {
//            Member member = new Member(memberCreateDto.getName(), memberCreateDto.getEmail(), memberCreateDto.getPassword());
//            memberRepository.save(memberCreateDto.toEntity());
//        }
//    }
//
//    public MemberDetailDto findById(Long id) throws Exception {
////        Optional<Member> optionalMember = memberMemoryRepository.findById(id);
////        Member member = optionalMember.orElseThrow(() -> new NoSuchElementException("없는 ID입니다."));
////        MemberDetailDto memberDetailDto = member.detailFromEntity();
////        return memberDetailDto;
////
//        return memberRepository.findById(id).orElseThrow(()-> new NoSuchElementException("없는 ID입니다.")).detailFromEntity();
//
////        Member member = MemberMemoryRepository.findById(id);
////        if (member==null){
////            throw new Exception("일치하는 회원이 없습니다.");
////        }else {
////            MemberDetailDto memberDetailDto = new MemberDetailDto(member.getName(), member.getEmail(), member.getPassword());
////            return memberDetailDto;
////        }
//    }
//
//    public void delite(Long id) {
//        Member member = memberRepository.findById(id).orElseThrow(()->new RuntimeException());
//    }
//
////    public MemberDetailDto findByID(Long id) throws NoSuchElementException{
////        Optional<Member> optionalMember = memberMemoryRepository.findById(id);
////        Member member = optionalMember.orElseThrow(() -> new NoSuchElementException("없는 ID입니다."));
////        MemberDetailDto memberDetailDto = new MemberDetailDto(member.getName(), member.getEmail(), member.getPassword());
////        return memberDetailDto;
////    }
//}
package com.beyond.basic.b2_board.service;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.dtos.MemberUpdateDto;
import com.beyond.basic.b2_board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
//아래 어노테이션을 통해, 모든 메서드에 트랜잭션을 적용하고 ,만약 예외(unchecked)가 발생시 롤백처리 자동화
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    public List<MemberListRes> findAll(){
//        List<Member> members =  memberMemoryRepository.findAll();
//        List<MemberListRes>memberListRes = new ArrayList<>();
//        for (Member m : members){
//            memberListRes.add(m.listFromEntity());
//        }
//        return memberListRes;
        return memberRepository.findAll().stream().map(m -> m.listFromEntity()).collect(Collectors.toList());
    }
    public void save(MemberCreateDto dto) throws IllegalArgumentException{
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (dto.getPassword().length()<8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        memberRepository.save(dto.toEntity());
    }

    public Member save2(MemberCreateDto dto) throws IllegalArgumentException{
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (dto.getPassword().length()<8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = memberRepository.save(dto.toEntity());
        return member;
    }

    public MemberDetailDto findById(Long id) throws NoSuchElementException, RuntimeException{
//        Optional<Member> member = memberMemoryRepository.findById(id);
//        Member member1 = member.orElseThrow(()-> new NoSuchElementException("없는 id입니다."));
//        MemberDetailDto dto = member1.detailFromEntity();
//        return dto;
        return memberRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("없는 id입니다."))
                .detailFromEntity();
    }

    public void updatePw(MemberUpdateDto dto){
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()){
            Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("없는 사용자입니다."));
            member.updatePw(dto.getNewPassword());
//            기존객체를 조회후에 다시 save할 경우에는 insert가 아니라 update쿼리 실행
            memberRepository.save(member);
        }
    }
    public void delete(Long id){
//        memberRepository.deleteById(id); 객체지향에 맞지 않고 예외처리가 안됨
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없다"));
        memberRepository.delete(member);
    }
}
