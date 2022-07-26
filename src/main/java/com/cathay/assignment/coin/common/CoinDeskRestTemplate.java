package com.cathay.assignment.coin.common;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinDeskRestTemplate extends RestTemplate {

    private static final Integer TIMEOUT = 10000;

    public RestTemplate getTemplate() {
        HttpsClientRequestFactory httpsClientRequestFactory = new HttpsClientRequestFactory();
        httpsClientRequestFactory.setConnectTimeout(TIMEOUT);
        RestTemplate restTemplate = new RestTemplate(httpsClientRequestFactory);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
