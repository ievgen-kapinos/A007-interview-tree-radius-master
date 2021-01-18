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

  private final UserSearchRequest defaultUserSearchRequest;
  private final String apiEndpoint;
  private final RestOperations restTemplate;
  
  private final String apiQuery;

  @Autowired
  public AppController(@Value("${tree-radius.api-endpoint}") String apiEndpoint,
                       @Value("${tree-radius.default.x}") double defaultX, 
                       @Value("${tree-radius.default.y}") double defaultY, 
                       @Value("${tree-radius.default.radius}") double defaultRadius,
                       RestTemplate restTemplate)
  {
    this.apiEndpoint = apiEndpoint;
    this.defaultUserSearchRequest = new UserSearchRequest(defaultX, 
                                                          defaultY, 
                                                          defaultRadius);
    this.restTemplate = restTemplate; 
    
    String query = 
        " SELECT                             " +
        "   spc_common AS commonName,        " +
        "   COUNT(*) AS count                " +
        " WHERE                              " +
        "   within_circle('POINT ({X} {Y})', " +
        "                 latitude,          " +
        "                 longitude,         " +
        "                 {radius})          " +
        " GROUP BY                           " +
        "   spc_common                       " +
        ""; 
    
    // Shortening the URL by removing extra spaces
    this.apiQuery = apiEndpoint + "?$query=" + query.replaceAll("\\s+", " "); 
  }
  
  @GetMapping("/")
  public String index( Model model ) 
  {
    model.addAttribute("apiEndpoint", apiEndpoint);
    model.addAttribute("defaultUserSearchRequest", defaultUserSearchRequest);
    return "index";
  }
  
  @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public Map<String, Integer> search(@RequestBody UserSearchRequest userRequest) 
  {
    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("X", userRequest.getX());
    uriVariables.put("Y", userRequest.getY());
    uriVariables.put("radius", userRequest.getRadius());
    
    ApiSearchResponseEntry[] apiResponse = restTemplate.getForObject(apiQuery,
                                                                     ApiSearchResponseEntry[].class, 
                                                                     uriVariables);
    
    Map<String, Integer> userResponse = new HashMap<>();
    for (ApiSearchResponseEntry apiResponseEntry : apiResponse)
    {
      logger.info("{}", apiResponseEntry);
      
      String commonName = apiResponseEntry.getCommonName();
      if (commonName == null)
      {
        commonName = "<empty>";
      }
      userResponse.put(commonName, apiResponseEntry.getCount());
    } 
    return userResponse;
  }
}
