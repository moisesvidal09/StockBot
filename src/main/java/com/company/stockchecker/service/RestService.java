package com.company.stockchecker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestService {

    private final WebClient.Builder webClient = WebClient.builder();

    public WebClient.ResponseSpec makeRequest(String URI, String header, String headerValue){
        return webClient.build()
                .get()
                .uri(URI)
                .header(header, headerValue)
                .retrieve();
    }

}
