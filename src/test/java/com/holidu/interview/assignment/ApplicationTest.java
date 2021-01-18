package com.holidu.interview.assignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.holidu.interview.assignment.dto.ApiSearchResponseEntry;
import com.holidu.interview.assignment.dto.UserSearchRequest;

// In general we should split UTests and do not test everything in one place

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;
    
    @MockBean
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldLoadContext() 
    {
      assertThat(applicationContext).isNotNull();
    }
    
    @Test
    public void testSearch() throws Exception
    {
      ApiSearchResponseEntry[] apiResponse = new ApiSearchResponseEntry[] 
      {
        new ApiSearchResponseEntry("test1", 1),
        new ApiSearchResponseEntry("test2", 2),
      };
      when(restTemplate.getForObject(any(), eq(ApiSearchResponseEntry[].class), anyMap())).thenReturn(apiResponse);
      
      UserSearchRequest request = new UserSearchRequest(1, 2, 3);    

      Map<String, Integer> userResponse = testRestTemplate.exchange("http://localhost:" + port + "/search", 
                                                                    HttpMethod.POST, 
                                                                    new HttpEntity<>(request), 
                                                                    new ParameterizedTypeReference<Map<String, Integer>>() {})
                                                          .getBody();    
      assertEquals(2, userResponse.size());
      assertEquals(1, userResponse.get("test1").intValue());
      assertEquals(2, userResponse.get("test2").intValue());
    }
}
