package org.pw.dto;

import java.awt.geom.Area;
import java.util.ArrayList;

public class FuncNodeObj {
	int id;
	String rel_id;
	String name;
	double qnty;
	double ar_each;
	double dim;
	double xPos;
	double yPos;
	double weight;
	int[] rgb;
	String adj_to;
	boolean selected=false;
	String zone;
        double[] aspect_ratio;
        String location;
        String department;
	ArrayList<Area>graphArea;
	
	public FuncNodeObj(double xPos_, double yPos_){
		xPos=xPos_;
		yPos=yPos_;
	}
	
	public FuncNodeObj(int id_, String name_, double qnty_, double ar_each_, double dim_,
			String adj_to_, int[] rgb_, String loc_, String zone_, String dept_,
                        double[] asp_r_){
            id=id_;
            name=name_;
            qnty=qnty_;
            ar_each=ar_each_;
            dim=dim_;
            adj_to=adj_to_;      
            rgb=new int[3];
            for(int i=0; i<rgb_.length; i++){
                    rgb[i]=rgb_[i];
            }
            location=loc_;
            rel_id="0";
            graphArea=new ArrayList<Area>();
            zone=zone_;
            aspect_ratio=asp_r_;
            department=dept_;
            weight=qnty*ar_each;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getRel_id() {
		return rel_id;
	}

	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQnty() {
		return qnty;
	}

	public void setQnty(double qnty) {
		this.qnty = qnty;
	}

	public double getAr_each() {
		return ar_each;
	}

	public void setAr_each(double ar_each) {
		this.ar_each = ar_each;
	}

	public double getDim() {
		return dim;
	}

	public void setDim(double dim) {
		this.dim = dim;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int[] getRgb() {
		return rgb;
	}

	public void setRgb(int[] rgb) {
		this.rgb = rgb;
	}

	public String getAdj_to() {
		return adj_to;
	}

	public void setAdj_to(String adj_to) {
		this.adj_to = adj_to;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String loc_) {
		this.location = location;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public ArrayList<Area> getGraphNodeArea() {
		return graphArea;
	}

	public void setGraphNodeArea(ArrayList<Area> graphicArea) {
		this.graphArea = graphicArea;
	}
        public String getDeparment(){
            return department;
        }
        public void setDepartment(String s){
            department=s;
        }
        public double[] getAspectRatio(){
            return aspect_ratio;
        }
        public void setAspectRatio(double[] asp_){
            aspect_ratio=asp_;
        }
        
        public String returnInfo(){
            double asp0=aspect_ratio[0];
            double asp1=aspect_ratio[1];
            String asp_rat=asp0+","+asp1;
            String s=("id:"+id+"|- name "+name+"|- dept  "+department+"|- zone "+zone+"|-qnty" + qnty+
                    "|- area(each)"+ar_each + "|- aspect "+asp_rat);
            return s;
        }
	
}
