# language: ko

@export_statements @smoke @complete
기능: 비용 처리 내역서(Statement)를 엑셀파일로 내보내기 한다.
  @smoke @complete
  시나리오: 조회된 Statements를 엑셀파일로 내보낸다.
  먼저 아래 URL Endpoint를 "POST" 방식으로 호출을 하려고 할 때
  | Type  | Value                  |
  | ENV   | STATEMENTS_EXPORT_URL  |
  그리고 Header를 아래의 정보를 이용하여 설정한다
  | Name            | Type  | Value                     |
  | Content-Type    | STR   | application/json          |
  그리고 Body를 아래 정보를 이용해서 JSON 형식으로 설정한다.
  | Name             | Type  | Value                     |
  | transaction_from | STR   | 20210630                  |
  | transaction_to   | STR   | 20210731                  |
  그리고 Endpoint를 호출한다
  그러면 상태코드 200를 반환한다
  그러면 Header에 아래 내용이 있어야 한다.
  | Key            | Value                                                              |
  | Content-Type   | application/vnd.openxmlformats-officedocument.spreadsheetml.sheet  |
  그러면 응답 데이터 크기가 0보다 커야 한다.
