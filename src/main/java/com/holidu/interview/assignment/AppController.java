package com.holidu.interview.assignment;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.holidu.interview.assignment.dto.ApiSearchResponseEntry;
import com.holidu.interview.assignment.dto.UserSearchRequest;

@Controller
public class AppController
{
  private static final Logger logger = LoggerFactory.getLogger(AppController.class); 

  private final UserSearchRequest defaultSearchRequest;
  
  private final String apiEndpoint;
  
  private final RestOperations restTemplate;
  
  @Autowired
  public AppController(@Value("${tree-radius.api-endpoint}") String apiEndpoint,
                       @Value("${tree-radius.default.x}") double defaultX, 
                       @Value("${tree-radius.default.y}") double defaultY, 
                       @Value("${tree-radius.default.radius}") double defaultRadius,
                       RestTemplate restTemplate)
  {
    this.apiEndpoint = apiEndpoint;
    this.defaultSearchRequest = new UserSearchRequest(defaultX, 
                                                     defaultY, 
                                                     defaultRadius);
    this.restTemplate = restTemplate; 
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
  public Map<String, Integer> search(@RequestBody UserSearchRequest searchRequest) 
  {
    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("X", searchRequest.getX());
    uriVariables.put("Y", searchRequest.getY());
    uriVariables.put("radius", searchRequest.getRadius());
    
    String query = 
        " SELECT                            " +
        "  spc_common AS commonName,        " +
        "  COUNT(*) AS count                " +
        " WHERE                             " +
        "  within_circle('POINT ({X} {Y})', " +
        "                latitude,          " +
        "                longitude,         " +
        "                {radius})          " +
        " GROUP BY                          " +
        "  spc_common                       " +
        ""; 
    
    // Shorting URL by removing extra spaces  
    query = query.replaceAll("\\s+", " "); 
    ApiSearchResponseEntry[] responseEntities = 
        restTemplate.getForObject(apiEndpoint + "?$query=" + query, 
                                  ApiSearchResponseEntry[].class, 
                                  uriVariables);
    
    Map<String, Integer> response = new HashMap<>();
    for (ApiSearchResponseEntry responseEntity : responseEntities)
    {
      logger.info("{}", responseEntity);
      
      String commonName = responseEntity.getCommonName();
      if (commonName == null)
      {
        commonName = "<empty>";
      }
      response.put(commonName, responseEntity.getCount());
    } 
    return response;
  }
}
