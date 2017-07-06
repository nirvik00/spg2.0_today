package org.pw.GA.func;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.pw.compare.CompareIndex0;
import org.pw.compare.CompareIndex1;
import org.pw.dto.Agent;
import org.pw.dto.CellObj;
import org.pw.dto.DeptObj;
import org.pw.dto.FuncObj;
import org.pw.dto.FuncNodeObj;

public class GAFuncPanel extends JPanel implements ActionListener{
    
    String STATUS="";

    ArrayList<FuncNodeObj>funcNodeObjList;
    ArrayList<FuncObj>funcObjList;
    ArrayList<CellObj>cellObjList;
    
    ArrayList<CellObj>interCellObjList;
    ArrayList<GeneralPath>interPathList;
    ArrayList<Integer>interIdList;
    
    ArrayList<CellObj>periCellObjList;
    ArrayList<GeneralPath>periPathList;
    ArrayList<Integer>periIdList;

    ArrayList<CellObj>genCellObjList;
    ArrayList<GeneralPath>genPathList;
    ArrayList<Integer>genIdList;
    
    ArrayList<DeptObj>deptObjList;
    
    HashMap<String,Double> mapArea;
    
    double scaleX,scaleY,translateX, translateY;
    double fitness_peri, fitness_cells_used_peri_limit;
    double fitness_cells_used_inter, fitness_inter, fitness_defi;
    double fitness_cells_used_gen, fitness_gen;
    
    int entryclicksize=10;
    Timer timer;
    Random rnd;
    Agent agent;
    boolean showGrid=false;
    
    public GAFuncPanel(ArrayList<FuncNodeObj>graphNodeList, 
            ArrayList<CellObj>GLOBAL_CellObjList){

        timer=new Timer(100,this);
        timer.start();
        rnd=new Random();
        
        funcNodeObjList=new ArrayList<FuncNodeObj>();
        funcNodeObjList.clear();
        funcNodeObjList.addAll(graphNodeList);

        funcObjList=new ArrayList<FuncObj>();
        funcObjList.clear();        
        
        cellObjList=new ArrayList<CellObj>();
        cellObjList.addAll(GLOBAL_CellObjList);
        
        periCellObjList=new ArrayList<CellObj>();
        periPathList=new ArrayList<GeneralPath>();
        
        interCellObjList=new ArrayList<CellObj>();
        interPathList=new ArrayList<GeneralPath>();
        
        genCellObjList=new ArrayList<CellObj>();
        genPathList=new ArrayList<GeneralPath>();

        deptObjList=new ArrayList<DeptObj>();
        
        scaleX=1;
        scaleY=1;
        translateX=0;
        translateY=0;
        
        fitness_peri=0;
        fitness_cells_used_peri_limit=0;
        
        fitness_cells_used_inter=0;
        fitness_inter=0;        
        
        fitness_cells_used_gen=0;
        fitness_gen=0;
        
        
        agent=new Agent(3);
        double ar_cells=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj cellobj = cellObjList.get(i);
            double ar=cellobj.getNumericalArea();
            ar_cells+=ar;
        }
        //System.out.println("total ar of cells : " +ar_cells);
        /*
        *   departments
        */
        deptObjList.clear();
        ArrayList<String>dept_names=new ArrayList<String>();
        for(int i=0; i<funcNodeObjList.size(); i++){
            FuncNodeObj obj=funcNodeObjList.get(i);
            String dept0=obj.getDeparment().toLowerCase();
            int sum=0;
            for(int j=0; j<dept_names.size();j++){
                String dept1=dept_names.get(j).toLowerCase();
                if(dept0.equals(dept1)){
                    sum++;
                }
            }
            if(sum==0){
                dept_names.add(dept0);
            }
        }
        //System.out.println("number of depts = "+dept_names.size());
        //System.out.println("name of depts = "+dept_names);
        for(int i=0; i<dept_names.size(); i++){
            String s=dept_names.get(i).toLowerCase();
            double ar_sum=0;
            for(int j=0; j<funcNodeObjList.size();j++){
                FuncNodeObj obj=funcNodeObjList.get(j);
                String name=obj.getDeparment().toLowerCase();
                double qnty=obj.getQnty();
                double ar=qnty*obj.getAr_each();
                if(s.equals(name)){
                    ar_sum+=ar;
                }
            }
            DeptObj deptobj=new DeptObj(s, ar_sum);
            deptObjList.add(deptobj);
        }
        
        for(int i=0; i<deptObjList.size(); i++){
            DeptObj obj=deptObjList.get(i);
            //System.out.println(obj.getName() + "," +obj.getArea());
        }
    }
   
    
    public void Update(double sx, double sy, double tx, double ty){
        scaleX=sx;
        scaleY=sy;
        translateX=tx;
        translateY=ty;
    }
    
    
    public void getPeriCells(){
        periCellObjList.clear();
        int idx=1;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            int id=c.getId();
            String zone=c.getZone();
            if(zone.equals("peripheral")){// || zone.equals("corner")){
                c.setId(idx);
                c.nullNumUsed();
                periCellObjList.add(c);
            }
        }
        periCellObjList.trimToSize();        
        //  CLEAR ALL CELLS
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            c.setFilled(false);
        }        
    }

    
    public void getInterCells(){
        interCellObjList.clear();
        interPathList.clear();  
        int idx=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj obj=cellObjList.get(i);
            if(obj.getZone().equals("interstitial")){
                interCellObjList.add(obj);
                obj.setFilled(false);
                obj.nullNumUsed();
                obj.setId(idx);
                idx++;
            }
        }
    }
   
    
    public void getGeneralCells(){
        genCellObjList.clear();
        int idx=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj obj=cellObjList.get(i);
            if(obj.getZone().equals("general")){
                obj.setFilled(false);
                obj.nullNumUsed();
                obj.setId(idx);
                genCellObjList.add(obj);
                idx++;
            }
        }
    }
    
    
    public void updateCells(ArrayList<CellObj>req_cell_list, 
    		GeneralPath path, String node_name){
    	Area ar=new Area(path);
    	for(int i=0; i<req_cell_list.size(); i++){
            CellObj c=req_cell_list.get(i);
            double x=c.getMidPt()[0];
            double y=c.getMidPt()[1];
            boolean t=ar.contains(x,y);
            int sum=0;
            double[] verX=c.getVerX();
            double[] verY=c.getVerY();
            for(int j=0; j<verX.length; j++){
                if(ar.contains(verX[j], verY[j])==true){
                    sum++;
                }	            		
            }
            if(sum>0 || t==true){
                c.setName(node_name);
                c.setFilled(true);
                c.addNumUsed();
            }
            /*
            if(t==true){
                c.setName(node_name);
                c.setFilled(true);
                c.addNumUsed();
            }
            */
    	}
    }
    
    
    public String getCellId(ArrayList<CellObj>req_cell_list,
            GeneralPath path, boolean refine, 
            int num_vertices_occupied){
    	String s="";
        for(int i=0; i<req_cell_list.size(); i++){
            CellObj c=req_cell_list.get(i);
            if(c.getFilled()==false){
                int id=c.getId();
                double x=c.getMidPt()[0];
                double y=c.getMidPt()[1];
                Area a=new Area(path);
                boolean t=a.contains(x, y);
                int sum=0;
                if(refine==true){
                    double[] verX=c.getVerX();
                    double[] verY=c.getVerY();
                    for(int j=0; j<verX.length; j++){
                        if(a.contains(verX[j], verY[j])==true){
                            sum++;
                        }	            		
                    }
                    if(sum>=num_vertices_occupied){
                        s+=id+",";
                    }
                }else{
                    if(t==true){
                        s+=id+",";
                    }
                }
            }
        }
        
        return s;
    }
    
    
    
    public double checkAr_cellsOcc(ArrayList<CellObj>req_cell_list, 
            double ar, String id_str){
    	int n=id_str.split(",").length;
    	double ar_sum=0;
    	for(int i=0; i<n; i++){
    		CellObj c=req_cell_list.get(i);
    		double arX=c.getNumericalArea();
    		ar_sum+=arX;
    	}
    	double diff=ar_sum/ar;
    	return diff;
    }
    
    
    
    public void plottedCells(boolean rotate){
        //System.out.println("--------CHECK PLOTTED -------");
        STATUS+="--------CHECK PLOTTED -------/n";
        //System.out.println("Total plotted cells : "+funcObjList.size());
        STATUS+="Total plotted cells : "+funcObjList.size()+"\n";
        for(int i=0; i<funcObjList.size(); i++){
            FuncObj funcobj=funcObjList.get(i);
            String name =funcobj.getName();
            String zone=funcobj.getZone();
            double ar=funcobj.getNumerical_area();
            //System.out.println(i +"> "+ name+" , "+zone+" , "+ar);
            STATUS+=i +"> "+ name+" , "+zone+" , "+ar+"\n";
        }
        /*
        *   CHECK FOR DEFICIT AND SEND IT FOR ACCOMMODATION
        */
        //System.out.println("--------DEFICIT PLOTTED -------");
        STATUS+="--------DEFICIT PLOTTED -------\n";
        for(int i=0; i<funcNodeObjList.size(); i++){
            FuncNodeObj nodeObj=funcNodeObjList.get(i);
            int nodeObj_id=nodeObj.getId();
            int req_num=(int)(nodeObj.getQnty());
            int sum=0;
            for(int j=0; j<funcObjList.size(); j++){
                FuncObj plotObj=funcObjList.get(j);
                int plotObj_id=plotObj.getId();
                if(plotObj.getId()==nodeObj_id){
                    sum++;
                }
            }
            int deficit_num=req_num-sum;
            double ar=nodeObj.getAr_each();
            String name=nodeObj.getName();
            int id=nodeObj.getId();
            int[] rgb=nodeObj.getRgb();
            double[] asp_=nodeObj.getAspectRatio();
            int sumX=0;
            for(int j=0; j<deficit_num; j++){
            	sumX++;
                allocateDeficitFunction(deficit_num, ar, name, id, rgb, asp_, rotate);
            }
            //System.out.println("deficit : "+id + name+","+sumX);
            STATUS+="deficit : "+id + name+","+sumX +"\n";
        }
    }
    
    
    
    public void allocatePeripheralFunction(int node_num, double node_ar, 
            String node_name, int node_id_, int[] rgb_, double[] asp_){
    	periPathList.clear();
        /*
        * LOGIC FOR OCCUPYING
        */
        if(periCellObjList.size()>0){
            int i=0;
            int counter=0;
            //System.out.println(node_name+","+node_num+","+node_ar);
            while(counter<(node_num) && i<periCellObjList.size()){
             	int idx=i;
                CellObj obj=periCellObjList.get(idx);
                if(obj.getNumUsed()==0 && obj.getFilled()==false){
                    double ar=node_ar;
                    GeneralPath path=obj.getPeripheralGeo(ar);
                    String id_str=getCellId(periCellObjList, path, true, 4);
                    double ar_fit=checkAr_cellsOcc(periCellObjList, ar, id_str);
                    boolean intX=checkIntXPath(periCellObjList, path);
                    if(ar_fit>fitness_peri && !id_str.isEmpty() && intX==true){
                        FuncObj funcobj=new FuncObj(path, ar,id_str, node_name, 
                                "peripheral", node_id_, rgb_, asp_);
                        funcObjList.add(funcobj);
                        periPathList.add(path);
                        updateCells(periCellObjList, path, node_name);
                        counter++;
                    }
                }
                i++;
            }
        }
        /*
        *   GET THE FITNESS FOR PERIPHERAL
        */
        double sumN_peri=0;
        double sumI_peri=0;
        for(int i=0; i<periCellObjList.size(); i++){
            CellObj c=periCellObjList.get(i);
            boolean t=c.getFilled();
            if(c.getZone().equals("peripheral")){
                if(t==true){
                    int n=c.getNumUsed();
                    sumI_peri+=n; //number of times this cell was used
                    sumN_peri++;
                }
            }
        }
        double f_overlap_peri=sumN_peri/(sumN_peri+Math.abs(sumN_peri-sumI_peri));
        fitness_peri=f_overlap_peri;
    }

    
    
    public boolean checkIntXPath(ArrayList<CellObj>tempCell,GeneralPath path0){
        Area ar0=new Area(path0);
        ArrayList<String>path0_pts=new ArrayList<String>();
        path0_pts.addAll(getPathPoints(path0));
        int sum=0;
        for(int i=0; i<tempCell.size(); i++){
            CellObj obj=tempCell.get(i);
            int num=obj.getNumUsed();
            if(obj.getFilled()==true){
                double x=obj.getMidPt()[0];
                double y=obj.getMidPt()[1];
                if(ar0.contains(x,y)==true){
                    sum++;
                }
            }
        }
        System.out.println("sum is "+sum);
        if(sum<1){
            return true;
        }else{
            return false;
        }
    }
    
    
    public boolean periPathIntX(ArrayList<GeneralPath>tempPath, GeneralPath path0){
        Area ar0=new Area(path0);
        int sum=0;
        for(int i=0; i<tempPath.size(); i++){
            GeneralPath path=tempPath.get(i);
            ArrayList<String>path_str=getPathPoints(path);
            for(int j=0; j<path_str.size(); j++){
                double x=Double.parseDouble(path_str.get(j).split(",")[0]);
                double y=Double.parseDouble(path_str.get(j).split(",")[1]);
                boolean t=ar0.contains(x,y);
                if(t==true){
                    sum++;
                }
            }
        }
        if(sum<1){
            return true;
        }else{
            return false;
        }
        
    }
    
    
    public void allocateInterstitialFunction(int node_num, double node_ar, 
            String node_name, int node_id_, int[] rgb_, double[] asp_){
        interPathList.clear();
        if(interCellObjList.size()>0){
            int i=0;
            int counter=0;
            
            while(counter<node_num && i<interCellObjList.size()){
            	int idx=i;
                CellObj obj=interCellObjList.get(idx);
                //System.out.println(node_name+","+node_num+","+node_ar);
                if(obj.getNumUsed()==0 && obj.getFilled()==false){
                    double ar=node_ar;
                    GeneralPath path=obj.getGeneralGeo(ar, asp_, false);
                    String id_str=getCellId(interCellObjList, path, false, 4);
                    double ar_fit=checkAr_cellsOcc(interCellObjList, ar, id_str);
                    boolean intX=checkIntXPath(interCellObjList, path);
                    if(ar_fit>fitness_inter && !id_str.isEmpty() && intX==true){
                        updateCells(interCellObjList, path, node_name);
                        FuncObj funcobj=new FuncObj(path, ar,id_str, node_name, "interstitial", node_id_, rgb_, asp_);
                        funcObjList.add(funcobj);
                        counter++;
                    }
                }
                i++;
            }
        }
        /*
        *   GET THE FITNESS FOR INTERSTITIAL
        */
        double sumN_inter=0;
        double sumI_inter=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            boolean t=c.getFilled();
            if(c.getZone().equals("interstitial")){
                if(t==true){
                    int n=c.getNumUsed();
                    sumI_inter+=n; //number of times this cell was used
                    sumN_inter++;
                }
            }
        }
        double f_overlap_inter=sumN_inter/(sumN_inter+Math.abs(sumN_inter-sumI_inter));
        fitness_inter=f_overlap_inter;
    }
    
    
    public void allocateGeneralFunction(int node_num, double node_ar, 
            String node_name, int node_id_, int[]rgb_, double[] asp_){
    	genPathList.clear();
        if(genCellObjList.size()>0){
            int i=0;
            int counter=0;
            //System.out.println(node_name+","+node_num+","+node_ar);
            while(counter<node_num && i<genCellObjList.size()){
            	int idx=i;
                CellObj obj=genCellObjList.get(idx);
                if(obj.getNumUsed()==0 && obj.getFilled()==false){
                	double ar=node_ar;
                	/*
                	 * based on the side of the cell we derive rect path for the area
                	 * getCellId() : refined placement : true; check for vertices & number of vertices to occupy
                	 */
                	GeneralPath ar_path=obj.getGeneralGeo(ar,asp_,false);
	                String id_str=getCellId(genCellObjList, ar_path, true, 4);
	                double ar_fit=checkAr_cellsOcc(genCellObjList, ar, id_str);
                        boolean intX=checkIntXPath(genCellObjList, ar_path);
	                if(ar_fit>fitness_gen && !id_str.isEmpty() && intX==true){
	                	updateCells(genCellObjList, ar_path, node_name);
	                	FuncObj funcobj=new FuncObj(ar_path, ar,id_str, node_name, "general", node_id_, rgb_, asp_);
 	                	funcObjList.add(funcobj);
	                	counter++;
	                }
                }
                i++;
            }
        }
        /*
        *   GET THE FITNESS FOR GENERAL
        */
        double sumN_gen=0;
        double sumI_gen=0;
        for(int i=0; i<genCellObjList.size(); i++){
            CellObj c=genCellObjList.get(i);
            boolean t=c.getFilled();
            if(c.getZone().equals("general")){
                if(t==true){
                    int n=c.getNumUsed();
                    sumI_gen+=n; //number of times this cell was used
                    sumN_gen++;
                }
            }
        }
        double f_overlap_gen=sumN_gen/(sumN_gen+Math.abs(sumN_gen-sumI_gen));
        fitness_gen=f_overlap_gen;
    }
   
    
    public void allocateDeficitFunction(int node_num, double node_ar, String 
            node_name, int node_id_, int[]rgb_, double[] asp_, boolean rotate){
        int i=0;
        int counter=0;
        /*
        *   use every possible cell
        */
        while(counter<node_num && i<cellObjList.size()){
            int idx=i;
            CellObj obj=cellObjList.get(idx);
            if(obj.getNumUsed()==0 && obj.getFilled()==false){
                double ar=node_ar;
                /*
                 * based on the side of the cell we derive rect path for the area
                 * getCellId() : refined placement : true; check for vertices & 
                 * number of vertices to occupy
                 *
                 */
                GeneralPath ar_path=obj.getGeneralGeo(ar,asp_,false);
                String id_str=getCellId(cellObjList, ar_path, true, 2);
                double ar_fit=checkAr_cellsOcc(cellObjList, ar, id_str);
                boolean intX=checkIntXPath(cellObjList, ar_path);
                if(ar_fit>fitness_defi && !id_str.isEmpty() && intX==true){
                    genPathList.add(ar_path);
                    updateCells(cellObjList, ar_path, node_name);
                    if(rotate==true){
                        //System.out.println("rotated in deficit function");
                        STATUS+="rotated in deficit function/n";
                    }
                    FuncObj funcobj=new FuncObj(ar_path, ar,id_str, node_name,
                            "general", node_id_, rgb_, asp_);
                    funcObjList.add(funcobj);
                    System.out.println("added : "+node_name);
                    STATUS+="added : "+node_name+"/n";
                    counter++;
                }
            }
            i++;
        }
        /*
        *   GET THE FITNESS
        */
        double sumN_gen=0;
        double sumI_gen=0;
        for(int j=0; j<cellObjList.size(); j++){
            CellObj c=cellObjList.get(j);
            boolean t=c.getFilled();
            if(c.getZone().equals("general")){
                if(t==true){
                    int n=c.getNumUsed();
                    sumI_gen+=n; //number of times this cell was used
                    sumN_gen++;
                }
            }
        }
        double f_overlap_gen=sumN_gen/(sumN_gen+Math.abs(sumN_gen-sumI_gen));
        fitness_cells_used_gen=f_overlap_gen;
    }
        
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d= (Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,1000,1000));
        g2d.scale(scaleX, scaleY);
        g2d.translate(translateX, translateY);
        /*
        *   PLOT ALL CELLS
        */
        if(showGrid==false){
            g2d.setColor(new Color(0,0,0));
            for(int i=0; i<cellObjList.size(); i++){
                CellObj c=cellObjList.get(i);
                GeneralPath path=c.genPath();
                String zone=c.getZone();
                g2d.setStroke(new BasicStroke(0.25F));
                g2d.setColor(new Color(0,0,0,50));
                g2d.draw(path);
            }

            for(int i=0; i<cellObjList.size(); i++){
                CellObj c= cellObjList.get(i);
                boolean t=c.getFilled();
                GeneralPath p=c.genPath();
                if(t==true){                
                    g2d.setColor(new Color(200,255,200,150));
                    g2d.fill(p);
                }
                int n=c.getNumUsed();
                if(n>1){
                    //g2d.setColor(new Color(255,0,0));
                    //g2d.fill(p);
                }
            }
        }
        /*
        *   PLOT THE FUNCTIONS ALLOCATED
        */
        for(int i=0; i<funcObjList.size(); i++){
            FuncObj obj=funcObjList.get(i);
            String name=obj.getName();
            //System.out.println(obj.getCellsOccupied());
            int[] rgb=obj.getRgb();
            int id=obj.getId();
            GeneralPath path=obj.getPath();
            g2d.setStroke(new BasicStroke(0.70F));            
            g2d.setColor(new Color(0,0,0));
            g2d.draw(path);
            g2d.setColor(new Color(rgb[0],rgb[1],rgb[2]));
            g2d.fill(path);
            /*
             * GET CELLS ID 
             * GET PATH
             * ADD AREA
             */
            String[] cellsId=obj.getCellsOccupied().split(",");
            int req_id0=Integer.parseInt(cellsId[0]);
            CellObj cell0=cellObjList.get(req_id0);
            GeneralPath path0=cell0.genPath();
            Area a0=new Area(path0);
            for(int j=0; j<cellsId.length; j++){
            	int req_id=Integer.parseInt(cellsId[j]);
            	CellObj cell=cellObjList.get(req_id);
            	GeneralPath path1=cell.genPath();
            	Area a1=new Area(path1);
            	a0.add(a1);
            }     
            /*
            *   INDEXING
            */
            int mpx=(int)(describePath(path)[0]);
            int mpy=(int)(describePath(path)[1]);
            g2d.setColor(new Color(255,255,255,150));
            double r=5;
            g2d.fill(new Rectangle2D.Double(mpx-r/2, mpy-r*2/3, r, r));
            g2d.setColor(new Color(0,0,0));
            Font font1=new Font("Consolas", Font.PLAIN, 3);
            g2d.setFont(font1);
            g2d.drawString(""+id,(int)(mpx-r/5),(int)mpy);
        }
        /*
        for(int i=0; i<periCellObjList.size(); i++){
            GeneralPath p=periCellObjList.get(i).genPath();
            g2d.setColor(new Color(255,0,0,100));
            g2d.fill(p);
        }
        */

        
        repaint();

    }
    
    
    public double[] describePath(GeneralPath path){
        PathIterator pi=path.getPathIterator(null);
        ArrayList<String> bb=new ArrayList<String>();
        bb.clear();
        String s="";
        while(pi.isDone()==false){
            double[] coor=new double[2];
            int type=pi.currentSegment(coor);
            switch(type){
                case PathIterator.SEG_MOVETO:
                    s=coor[0]+","+coor[1];
                    bb.add(s);
                    break;
                case PathIterator.SEG_LINETO:
                    s=coor[0]+","+coor[1];
                    bb.add(s);
                    break;
                default:
                    break;  
            }               
            pi.next();
        }
        Collections.sort(bb,new CompareIndex0());
        double minX=Double.parseDouble(bb.get(0).split(",")[0]);
        double maxX=Double.parseDouble(bb.get(bb.size()-1).split(",")[0]);
        Collections.sort(bb,new CompareIndex1());
        double minY=Double.parseDouble(bb.get(0).split(",")[1]);
        double maxY=Double.parseDouble(bb.get(bb.size()-1).split(",")[1]);
        double mpx=(minX+maxX)/2;
        double mpy=(minY+maxY)/2;
        double[] mp={mpx,mpy};
        return mp;
    }
    
    
    public ArrayList<String> getPathPoints(GeneralPath path){
    	PathIterator pi=path.getPathIterator(null);
        ArrayList<String> bb=new ArrayList<String>();
        bb.clear();
        String s="";
        while(pi.isDone()==false){
            double[] coor=new double[2];
            int type=pi.currentSegment(coor);
            switch(type){
                case PathIterator.SEG_MOVETO:
                    s=coor[0]+","+coor[1];
                    bb.add(s);
                    break;
                case PathIterator.SEG_LINETO:
                    s=coor[0]+","+coor[1];
                    bb.add(s);
                    break;
                default:
                    break;  
            }               
            pi.next();
        }
        return bb;
    }
    
    
    public double dis(double x0, double y0, double x1, double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) { 
        repaint();
    }
}
    

