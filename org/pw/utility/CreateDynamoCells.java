package org.pw.utility;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.pw.dto.CellObj;

public class CreateDynamoCells {
    ArrayList<CellObj> cellList;
    
    public CreateDynamoCells(ArrayList<String>genPtList, 
            ArrayList<String>interPtList, ArrayList<String>periPtList, 
            ArrayList<String>cornerPtList,ArrayList<String>interPeriPtList){
        
        /*
        *   general
        *   peripheral
        *   corner
        *   interstitial
        */
        
        
        /*
        *   GENERAL CELLS
        */
        cellList=new ArrayList<CellObj>();
        ArrayList<String>tempGen=new ArrayList<String>();
        tempGen.clear();
        for(int i=0; i<genPtList.size();i++){
            String[] ptArr=genPtList.get(i).split("-");
            for(int j=0; j<ptArr.length; j++){
                String[] pt=ptArr[j].split(";");
                if(pt.length>2){
                    if(ptArr[j]=="" || ptArr[j].isEmpty()){
                        //empty string
                    }else{
                        tempGen.add(ptArr[j]);
                    }
                }
            }
        }
        LinkedHashSet<String>lhs_gen=new LinkedHashSet<String>();
        lhs_gen.addAll(tempGen);
        tempGen.clear();
        tempGen.addAll(lhs_gen);
        lhs_gen.clear();
        for(int i=0; i<tempGen.size(); i++){
            //System.out.println(tempGen.get(i));
        }
        for(int i=0; i<tempGen.size(); i++){
            CellObj obj=new CellObj(i, tempGen.get(i).split(";"), "general");
            cellList.add(obj);
        }
        System.out.println("AFTER GENERAL CELLS : "+cellList.size());
        
        /*
        *   PERIPHERAL CELLS
        */
        ArrayList<String>tempPeri=new ArrayList<String>();
        tempPeri.clear();
        
        for(int i=0; i<periPtList.size();i++){
            String[] ptArr=periPtList.get(i).split("-");
            for(int j=0; j<ptArr.length; j++){
                String[] pt=ptArr[j].split(";");
                if(pt.length>2){
                    if(ptArr[j]=="" || ptArr[j].isEmpty()){
                        //empty string 
                    }else{
                        tempPeri.add(ptArr[j]);
                    }
                }
            }
        }
        LinkedHashSet<String>lhs_peri=new LinkedHashSet<String>();
        lhs_peri.addAll(tempPeri);
        tempPeri.clear();
        tempPeri.addAll(lhs_peri);
        lhs_peri.clear();
        for(int i=0; i<tempPeri.size(); i++){
            CellObj obj=new CellObj(i, tempPeri.get(i).split(";"),"peripheral");
            cellList.add(obj);
        }
        System.out.println("AFTER PERIPHERAL CELLS : "+","+tempPeri.size()+","+cellList.size());
        
        /*
        *   CORNER CELLS
        */
        ArrayList<String>tempCorner=new ArrayList<String>();
        tempCorner.clear();        
        for(int i=0; i<cornerPtList.size();i++){
            String[] ptArr=cornerPtList.get(i).split("-");
            for(int j=0; j<ptArr.length; j++){
                String[] pt=ptArr[j].split(";");
                if(pt.length>2){
                    if(ptArr[j]=="" || ptArr[j].isEmpty()){
                        //empty string
                    }else{
                        tempCorner.add(ptArr[j]);
                    }
                }
            }
        }
        LinkedHashSet<String> lhs_cor=new LinkedHashSet<String>();
        lhs_cor.clear();
        lhs_cor.addAll(tempCorner);
        tempCorner.clear();
        tempCorner.addAll(lhs_cor);
        lhs_cor.clear();
        for(int i=0; i<tempCorner.size(); i++){
            CellObj obj=new CellObj(i, tempCorner.get(i).split(";"),"corner");
            cellList.add(obj);
        }
        System.out.println("AFTER CORNER CELLS : "+","+tempCorner.size()+","+cellList.size());
        
        /*
        *   INTERSTITIAL PERIPHERAL CELLS
        */
        ArrayList<String>tempInter=new ArrayList<String>();
        tempInter.clear();        
        for(int i=0; i<interPeriPtList.size();i++){
            String[] ptArr=interPeriPtList.get(i).split("-");
            for(int j=0; j<ptArr.length; j++){
                String[] pt=ptArr[j].split(";");
                if(pt.length>2){
                    if(ptArr[j]=="" || ptArr[j].isEmpty()){
                        //empty string
                    }else{
                        tempInter.add(ptArr[j]);
                    }
                }
            }
        }
        LinkedHashSet<String> lhs_inter=new LinkedHashSet<String>();
        lhs_inter.clear();
        lhs_inter.addAll(tempInter);
        tempInter.clear();
        tempInter.addAll(lhs_inter);
        lhs_inter.clear();
        for(int i=0; i<tempInter.size(); i++){
            CellObj obj=new CellObj(i, tempInter.get(i).split(";"),"interstitial");
            cellList.add(obj);
        }
        System.out.println("AFTER INTERSTITIAL PERIPHERAL CELLS : "+","+tempInter.size()+
                ","+cellList.size());
    }
   
    public ArrayList<CellObj> getCellList(){
        return cellList;
    }
}
