### 설명
- Spring-cloud-gateway를 사용
- route class 기반으로 Service Domain 분리가 v1 에 비해 쉽다고 느낌

### 기타..
- v1과 동일하게 인증 등등은 따로 서버 분리가 필요
- gateway서버는 인증서버(apikey발급 서버)로부터 받은 access_token refresh_token등등에 대한 처리를 해주고
  해당 token을 기반으로 인증된 토큰일 경우 Service Domain 접근 허용
```diff
- - 해당 부분 filter 개발 필요
+ - JWT(auth0) token 기반으로 Sample Filter 적용함 (GET /domains url)
```
- 이런 식으로 나누어서 개발할 만큼 큰 서비스를 할 날이 올까
- 튜토리얼 사이트 : https://spring.io/guides/gs/gateway/

#### 구조는 대충 이런식으로..
    
    Client 

    └── Gateway Server

        └── Authentication Server
    
        └── Logging Server
    
        └── Api Domain 1 Server set
    
        └── Api Domain 2 Server set
    
        ...
        ...
