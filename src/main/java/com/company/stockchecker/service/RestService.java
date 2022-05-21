package com.company.stockchecker.service;

import com.company.stockchecker.config.WebClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class RestService {

    private final WebClientConfig webClientConfig;
    private final Logger logger = LoggerFactory.getLogger(RestService.class);

    public RestService(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    public <T> T makeRequest(String URL, String header, String headerValue, Class<T> clazz, HttpMethod httpMethod){
        return webClientConfig.getWebClient()
                .build()
                .method(httpMethod)
                .uri(URL)
                .header(header, headerValue)
                .retrieve()
                .bodyToMono(clazz)
                .blockOptional()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    public String encodeUrlUTF8(String URL){

        try {

            URL = URLEncoder.encode(URL, StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            logger.error("Error during URL encoding " + URL);
            e.printStackTrace();
        }

        return URL;

    }

}
