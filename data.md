- [1. 테이블 생성](#1-테이블-생성)
  - [1.1. notice테이블 생성](#11-notice테이블-생성)
  - [1.2. comments 테이블 생성](#12-comments-테이블-생성)
  - [1.3. Role 테이블 생성](#13-role-테이블-생성)
  - [1.4. member_role 테이블 생성](#14-member_role-테이블-생성)
  - [1.5. member 테이블 생성](#15-member-테이블-생성)


# 1. 테이블 생성

## 1.1. notice테이블 생성
```sql
CREATE TABLE NOTICE(
    id NUMBER,
    title Nvarchar2(100),
    writer_id nvarchar2(50),
    content clob,
    regdate timestamp,
    hit number,
    files nvarchar2(1000)
)
```

## 1.2. comments 테이블 생성
- 이때 create table comment를 하니까 invalid table name이라고 하여 생성되지 않았다. 아마 오라클 예약어를 써서 그런듯 싶어서 comment를 comments로 바꿔주니 올바르게 실행되었다
  
```sql
CREATE TABLE comments(
    id number,
    content NVARCHAR2(2000),
    regdate timestamp,
    writer_id NVARCHAR2(50),
    notice_id number
    );
```

## 1.3. Role 테이블 생성
```sql
create table role(
    id varchar2(50),
    discription nvarchar2(500)
    );
```

## 1.4. member_role 테이블 생성

```sql
create table member_role(
    member_id nvarchar2(50),
    role_id varchar2(50)
);
```

## 1.5. member 테이블 생성
```sql
create table member(
    id nvarchar2(50),
    pwd nvarchar2(50),
    name nvarchar2(50),
    gender nchar(2),
    birthday char(10),
    phone char(13),
    regdate date,
    email varchar2(200)
    );
```