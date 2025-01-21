//package com.beyond.basic.b2_board.domain;
//
//
//import com.beyond.basic.b2_board.dtos.MemberDetailDto;
//import com.beyond.basic.b2_board.dtos.MemberListRes;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//
//@NoArgsConstructor
//@Getter
//@ToString
//// JPA의 엔티티매니저에게 객체를 위임하려면 @Entity 어노테이션 필요
//@Entity
//public class Member {
//    @Id //pk설정
////    identity : auto_increment 설정(AUTO 설정은 jpa에게 적절한 전략을 위임하는 것)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
////    String은 별다른 설정 없을경우 varchar(255)로 DB컬럼으로 설정. 변수명 == 컬럼명 으로 변환.
//    private String name;
//    @Column(length = 50, unique = true, nullable = false)   // 컬럼의 성질 세팅
//    private String email;
////    @Column(name = "pw")  할수는 있으나 되도록이면 컬럼명과 변수명을 일치시키는 것이 개발의 혼선을 줄일 수 있음.
//    private String password;
////    자바에서 카멜케이스 사용시 db에는 created_time으로 들어간다
//    @CreationTimestamp
//    private LocalDateTime createdTime;
//    @UpdateTimestamp
//    private LocalDateTime updateTime;
//
//    public Member(String name, String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//
//    public MemberListRes listFromEntity(){
//        return new MemberListRes(this.id, this.name, this.email);
//
//    }
//
//    public MemberDetailDto detailFromEntity(){
//        return new MemberDetailDto(this.name, this.email, this.password);
//    }
//}

package com.beyond.basic.b2_board.domain;

import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
//jpa의 엔티티매니저에게 객체를 위임하려면 @Entity어노테이션 필요
@Entity
public class Member extends BaseTimeEntity {
    @Id //pk설정 어노테이션
//    IDENTITY : auto_increment설정(AUTO설정은 JPA에게 적절한 전략을 위임하는 것)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    String은 별다른 설정없을 경우 varchar(255)로 DB컬럼으로 설정. 변수명 == 컬럼명으로 변환.
    private String name;
    @Column(length = 50, unique = true,nullable = false)
    private String email;
    //    @Column(name = "pw") 이렇게 할 수 있으나, 되도록이면 컬럼명과 변수명을 일치시키는 것이 개발의 혼선을 줄일 수 있음.
    private String password;

    public Member(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }



    public MemberListRes listFromEntity(){
        return new MemberListRes(this.id,this.name,this.email);
    }
    public MemberDetailDto detailFromEntity(){
        return new MemberDetailDto(this.name,this.email,this.password);
    }
    public void updatePw(String newPassword){
        this.password = newPassword;
    }
}
