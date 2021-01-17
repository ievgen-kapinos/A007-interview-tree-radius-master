package com.holidu.interview.assignment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController
{
  @Value("${tree-radius.api-endpoint}")
  private String apiEndpoint;
  
  @Value("${tree-radius.default.x}")
  private double defaultX;
  
  @Value("${tree-radius.default.y}")
  private double defaultY;
  
  @Value("${tree-radius.default.radius}")
  private double defaultRadius;
  
  @GetMapping("/")
  public String index( Model model ) 
  {
    model.addAttribute("apiEndpoint", apiEndpoint);
    model.addAttribute("defaultSearchRequest", new SearchRequest(defaultX, 
                                                                 defaultY, 
                                                                 defaultRadius));
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
