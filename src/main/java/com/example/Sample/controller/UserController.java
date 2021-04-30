package com.example.Sample.controller;

import com.example.Sample.model.User;
import com.example.Sample.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * UserController, Handles the Request from the client.
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;




    /**
     * addUser(
     * @param httpEntity,
     * @param requestParams,
     * @param headers,
     * @param httpServletRequest)
     * This EndPoint adds the user to the DB(Insert or Update).
     *
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/1")
    public ResponseEntity<String> addUser(HttpEntity<User> httpEntity,
                                          @RequestParam Map<String, String> requestParams,
                                          @RequestHeader MultiValueMap<String, String> headers,
                                          HttpServletRequest httpServletRequest) throws JsonProcessingException {
        User user = httpEntity.getBody();
        log.info(httpServletRequest.getRequestURI());
        log.info(String.valueOf(httpServletRequest.getRequestURL()));
        log.info(String.valueOf(requestParams));
        log.info(String.valueOf(headers));
        userService.saveUser(user, requestParams, headers, httpServletRequest.getRequestURI());
        return null;
    }

}
