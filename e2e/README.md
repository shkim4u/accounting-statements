### Install Yarn
`npm install --global yarn`

### Install dependencies
`yarn install`

### i18n reference
Setup a spoken language: https://cucumber.io/docs/gherkin/reference/#spoken-languages <br/>
Gherkin syntax: https://cucumber.io/docs/gherkin/languages/

### Cucumber Test 실행

프로젝트 Root directory에서,

Smoke test (Feature/bugfix branches): `yarn run smoke`

Complete test (Develop/master branches): `yarn run complete`

Sample: `yarn run sample`

### Feature 작성 요령
1. 1개 기능 내 여러 개의 시나리오를 작성할 수 있음.
2. Gherkin syntax keyword와 Step definition 사이에는 [Tab]을 이용하여 구분 <br/>
   예) 그러면[Tab]상태코드 200를 반환한다
3. Step definition 내 변수 값 입력 시에 Integer는 Double quote가 없는 상태에서 입력하고, String의 경우에는 Double quote를 사용하여 입력한다.<br/>
   예) 먼저[Tab]`"ap-northeast-2"` Region에서 `"id_sample"` `"Prod"` 환경의 OpenAPI `3`.0 정의를 불러온다
4. 새로운 Step definition 작성 시에는 각 Step definition의 성격에 따라 기존의 JavaScript File에 추가하거나 새로운 File을 만들어서 정의한다.<br/>
   이 때, 각 File은 각각의 Variable scope을 가지게 되며, 각 File 간 Variable을 공유하는 기능은 현재 없다.
   
### 미리 설정하여야 하는 Environment Variables
STATEMENTS_EXPORT_URL: Presets micrservice의 URL
   예) Local의 경우에는 `export STATEMENTS_EXPORT_URL=http://localhost:9080/statement/export`, Docker의 경우에는 `export STATEMENTS_EXPORT_URL=http://localhost:63061/export`
FEATURE_TOGGLE_URL: Feature Toggle Service의 URL
   예) Local과 Docker의 경우에는 `export FEATURE_TOGGLE_URL=http://localhost:63011`
