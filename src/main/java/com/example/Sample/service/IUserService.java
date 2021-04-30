package com.example.Sample.service;

import com.example.Sample.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * IUserService interface provides the abstract methods for the UserService implementation
 *
 */
public interface IUserService {

    /**
     * saveUser(
     * @param user,
     * @param requestParams,
     * @param headers,
     * @param requestURL)
     * To save the user in DB.
     *
     * @return
     * @throws JsonProcessingException
     */
    Mono<User> saveUser(User user, Map<String, String> requestParams, MultiValueMap<String, String> headers, String requestURL) throws JsonProcessingException;
}
