# Accounting Statements

## 진행 사항
* Statement 데이터는 Ledger 원장 데이터의 <u>**조회 모델**</u>로서 Data Decomposition을 위해서는 테이블 구조 설계와 Ledger 서비스와의 이벤트 연계가 필요함
  * 현재는 Overkill로 보이므로, 독립적인 데이터를 가지기보다는 ACL 연계를 통해 Ledger 서비스, 혹은 Ledger 서비스 구현 전이라면 Legacy Monolith 혹은 Mountebank Mock을 통해 조회 모델을 호출한다.
* MSA 구현을 위해 DDD 사상을 적용하여 패키지를 구조화
  * Layered Architecture
    * Controller (Inbound)
    * Application
    * Infrastructure (Outbound)
      * ACL Invocation to External Bounded Context
      * Repository
      * Event Broker
    * Domain Model
  * Domain Model
* Feature Toggle with Flipt
  * http://localhost:63011
  * Flag: statements-legacy-acl
* [Mountebank Imposter](./Resources/Mountebank/Configure_Imposter_For_Ledger_Monolith.txt) 
* Test Automation & * Code Coverage Test (with JaCoCo)
  * [JaCoCo Coverage Report](./build/reports/jacoco/jacoco.html/index.html)
* E2E Test

## GitHub 진행 사항
* 리포지터리 생성 시 build_env/buildspec*.yaml 파일, CloudFormation Stack(CodePipeline) 생성
* 리포지터리 삭제 시 main, master, develop 브랜치 명이 아닐 경우 CloudFormation Stack 삭제
* Push 이벤트 시에 CodePipeline 시작
* GitHub WebHook API Gateway:  https://dvtbbdj2j6.execute-api.ap-northeast-2.amazonaws.com/prod
  * Content Type
    * application/json
    * application/x-www-form-urlencoded
  * GitHub WebHook Event for Push
    * Header: X-GitHub-Event
    * TODO1
    * TODO2
    * 