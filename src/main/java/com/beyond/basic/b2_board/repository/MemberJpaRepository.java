package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    //    EntityManager는 JPA의 핵심클래스(객체)
//    Entity의 생명주기를 관리, 데이터베이스와의 모든 인터페이싱을 책임, 엔티티를 대상으로 CRUD기능을 제공.
    private final EntityManager entityManager;
    public MemberJpaRepository (EntityManager entityManager){
        this.entityManager = entityManager;
    }
    //    순수JPA는 기본적인 CRUD만을 제공해주고,상당수의 쿼리는 직접 작성해야함.
    public List<Member> findAll(){
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return memberList;
    }
    public Optional<Member> findById(Long id){
//        find()는 기본키가 매개변수이다.
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Optional<Member> findByEmail(String email){
//        여기서 작성되는 쿼리는 문자열 형식의 쿼리가 아니라 jpql(객체지향쿼리문)
        Member member = null;
        try {
            member = entityManager.createQuery("select m from Member m where m.email= :email",Member.class).setParameter("email",email).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }

    public void save(Member member){
//        persist:전달된 엔티티가 EntitiyManager의 관리상태가 되도록 만들어주고, 트랜잭션이 커밋될때 db에 저장
        entityManager.persist(member);
    }
}