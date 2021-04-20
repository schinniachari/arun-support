package com.example.Sample.service;

import com.example.Sample.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface IUserService {

    User saveUser(User user, Map<String, String> requestParams, MultiValueMap<String, String> headers, StringBuffer requestURL) throws JsonProcessingException;
}
