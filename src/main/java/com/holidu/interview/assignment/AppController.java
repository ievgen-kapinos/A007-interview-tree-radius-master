package com.holidu.interview.assignment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController
{
  @Value("${tree-radius.api-endpoint}")
  private String apiEndpoint;
  
  // TODO aggregate on DTO
  @Value("${tree-radius.default.x_sp}")
  private double defaultX;
  
  @Value("${tree-radius.default.y_sp}")
  private double defaultY;
  
  @Value("${tree-radius.default.radius}")
  private double defaultRadius;
  
  @GetMapping("/")
  public String index( Model model ) 
  {
    model.addAttribute("apiEndpoint", apiEndpoint);
    model.addAttribute("defaultX", defaultX);
    model.addAttribute("defaultY", defaultY);
    model.addAttribute("defaultRadius", defaultRadius);
    return "index";
  }
}
