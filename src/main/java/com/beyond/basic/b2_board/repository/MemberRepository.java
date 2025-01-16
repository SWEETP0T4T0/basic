package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//springDataJpa가 되기 위해서는 JpaRepository를 상속해야하고, 상속시에 Entity명과 entity의 pk타입을 명시
//JpaRepository를 상속함으로서  JpaRepository의 주요기능(각종crud기능이 있음) 상속
public interface MemberRepository extends JpaRepository<Member, Long> {
    //    save, findAll,findById는 사전에 구현, 그외 다른 컬럼을 조회할때는 findBy+컬럼명 형식으로 선언만하면 실행시점에 구현
//    무조건 낙타표기법으로 써야함
//    findByNameAndEmail() = select * from member where name = ? and email
//    findByNameOrEmail()
    Optional<Member> findByEmail(String email);
}