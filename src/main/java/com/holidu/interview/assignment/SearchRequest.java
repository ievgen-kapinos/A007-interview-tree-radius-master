package com.holidu.interview.assignment;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SearchRequest
{
  private final double x;
  private final double y;
  private final double radius;
  
  @JsonCreator
  public SearchRequest(double x, double y, double radius)
  {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public double getRadius()
  {
    return radius;
  }

  @Override
  public String toString()
  {
    return "SearchRequest [x=" + x + 
        ", y=" + y + 
        ", radius=" + radius + 
        "]";
  } 
}
