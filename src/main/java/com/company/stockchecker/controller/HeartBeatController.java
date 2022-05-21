package com.company.stockchecker.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/heartbeat")
public class HeartBeatController {

    private final Logger logger = LoggerFactory.getLogger(HeartBeatController.class);

    @GetMapping
    @SneakyThrows
    public ResponseEntity<String> getHeartBeat(){
        Thread.sleep(7500);
        logger.info("Alive \\o/");
        return new ResponseEntity<String>("Alive", HttpStatus.OK);
    }


}
