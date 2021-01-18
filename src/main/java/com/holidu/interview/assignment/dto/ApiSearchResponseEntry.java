package com.holidu.interview.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiSearchResponseEntry
{
  private final String commonName;
  private final int count;
  
  @JsonCreator
  public ApiSearchResponseEntry(@JsonProperty("commonName") String commonName, 
                                @JsonProperty("count") int count)
  {
    this.commonName = commonName;
    this.count = count;
  }

  public String getCommonName()
  {
    return commonName;
  }

  public int getCount()
  {
    return count;
  }

  @Override
  public String toString()
  {
    return "ApiSearchResponseEntry [commonName=" + commonName + 
        ", count=" + count + 
        "]";
  }
   
}
