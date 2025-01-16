package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MemberMemoryRepository {
    public static Long id = 1L;

    private static List<Member> memberList = new ArrayList<>();

    public List<Member> findAll(){
        return this.memberList;
    }

    public String save(Member member){
        this.memberList.add(member);
        id++;
        return "member/member-complite";
    }

    public static Optional<Member> findByEmail(String email){
        Member member = null;
        for (Member m : memberList) {
            if (Objects.equals(email, m.getEmail())) {
                member = m;
            }
        }
        return Optional.ofNullable(member);
    }

    public static Optional<Member> findById(Long id){
        Member member = null;
        for (Member m : memberList) {
            if (Objects.equals(id, m.getId())) {
                member = m;
            }
        }
        return Optional.ofNullable(member);
    }


}
