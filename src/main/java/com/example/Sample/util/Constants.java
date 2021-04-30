package com.example.Sample.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:constants.properties")
public class Constants {

    @Value( "${acceptedHeaders}" )
    public String acceptedHeaders;

}
