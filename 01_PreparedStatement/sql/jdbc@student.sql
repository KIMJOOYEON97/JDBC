--===============================
--관리자 계정
--===============================
--student계정 생성 및 권한부여
create user student
identified by student
default tablespace users;

--권한부여
grant connect,resource to student;

--===============================
--STUDENT 계정
--===============================
create table member(
    member_id varchar2(20),
    password varchar2(20) not null,
    member_name varchar2(100) not null,
    gender char(1),
    age number,
    email varchar2(200),
    phone char(11) not null, --하이픈 없이 숫자만
    address varchar2(1000),
    hobby varchar2(100), --농구,음악감상,영화  컴마를 구분으로 여러개를 하나의 문자열로 관리
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint pk_member_gender check(gender in('M','F'))
);

insert into member
values(
    'honggd','1234','홍길동','M',33,'honggd@naver.com','01012341234','서울 강남구 테헤란로','등산,그림,요리',default
);


insert into member
values(
    'sinsa','1234','신사임당','F',48,'sinsa@naver.com','01012344321','서울 강동구','음악감상',default
);


insert into member
values(
    'sejong','1234','세종','M',76,'sejong@naver.com','01099998888','서울 중구','육식',default
);


insert into member
values(
    'leess','1234','이순신','M',33,'leess@naver.com','01012121212',' 전남 여수','목공예',default
);


insert into member
values(
    'ygsgs','1234','유관순','F',null,null,'01031313131',null,null,default
);

select * from member;
commit;

desc member;


