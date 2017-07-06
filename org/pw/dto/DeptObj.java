package org.pw.dto;

public class DeptObj {
    String name;
    double area;
    
    public DeptObj(String name_, double ar_){
        name=name_;
        area=ar_;
    }
    public String getName(){
        return name;
    }
    public double getArea(){
        return area;
    }
    public void setName(String n_){
        name=n_;
    }
    public void setArea(double ar_){
        area=ar_;
    }
}
