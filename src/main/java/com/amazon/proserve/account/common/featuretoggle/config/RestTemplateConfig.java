package com.amazon.proserve.account.common.featuretoggle.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages={"com.amazon.proserve.account.common.featuretoggle.config"})
//@ConfigurationPropertiesScan(basePackages={"com.amazon.proserve.account.common.featuretoggle.config"})
public class RestTemplateConfig {

	@Autowired
	CloseableHttpClient httpClient;

//	@Value("${api.host.baseurl}")
	@Value("${endpoint.url.featureToggle}")
	private String apiUrl;

	@Value("${endpoint.uri.featureToggle}")
	private String apiUri;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiUrl + apiUri));
		return restTemplate;
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
		return clientHttpRequestFactory;
	}
}
