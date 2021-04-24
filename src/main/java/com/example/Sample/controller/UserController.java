package com.example.Sample.controller;

import com.example.Sample.model.User;
import com.example.Sample.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * The type User controller.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/1")
    public ResponseEntity<String> addUser(HttpEntity<User> httpEntity,
                                          @RequestParam Map<String, String> requestParams,
                                          @RequestHeader MultiValueMap<String, String> headers,
                                          HttpServletRequest httpServletRequest) throws JsonProcessingException {
        User user = httpEntity.getBody();
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getRequestURL());
        System.out.println(requestParams);
        System.out.println(headers);
        userService.saveUser(user, requestParams, headers, httpServletRequest.getRequestURI());
//        UserPayload userPayload = new UserPayload();
//        userPayload.setUrl(String.valueOf(httpServletRequest.getRequestURL()));
//        userPayload.setQueryParams(requestParams);
//        userPayload.setHeaders(mapOfHeaders);
//        userPayload.setBody(new Gson().toJson(user));

        return null;
    }

}
