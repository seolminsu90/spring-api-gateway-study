### 설명
- Spring-cloud-gateway를 사용
- route class 기반으로 Service Domain 분리가 v1 에 비해 쉽다고 느낌
- v1과 동일하게 인증 등등은 따로 서버 분리가 필요

### 기타..
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
