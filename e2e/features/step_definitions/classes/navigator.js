const pactum = require('pactum');

class Navigator {
    _url;
    _pathParameters = new Map();
    _parameters = new Map();
    _headers = new Map();
    _method = 'GET';
    _availableMethods = ['GET', 'POST', 'PUT', 'DELETE']
    _body;
    _isCalled = false;
    _callApiSpec;

    constructor() {
    }

    get url() {
        return !!this._url ? this._url : '';
    }

    set url(inputUrl) {
        if (!!inputUrl && (inputUrl.includes('http://') || inputUrl.includes('https://'))) {
            this._url = inputUrl;
        } else {
            throw 'INVALID_URL';
        }
    }

    get body() {
        return !!this._body ? this._body : '';
    }

    set body(inputBody) {
        this._body = inputBody;
    }

    setBody(inputBody) {
        this._body = inputBody;
    }

    get pathParameters() {
        return this._pathParameters;
    }

    setPathParameter(paramName, paramValue) {
        this._pathParameters.set(paramName, paramValue);
    }

    getPathParameter(paramName) {
        if (this._pathParameters.has(paramName)) {
            return this._pathParameters.get(paramName);
        } else {
            return ;
        }
    }

    get headers() {
        return this._headers;
    }

    setHeader(headerName, headerValue) {
        this._headers.set(headerName, headerValue);
    }

    getHeader(headerName) {
        if (this._headers.has(headerName)) {
            return this._headers.get(headerName);
        } else {
            return ;
        }
    }

    get parameters() {
        return this._parameters;
    }

    setParameter(paramName, paramValue) {
        this._parameters.set(paramName, paramValue);
    }

    getParameter(paramName) {
        if (this._parameters.has(paramName)) {
            return this._parameters.get(paramName);
        } else {
            return ;
        }
    }

    get method() {
        return this._method;
    }

    set method(inputMethod) {
        if (!!inputMethod && this._availableMethods.indexOf(inputMethod.toUpperCase()) >= 0) {
            this._method = inputMethod.toUpperCase();
        } else {
            this._method = 'GET';
        }
    }

    navigate() {
        if (!this._isCalled) {
            this._callApiSpec = pactum.spec();

            if (this.method === 'GET') {
                this._callApiSpec.get(this.url);
            } else if (this.method == 'POST') {
                this._callApiSpec.post(this.url);
            } else if (this.method == 'PUT') {
                this._callApiSpec.put(this.url);
            } else if (this.method == 'DELETE') {
                this._callApiSpec.delete(this.url);
            }

            if (this.headers.size > 0) this._callApiSpec.withHeaders(Object.fromEntries(this._headers));
            if (this.pathParameters.size > 0) this._callApiSpec.withPathParams(Object.fromEntries(this.pathParameters));
            if (this.parameters.size > 0) this._callApiSpec.withQueryParams(Object.fromEntries(this.parameters));

            if ((typeof this.body) === 'object') {
                this._callApiSpec.withJson(this.body);
            } else {
                this._callApiSpec.withBody(this.body.toString());
            }

            this._isCalled = true;
        }

        return this._callApiSpec;
    }

    reset() {
        this._isCalled = false;
        this._callApiSpec = undefined;
    }

    getResponse() {
        if (this._isCalled) {
            return this._callApiSpec._response.json;
        } else {
            return {};
        }
    }

    getResponseHeaders() {
        if (this._isCalled) {
            return this._callApiSpec._response['headers'];
        } else {
            return {};
        }
    }

    getResponseBody() {
        if (this._isCalled) {
            return this._callApiSpec._response['body'];
        } else {
            return {};
        }
    }

    getStatusCode() {
        if (this._isCalled) {
            return this._callApiSpec._response.statusCode;
        } else {
            return -1;
        }
    }
}

module.exports = Navigator;