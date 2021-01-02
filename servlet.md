# 목차

- [목차](#목차)
- [21.01.01](#210101)
	- [1. 서블릿](#1-서블릿)
		- [1.1. 서블릿 기초](#11-서블릿-기초)
		- [1.2. 언제 dopost, doget을 쓸까](#12-언제-dopost-doget을-쓸까)
		- [1.3. Hello Servlet 출력](#13-hello-servlet-출력)
		- [1.4. 라이프 사이클](#14-라이프-사이클)
		- [1.5. textbox에 입력된 값 얻어오기](#15-textbox에-입력된-값-얻어오기)
- [21.01.02](#210102)
	- [1. 파일업로드](#1-파일업로드)
		- [1.1. 파일업로드 위한 jsp 폼](#11-파일업로드-위한-jsp-폼)
		- [1.2. 파일 업로드 위한 서블릿](#12-파일-업로드-위한-서블릿)
	- [2. 상품등록](#2-상품등록)



# 21.01.01
## 1. 서블릿
### 1.1. 서블릿 기초
```html
<form action"CallServlet">
    <input type = "submit" value = "전송">
</form>
```
- 이때 action에 있는 CallServlet이 요청할 서블릿이다.  
input type 이 submit이기 때문에 클릭하면 서블릿이 요청된다.
  - 전송 버튼은 일반버튼이 아닌 input태그의 type 속성값은 submit으로 지정해야한다
- form 태그로 서버측에 존재하는 많은 서블릿 중 하나를 정해서 요청하고있음
- form태그가 서블릿을 요청할 때는 get과 post 두가지 전송 방식 중 한가지로 전송된다. 개발자는 원하는 전송방식을 선택 할 수 있는데 그러기위해서는 method 속성값을 form태그에 추가해야한다

<br>

- form태그를 이용한 get방식 요청의 예(아래)

```html
<form method="get" action"CallServlet">
    <input type = "submit" value = "전송">
</form>
```
- form태그를 이용한 post방식 요청의 예(아래)
```html
<form method="post" action"CallServlet">
    <input type = "submit" value = "전송">
</form>
```
- 결정하지 않았다면 기본값은 get이다. form태그외에도 a태그를 사용할 수 있다(아래)
```html
<a href="callServlet"> get 방식의 요청 </a>
```

### 1.2. 언제 dopost, doget을 쓸까
- doget()메소드는 IOException, Servlet Exception 예외를 외부에서 처리하도록 정의되어 있고 두개의 매개 변수를 갖는다. HttpServletRequest 형으로 선언된 첫번째 매개 변수는 클라이언트의 요청(request)을 처리하고 HttpServletResponse형으로 선언된 두번째 매개 변수는 요청 처리 결과를 클라이언트에게 되돌리기(response)를 위해 사용됨.
- 서버가 요청에 대한 처리를 마치고 클라이언트에게 결과를 되돌려주기 위해서 아래와 같이 HttpServletResponse로부터 PrintWriter형의 출력 스트림 객체를 얻어와야함
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
	out.println("<html><head><title>Addtion</title></head>");
    //PrintWriter 출력 스트림객체의 println()호출 -> 결과얻을 수 있음
	}
```
### 1.3. Hello Servlet 출력
```java
@WebServlet("/Hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트에게 응답할 페이지 정보를 셋팅한다.
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<html><body><h1>");
		out.println("Hello Servlet");
		out.println("</h1><body></html>");
		out.close();
	
	}
}
```
- 서블릿을 요청하기 위한 URL은 다음과 같다  
  
`http://localhost:9090/web-study-01/Hello`
- web-study-01 : context path
- hello : 서블릿 요청 url패턴


### 1.4. 라이프 사이클 
```java
public void init(ServletConfig config) throws ServletException {
		System.out.println("init 메소드는 첫 요청만 호출됨 : " + initCount++);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("destroy 메소드는 톰캣이 종료될 때만 호출됨 : " + destoyCount++);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doGet 메소드가 요청때마다 호출됨 :" + doGetCount++);
	}
```

### 1.5. textbox에 입력된 값 얻어오기
- 입력폼
```html
<form method = "get" action = "../ParamServlet">
	아이디 : <input type = "text" name = "id"><br>
	나이 : <input type = "text" name = "age"><br>
	<input type = "submit" value = "전송">
</form>
```
- 서블릿
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=EUC-KR");
		
		String id = request.getParameter("id");
		int age = Integer.parseInt(request.getParameter("age"));
		
		PrintWriter out = response.getWriter();
		out.print("<html><body>");
		out.println("당신이 입력한 정보입니다 <br>");
		out.println("아이디 :");
		out.println(id);
		out.println("<br>나이:");
		out.println(age);
		
		out.println("<br><a href='javascript:history.go(-1)'>다시</a>");
		out.print("</body></html>");
	}
```



# 21.01.02
## 1. 파일업로드
### 1.1. 파일업로드 위한 jsp 폼
```html
<form action = "../Upload.do" method = "post" enctype = "multipart/form-data">
글쓴이 : <input type = "text" name = "name"><br>
제목 : <input type = "text" name = "title"><br>
파일 지정하기: <input type = "file" name = "uploadFile" > <br>
<input type = "submit" value = "전송">
```

### 1.2. 파일 업로드 위한 서블릿
```java
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//다운받는 경로가 바뀜
		String savePath = "upload";
		int uploadFileSizeLimit = 5* 1024 * 1024;
		String encType = "UTF-8";
		
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		System.out.println("서버상의 실제 디렉토리 : ");
		System.out.println(uploadFilePath);
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, uploadFilePath, uploadFileSizeLimit, encType, new DefaultFileRenamePolicy());
			
			String fileName = multi.getFilesystemName("uploadFile");
			
			if (fileName == null) {
				System.out.println("파일 업로드 되지않았음");
			} else {
				out.println("<br>글쓴이 : " + multi.getParameter("name"));
				out.println("<br>제목 : " + multi.getParameter("title"));
				out.println("<br>파일명 : " + fileName);
			}
		} catch (Exception e) {
			System.out.println("예외 발생 : " + e);
		}
		
```

## 2. 상품등록
