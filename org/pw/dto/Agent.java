package org.pw.dto;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Agent {
    FuncObj funcobj;
    double rad;
    double[] pos;
    public Agent(double r_){
        pos=new double[2];
        rad=r_;
    }
    public void setPos(double[] mp){
        pos[0]=mp[0];
        pos[1]=mp[1];
    }
    public Ellipse2D display(){
        Ellipse2D e=new Ellipse2D.Double(pos[0]-rad/2,pos[1]-rad/2,rad,rad);
        return e;
    }
}
