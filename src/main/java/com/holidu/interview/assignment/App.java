package com.holidu.interview.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App 
{
    public static void main(String[] args) 
    {
      SpringApplication.run(App.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate()
    {
      // Use Apache implementation for better logging
      // https://www.baeldung.com/spring-resttemplate-logging
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
      return restTemplate;
    }
}
