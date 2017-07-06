  package org.pw.dto;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import javafx.scene.shape.Path;

public class CellObj {
    int id;
    String zone;
    String vertices;
    double[] verX;
    double[] verY;
    double numerical_ar;
    String name;
    boolean filled;
    String locationOnTree;
    String func;
    int[] Rgb;
    public boolean ignore; //sometimes loops are getting stuck
    int numUsed; //number of times this cell was used
    
    public CellObj(int id_, String[] ver_, String zone_){
        Rgb=new int[4];
        locationOnTree="0,";
        id=id_;
        verX=new double[ver_.length];
        verY=new double[ver_.length];
        zone=zone_;
        for (int i=0; i<ver_.length; i++){
            double x=Double.parseDouble(ver_[i].split(",")[0]);
            double y=Double.parseDouble(ver_[i].split(",")[1]);
            verX[i]=x;
            verY[i]=y;
        }
        filled=false;
        ignore=false;
        numUsed=0;
    }
    
    public GeneralPath genPath(){
        GeneralPath path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        if(verX.length==4){
            path.moveTo(verX[0],verY[0]);
            path.lineTo(verX[1],verY[1]);
            path.lineTo(verX[2],verY[2]);
            path.lineTo(verX[3],verY[3]);
            path.closePath();
        }else if(verY.length==3){
            path.moveTo(verX[0],verY[0]);
            path.lineTo(verX[1],verY[1]);
            path.lineTo(verX[2],verY[2]);
            path.closePath(); 
        }
        return path;
    }
    
    public int getId(){
        return id;
    }
    
    public double[] getVerX(){
        return verX;
    }
    
    public double[] getVerY(){
        return verY;
    }
    
    public double getDiagLength(){
        double x0=verX[0];
        double y0=verY[0];
        double x1=verX[2];
        double y1=verY[2];
        double d1= Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d1;
    }
    
    public void setId(int id_){
        id=id_;
    }
    
    public String getVertices(){
        return vertices;
    }    
    
    public void setVertices(String v_){
        vertices=v_;
    }
    
    public double[] getMidPt(){
        double mpx=(verX[0]+verX[2])/2;
        double mpy=(verY[0]+verY[2])/2;
        double[] mp=new double[2];
        mp[0]=mpx;
        mp[1]=mpy;        
        return mp;
    }
    
    public String getZone(){
        return zone;
    }
    
    public void setZone(String zone_){
        zone=zone_;
    }
    
    public double getNumericalArea(){
        double x0=verX[0];
        double y0=verY[0];
        double x1=verX[1];
        double y1=verY[1];
        double x2=verX[2];
        double y2=verY[2];
        if(verX.length==4){
            double x3=verX[3];
            double y3=verY[3];
            double d0=dis(x0,y0,x1,y1);
            double d1=dis(x1,y1,x2,y2);
            numerical_ar=d0*d1;
        }else{
            double d01=dis(x0,y0,x1,y1);
            double d12=dis(x1,y1,x2,y2);
            double d20=dis(x2,y2,x0,y0);
            double s=(d01+d12+d20)/2;
            double numerical_ar=Math.sqrt(s*(s-d01)*(s-d12)*(s-d20));
        }        
        return numerical_ar;
    }
    
    public void setNumericalArea(double ar_){
        numerical_ar=ar_;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name_){
        name=name_;
    }
    
    public double[] getPos(){
        double x=verX[0];
        double y=verY[0];
        double[] pt=new double[2];
        pt[0]=x;
        pt[1]=y;
        return pt;
    }
    
    public boolean getFilled(){
        return filled;
    }
    
    public void setFilled(boolean t){
        filled=t;
    }
    
    public String getLocationOnTree(){
        return locationOnTree;
    }
    
    public void setLocationOnTree(String t){
        locationOnTree+=t+",";
    }
    
    public int[] getRGB(){
        return Rgb;
    }
    
    public void setRGB(int r, int g, int b, int tr_){
        Rgb[0]=r;
        Rgb[1]=g;
        Rgb[2]=b;
        Rgb[3]=tr_;
    }
    
    public void clearAttribute(){
        Rgb[0]=255;
        Rgb[1]=255;
        Rgb[2]=255;
        locationOnTree="";
    }
    
    public String getFunc(){
        return func;
    }
    
    public void setFunc(String func_){
        func=func_;
    }
    
    public boolean pointInQuad(double[] pt){
        boolean t=false;
        double a=pt[0];
        double b=pt[1];
        if(verX.length>2){
            if(a>verX[0] && a<verX[2] && b>verY[0] && b<verY[2]){
                t=true;
            }
        }
        return t;
    }
    
    public void setIgnore(boolean t){
        ignore=t;
    }
    
    public boolean getIgnore(){
        return ignore; 
    }
    
    public double dis(double x0, double y0, double x1, double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }
    
    public void clrAttributes(){
        id=1;
        zone="";
        vertices="";
        double[] verX;
        double[] verY;
        numerical_ar=0;
        name="";
        filled=false;
        locationOnTree="";
        func="";
        int[] Rgb;
        ignore=false; 
    }
        
    public GeneralPath getGeneralGeo(double ar_req, double[] asp, boolean rotate){
        double m0,n0,m1,n1;
        double d0,d1;
        if(rotate==true){
            System.out.println("rotated in cellobj");
            d0=asp[1]*Math.sqrt(ar_req/(asp[0]*asp[1]));
            d1=asp[0]*Math.sqrt(ar_req/(asp[0]*asp[1]));
        }else{
            d0=asp[0]*Math.sqrt(ar_req/(asp[0]*asp[1]));
            d1=asp[1]*Math.sqrt(ar_req/(asp[0]*asp[1]));
        }
        if(getVerX().length >3){
            m0=getVerX()[0];
            n0=getVerY()[0];
            m1=getVerX()[3];        
            n1=getVerY()[3];
        }
        else{
            m0=getVerX()[0];
            n0=getVerY()[0];
            m1=getVerX()[2];        
            n1=getVerY()[2];
        }
        double df0=dis(m0,n0,m1,n1);

        double x0=m0;
        double x1=((m1-m0)*(d0/df0))+m0;
        double y0=n0;
        double y1=((n1-n0)*(d0/df0))+n0;
        
        double dx0=x0-x1;
        double dy0=y0-y1;
        
        double px0= dy0*Math.sin(Math.PI/2) + x1;
        double py0= -dx0*Math.sin(Math.PI/2) + y1;
        double di0=dis(x1,y1,px0,py0);
        double di00=(d1/di0);
        double sx0=(px0-x1)*di00+x1;
        double sy0=(py0-y1)*di00+y1;

        double dx1=x1-x0;
        double dy1=y1-y0;
        double px1= -dy1*Math.sin(Math.PI/2) + x0;
        double py1= dx1*Math.sin(Math.PI/2) + y0;
        double di1=dis(x0,y0,px1,py1);
        double di01=(d1/di1);
        double sx1=(px1-x0)*di01+x0;
        double sy1=(py1-y0)*di01+y0;
        
        double di_0=dis(x1,y1,sx0,sy0);
        double di_1=dis(x0,y0,x1,y1);
        String s1=x1+","+y1+","+sx0+","+sy0+","+sx1+","+sy1+","+x0+","+y0;
        
        GeneralPath path=new GeneralPath();
        path.moveTo(x1, y1);
        path.lineTo(sx0, sy0);
        path.lineTo(sx1, sy1);
        path.lineTo(x0, y0);
        path.closePath();
        return path;
    }
    
    public GeneralPath getPeripheralGeo(double ar_req){
        double x0,y0,x1,y1;
        if(getVerX().length>3){ //QUAD
            x0=getVerX()[0];
            y0=getVerY()[0];
            x1=getVerX()[3];        
            y1=getVerY()[3];
        }else{                  //TRIANGLE
            x0=getVerX()[0];
            y0=getVerY()[0];
            x1=getVerX()[1];        
            y1=getVerY()[1];
        }

        double d0=dis(x0,y0,x1,y1);
        double d1=ar_req/d0;
        double dx0=x0-x1;
        double dy0=y0-y1;
        double px0= dy0*Math.sin(Math.PI/2) + x1;
        double py0= -dx0*Math.sin(Math.PI/2) + y1;
        double di0=dis(x1,y1,px0,py0);
        double di00=(d1/di0);
        double sx0=(px0-x1)*di00+x1;
        double sy0=(py0-y1)*di00+y1;

        double dx1=x1-x0;
        double dy1=y1-y0;
        double px1= -dy1*Math.sin(Math.PI/2) + x0;
        double py1= dx1*Math.sin(Math.PI/2) + y0;
        double di1=dis(x0,y0,px1,py1);
        double di01=(d1/di0);
        double sx1=(px1-x0)*di01+x0;
        double sy1=(py1-y0)*di01+y0;
        
        double di_0=dis(x1,y1,sx0,sy0);
        double di_1=dis(x0,y0,x1,y1);
        
        String s1=x1+","+y1+","+sx0+","+sy0+","+sx1+","+sy1+","+x0+","+y0;
        
        GeneralPath path=new GeneralPath();
        path.moveTo(x1, y1);
        path.lineTo(sx0, sy0);
        path.lineTo(sx1, sy1);
        path.lineTo(x0, y0);
        path.closePath();
        return path;
    }
    
    
    /*
    *   NUMBER OF TIMES THE CELL WAS OCCUPIED : FITNESS
    */
    public int getNumUsed(){
        return numUsed;
    }
    
    public void addNumUsed(){
        numUsed++;
    }
    
    public void nullNumUsed(){
        numUsed=0;
    }
}
