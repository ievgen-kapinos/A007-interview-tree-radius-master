package com.holidu.interview.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserSearchRequest
{
  private final double x;
  private final double y;
  private final double radius;
  
  @JsonCreator
  public UserSearchRequest(double x, 
                           double y, 
                           double radius)
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
    return "UserSearchRequest [x=" + x + 
        ", y=" + y + 
        ", radius=" + radius + 
        "]";
  } 
}
