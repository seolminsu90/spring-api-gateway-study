# spring-api-gateway-study
api gateway(1) : api server(N) 

### 설명
- 제일 선행 단에 API gateway 역할을 하는 서버를 두고 Part 별로 API server를 여러개 두면
  마이크로 서비스와 같이 비슷한 구조를 갖지 않을까 라는 생각을 하며 샘플로 만들어 봄
- Gateway 역할을 하는 서버는 단순히 Request / Response만 해오는 역할만 하면 되서 DB를 조회할 필요가 없기 때문에
- Spring Start Webflux를 사용한 Netty서버 환경으로 구상

### 기타..
- Netty 서버는 처음 써봐서 한번 스터디를..
- spring-cloud-starter-zuul 로 구현한 Gateway 도 언젠간..
