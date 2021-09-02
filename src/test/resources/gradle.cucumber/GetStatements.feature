Feature: 비용 처리 내역서(Statement)를 조회한다.

  Scenario: 임직원으로서, 비용 정산이 정상적으로 진행되었는지 확인하기 위하여, 비용 처리 내역을 조회하고 싶다.
    Given 임직원이 아래의 기간으로 비용 처리 내역을 조회한다.
      | 조회 기간        | 일시     |
      | transaction_from | 20200101 |
      | transaction_to   | 20211231 |
    When 비용 처리 내역이 조회된다.
    Then 비용 처리 내역이 0l건 이상 조회된다.