package com.holidu.interview.assignment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;

@Controller
public class AppController
{
  private final SearchRequest defaultSearchRequest;
  
  private final String apiEndpoint;
  
  private final RestOperations restTemplate;
  
  @Autowired
  public AppController(@Value("${tree-radius.api-endpoint}") String apiEndpoint,
                       @Value("${tree-radius.default.x}") double defaultX, 
                       @Value("${tree-radius.default.y}") double defaultY, 
                       @Value("${tree-radius.default.radius}") double defaultRadius,
                       RestTemplateBuilder restTemplateBuilder)
  {
    this.apiEndpoint = apiEndpoint;
    this.defaultSearchRequest = new SearchRequest(defaultX, 
                                             defaultY, 
                                             defaultRadius);
    
    this.restTemplate = restTemplateBuilder.build();
  }
  
  @GetMapping("/")
  public String index( Model model ) 
  {
    model.addAttribute("apiEndpoint", apiEndpoint);
    model.addAttribute("defaultSearchRequest", defaultSearchRequest);
    return "index";
  }
  
  @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public Map<String, Integer> search(@RequestBody SearchRequest searchRequest) 
  {
    Map<String, Integer> response = new HashMap<>();
    
    
    
    response.put("name", 7);
    response.put("name2", 8);
    
    return response;
  }
}
