const { Given, Then, When } = require('@cucumber/cucumber');
const Navigator = require(process.cwd() + '/features/step_definitions/classes/navigator');
const jsonpath = require('jsonpath');
const { expect } = require('chai')
const fs = require('fs');
const resolve = require('path').resolve;

let navigator;
const dataSet = new Map();
const TYPE_DATASET = 'SET';
const TYPE_ENV = 'ENV';
const TYPE_VALUE = 'STR';

Given('아래 URL Endpoint를 {string} 방식으로 호출을 하려고 할 때', (method, dataTable, next) => {
    const urlNotDefinedCode = 'URL_NOT_DEFINED';

    if (dataTable.hashes().length > 0) {
        const urlStoreType = dataTable.hashes()[0]['Type'];
        const urlStoreValue = dataTable.hashes()[0]['Value'];
        let endpointUrl;

        if (urlStoreType === TYPE_VALUE) {
            endpointUrl = urlStoreValue;
        } else if (urlStoreType === TYPE_DATASET && dataSet.has(urlStoreValue))  {
            endpointUrl = dataSet.get(urlStoreValue);
        } else if (urlStoreType === TYPE_ENV && !!process.env[urlStoreValue]) {
            endpointUrl = process.env[urlStoreValue];
        }

        if (!!endpointUrl) {
            navigator = new Navigator();
            navigator.url = endpointUrl;
            navigator.method = method.toUpperCase();

            next();
        } else {
            next(urlNotDefinedCode);
        }
    } else {
        next(urlNotDefinedCode);
    }
});

Then('{string} Path를 URL에 추가한다', (urlPath, next) => {
    navigator.url = navigator.url + urlPath;

    next();
});

When('Header를 아래의 정보를 이용하여 설정한다', (dataTable, next) => {
    if (dataTable.hashes().length > 0) {
        const notMappedList = [];

        dataTable.hashes().forEach((headerInfo) => {
            const dataType = headerInfo['Type'];
            const headerName = headerInfo['Name'];
            let headerValue = headerInfo['Value']

            if (dataType === TYPE_VALUE) {
                navigator.setHeader(headerName, headerValue);
            } else if (dataType === TYPE_DATASET && dataSet.has(headerValue)) {
                navigator.setHeader(headerName, dataSet.get(headerValue));
            } else if (dataType === TYPE_ENV && !!process.env[headerValue]) {
                navigator.setHeader(headerName, process.env[headerValue]);
            } else {
                notMappedList.push([dataType, headerName]);
            }
        });

        if (notMappedList.length > 0) {
            next(notMappedList.map((values) => `NOT_FOUND: ${values[1]} [TYPE: ${values[0]}]`).join('\n'));
        } else {
            next();
        }
    }

    console.log(navigator._headers);

    next();
});

Then('Parameter를 아래의 정보를 이용하여 설정한', (dataTable, next) => {
    if (dataTable.hashes().length > 0) {
        dataTable.hashes().forEach((headerInfo) => {
            navigator.setParameter(headerInfo['Name'], headerInfo['Value']);
        })
    }

    next();
});

Then('Body를 아래 정보를 이용해서 JSON 형식으로 설정한다.', (dataTable, next) => {
    if (dataTable.hashes().length > 0) {
        const inputBody = {};
        dataTable.hashes().forEach((headerInfo) => {
            inputBody[headerInfo['Name']] = headerInfo['Value'];
        })
        navigator.setBody(JSON.stringify(inputBody));
    }

    next();
});

Then('{string}에 저장된 Token을 이용하여 인증을 처리한다', (envName, next) => {
    const envValue = process.env[envName];

    if (!!envValue) {
        navigator.setHeader('Authorization', `Bearer ${envValue}`);
        next();
    } else {
        next(`ENV_VALUE_NOT_FOUND: ${envName}`);
    }
});

When('Endpoint를 호출한다', async () => {
    console.log(`Endpoint: ${navigator.url}`)
    await navigator.navigate().toss();
});

Then('상태코드 {int}를 반환한다', (status_code, next) => {
    expect(navigator.getStatusCode()).to.be.eq(status_code);

    next();
});

Then('응답 데이터의 {string} 값을 {string}에 보관한다', (jsonPathStr, dataKey, next) => {
    const jsonValues = jsonpath.query(navigator.getResponse(), jsonPathStr);
    let is_inserted = false;

    if (jsonValues.length == 1) {
        dataSet.set(dataKey, jsonValues[0]);
        is_inserted = true;
    } else if (jsonValues.length > 1) {
        dataSet.set(dataKey, jsonValues);
        is_inserted = true;
    } else {
        is_inserted = false;
        next('JSONPATH_NOT_FOUND: ' + jsonPathStr);
    }

    if (is_inserted) {
        console.log(dataSet);
        next();
    }
})

Then('{int}번째 응답결과에는 아래의 JSON Key가 결과에 포함되어야 한다', (idx, dataTable, next) => {
    const jsonValues = navigator.getResponse();

    expect(jsonValues.length > 0).to.be.true;

    if (dataTable.hashes().length > 0) {
        dataTable.hashes().forEach((headerInfo) => {
            expect(headerInfo['Key'] in jsonValues[idx]).to.be.true;
        });
    }

    next();
});

Then('마지막 응답결과에는 아래의 JSON Data가 결과에 포함되어야 한다', (dataTable, next) => {
    const jsonValues = navigator.getResponse();

    expect(jsonValues.length > 0).to.be.true;

    const idx = jsonValues.length - 1;

    if (dataTable.hashes().length > 0) {
        dataTable.hashes().forEach((headerInfo) => {
            expect(jsonValues[idx][headerInfo['Key']]).to.be.eq(headerInfo['Value']);
        });
    }

    next();
});

Then('{string}파일에 있는 내용을 Body로 사용한다.', (fileName, next) => {
    let jsonStr = fs.readFileSync(resolve('./payloads/' + fileName));
    navigator.body = jsonStr;

    next();
});

Then('Header에 아래 내용이 있어야 한다.', (dataTable, next) => {
    expect(navigator.getResponseHeaders()['content-type']).to.be.eq(dataTable.hashes()[0]['Value']);

    next();
});

Then('응답 데이터 크기가 {}보다 커야 한다.', (size, next) => {
    expect(navigator.getResponseBody().length > 0).to.be.true;
    next();
});
