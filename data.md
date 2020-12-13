- [1. 테이블 생성](#1-테이블-생성)
  - [1.1. notice테이블 생성](#11-notice테이블-생성)
  - [1.2. comments 테이블 생성](#12-comments-테이블-생성)
  - [1.3. Role 테이블 생성](#13-role-테이블-생성)
  - [1.4. member_role 테이블 생성](#14-member_role-테이블-생성)
  - [1.5. member 테이블 생성](#15-member-테이블-생성)
- [2. 데이터 삽입](#2-데이터-삽입)
- [3. jdbc](#3-jdbc)
  - [3.1.1. driver, statement, resultset객체 만들어주기](#311-driver-statement-resultset객체-만들어주기)
    - [3.1.2. select문으로 쿼리 불러오기](#312-select문으로-쿼리-불러오기)


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

# 2. 데이터 삽입
- 이 때 commit은 필수
```sql
insert into notice values(1, 'jdbc란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
insert into notice values(2, 'jdbc2란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
insert into notice values(3, 'jdbc3란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
commit;
```

# 3. jdbc
## 3.1.1. [driver, statement, resultset객체 만들어주기](JDBCprj/src/ex1/Program.java)
```java
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "oracle");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
```

### 3.1.2. [select문으로 쿼리 불러오기](JDBCprj/src/ex1/Program.java)

```java
while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			Date regDate = rs.getDate("REGDATE");
			String content = rs.getString("CONTENT");
			int hit = rs.getInt("hit");
			
			System.out.printf("id : %d, title: %s, writerId:%s, regDate : %s, content:%s, hit : %d\n",
								id, title, writerId, regDate, content, hit);
		}
		rs.close();
		st.close();
		con.close();
```