### 설명
- Spring-cloud-gateway를 사용
- route class 기반으로 Service Domain 분리가 v1 에 비해 쉽다고 느낌

### 기타..
- v1과 동일하게 인증 등등은 따로 서버 분리가 필요
- gateway서버는 인증서버(apikey발급 서버)로부터 받은 access_token refresh_token등등에 대한 처리를 해주고
  해당 token을 기반으로 인증된 토큰일 경우 Service Domain 접근 허용
- JWT(auth0) token 기반으로 Sample Filter 적용함 (GET /domains url)
```diff
- - 해당 부분 filter 개발 필요
+ - JWT(auth0) token 기반으로 Sample Filter 적용함 (GET /domains url)
```
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
        
        


### 기타
Annotaion 기반이아닌 yml File 기반의 설정시 (actuator-refresh가 편한 장점, 모아보는 장점?)
```
server:
  port: 8080
---
spring:
  cloud:
    gateway:
      default-filters:
        - name: GlobalFilter
          args:
            baseMessage: Spring Cloud Gateway GlobalFilter
            preLogger: true
            postLogger: true
      routes:
        - id: user-svc
          uri: http://localhost:8081/
          predicates:
            - Path=/user/**
          filters:
            - name: UserFilter
              args:
                baseMessage: Spring Cloud Gateway UserFilter
                preLogger: true
                postLogger: true
        - id: cafe-svc
          uri: http://localhost:8082/
          predicates:
            - Path=/cafe/**
          filters:
            - name: CafeFilter
              args:
                baseMessage: Spring Cloud Gateway CafeFilter
                preLogger: true
                postLogger: true
```
Class config로 구현한 내용들이 대부분 있음....

- Route(경로) : 게이트웨이의 기본 골격이다. ID, 목적지 URI, 조건부(predicate) 집합, 필터(filter) 집합으로 구성된다.  조건부가 맞게 되면 해당하는 경로로 이동하게 된다. 
- Predicate(조건부) : Java8의 Function Predicate이다. Input Type은 Spring Framework ServerWebExchange이다. 조건부를 통해 Header 나 Parameter같은 HTTP 요청의 모든 항목을 비교할 수 있다.
- Filter(필터) : 특정 팩토리로 구성된 Spring Framework GatewayFilter 인스턴스다. Filter에서는 다운스트림 요청 전후에 요청/응답을 수정할 수 있다.
(https://twofootdog.tistory.com/64)
