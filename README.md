## 1) 로그 추적기


### 요구사항

- 모든 public 메서드의 호출, 응답 정보를 로그로 출력
- 로그로 인하여 비즈니스 로직에 영향을 끼쳐서는 안 됨
- 메서드 호출에 걸린 시간
  - 호출 - 종료 시점을 통하여 시간 측정 > 어떤 부분에서 병목이 발생하는지 알기 위함
- 정상 흐름과 예외 흐름의 로그를 구분
  - 예외 발생 시 예외에 대한 정보가 필요
- 메서드 호출의 depth 기록
- HTTP 요청에 대한 구분 필요
  - 요청 단위로 특정한 ID값을 남김으로써 어떤 요청으로 인한 로그인지를 명시
  - 트랜잭션 ID 사용 권장