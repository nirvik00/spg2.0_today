package org.pw.dto;

import java.awt.geom.GeneralPath;

public class FuncObj {
    GeneralPath path;
    String cellsOccupied;
    String zone;
    String name;
    double numerical_area;
    int func_id;
    int[] rgb;
    double[] aspect_ratio;
    public FuncObj(GeneralPath path_, double ar_, String cell_id_, String name_,
    		String z_, int func_id_, int[] rgb_, double[] asp_){
    	path=path_;
    	numerical_area = ar_;
    	cellsOccupied=cell_id_;
    	name=name_;
    	zone=z_;
        func_id=func_id_;
        rgb=rgb_;
        aspect_ratio=asp_;
    }
    public void setRgb(int[] rgb_){
        rgb=rgb_;
    }
    public int[] getRgb(){
        return rgb;
    }
    public void setId(int id_){
        func_id=id_;
    }
    public int getId(){
        return func_id;
    }
    public String getName() {
		return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public double getNumerical_area() {
            return numerical_area;
    }

    public void setNumerical_area(double numerical_area) {
            this.numerical_area = numerical_area;
    }

    public GeneralPath getPath() {
            return path;
    }

    public void setPath(GeneralPath path) {
            this.path = path;
    }

    public String getCellsOccupied() {
            return cellsOccupied;
    }

    public void setCellsOccupied(String cellsOccupied) {
            this.cellsOccupied = cellsOccupied;
    }

    public String getZone() {
            return zone;
    }

    public void setZone(String zone) {
            this.zone = zone;
    }

    public void setAspectRatio(double[] asp_){
        aspect_ratio=asp_;
    }
    public double[] getAspectRatio(){
        return aspect_ratio;
    }
}
