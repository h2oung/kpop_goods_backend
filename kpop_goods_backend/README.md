## 로컬 환경 설정

민감정보(DB 계정, API Key, JWT Secret 등)는 **`application-local.properties`** 파일에 작성하며, 이 파일은 깃에 업로드되지 않습니다.


###  1. DB 생성
1. MySQL 실행
2. 아래 명령으로 DB 생성:
CREATE DATABASE kpop_goods CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; 

### 2. 로컬 설정 파일 생성
`src/main/resources` 경로에 `application-local.properties` 파일을 생성합니다.  
아래 예시 파일(`application-local-example.properties`)을 참고해 실제 값을 채워 넣으세요.

```properties
# application-local.properties (예시)
spring.datasource.url=jdbc:mysql://localhost:3306/kpop_goods?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=myuser
spring.datasource.password=mypassword

kakao.client-id=my_kakao_client_id
kakao.redirect-uri=http://localhost:3000/login-success

jwt.secret=my_jwt_secret
jwt.expiration=360000
```

#### 3. KpopGoodsBackendApplication에서 실행 버튼(▶)을 눌러 애플리케이션을 시작합니다.