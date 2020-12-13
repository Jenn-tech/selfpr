- [1. 테이블 생성](#1-테이블-생성)
  - [1.1. notice테이블 생성](#11-notice테이블-생성)
  - [1.2. comments 테이블 생성](#12-comments-테이블-생성)
  - [1.3. Role 테이블 생성](#13-role-테이블-생성)
  - [1.4. member_role 테이블 생성](#14-member_role-테이블-생성)
  - [1.5. member 테이블 생성](#15-member-테이블-생성)
- [2. 데이터 삽입](#2-데이터-삽입)
  - [2.1. 아이디를 시퀀스로](#21-아이디를-시퀀스로)
- [3. jdbc](#3-jdbc)
    - [3.1.1. select문으로 쿼리 불러오기](#311-select문으로-쿼리-불러오기)
    - [3.1.2. insert문으로 데이터 삽입하기](#312-insert문으로-데이터-삽입하기)
    - [3.1.3 update문으로 데이터 수정하기](#313-update문으로-데이터-수정하기)


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
- 이 때 **commit은** 필수
```sql
insert into notice values(1, 'jdbc란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
insert into notice values(2, 'jdbc2란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
insert into notice values(3, 'jdbc3란 무엇인가?','jenn', 'aaa', sysdate, 0, '');
commit;
```
  
  ## 2.1. 아이디를 시퀀스로 
  - notice테이블편집 > id열 > 열시퀀스 
    - 만약 그 전에 값이 있었을 경우 last number을 그 마지막 값으로 바꿔줌

# 3. jdbc
### 3.1.1. [select문으로 쿼리 불러오기](JDBCprj/src/ex1/Program.java)
- driver, statement, resultset객체 만들어주기
- select문을 쓸때는 **executeQuery를** 사용함
- 자원을 소모하므로 close해주기

```java
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "oracle");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);

while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			Date regDate = rs.getDate("REGDATE");
			String content = rs.getString("CONTENT");
			int hit = rs.getInt("hit");
			
			System.out.printf(
                "id : %d, title: %s, writerId:%s, regDate : %s, content:%s, hit : %d\n",
								id, title, writerId, regDate, content, hit);
		}
		rs.close();
		st.close();
		con.close();
``` 
  
### 3.1.2. [insert문으로 데이터 삽입하기](JDBCprj/src/ex1/Program2.java)
- 쿼리문에 ?을 넣으면서 값을 끼워넣을 수 있는 문장준비
- 따라서 createStatement가 아닌 **prepareStatement**를 쓴다
  - 이 때 prepareStatement안에는 sql이 들어있음
- 각각 ?에 맞는 인덱스와 넣을 값을 setString(int, double등 타입에 맞춰 사용)으로 넣어준다
  - 이 때 index값은 **1부터 시작**
- insert문 쓸 때는 **executeUpdate**를 사용
```java
        String title ="test2";
		String writerId = "jam";
		String content = "hahaha";
		String files = "";
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "INSERT INTO notice ("
				+ "    title,"
				+ "    writer_id,"
				+ "    content,"
				+ "    files"
				+ ") VALUES (?, ?, ?, ?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "oracle");
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title); //1부터 시작
		st.setString(2, writerId);
		st.setString(3, content);
		st.setString(4, files);
		
		int result = st.executeUpdate();
		
		System.out.println(result);
		
		st.close();
		con.close();
``` 

### 3.1.3 [update문으로 데이터 수정하기](JDBCprj/src/ex1/Program3.java)
- id가 17번째인 값을 수정하려고 한다.    
  update는 insert와 거의 흡사하다  
  만약 오류가 발생한다면 sql안의 쿼리문이 진짜 제대로된 쿼리문인지 확인
  (notice뒤에 띄어쓰기가 없어서 오류가 발생했었음)
```java
String title ="test3";
		String content = "hahaha3";
		String files = "";
		int id = 17;
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "update notice "
				+ "set "
				+ "    title =?,"
				+ "    content=?,"
				+ "    files=?"
				+ "where id =?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "oracle");
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title); //1부터 시작
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate();
		
		System.out.println(result);
		
		st.close();
		con.close();
```