package org.pw.GA.func;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.pw.dto.CellObj;
import org.pw.dto.FuncNodeObj;


public class GAFuncFrame extends JFrame implements ActionListener{
    
    JLabel jlblScale, jlblTranslate,jlblalgoname, jlblDept, jlblDeptFit, 
           jlblOverlapFit_peri, jlblOverlapFitnessSize_peri, jlblOverlap_peri, 
           jlblOverlapFit_inter, jlblOverlap_inter,jlblOverlapFitnessSize_inter,
           jlblOverlap_gen,jlblOverlapFit_gen,jlblOverlapFitnessSize_gen,
           jlblDeficit_gen, jlblDeficitFit_gen, jlblDeficitFitnessSize_gen,
           jlblFitnessFields, jlblFitnessReturned,jlblGrid;
    JTextField jtfScale, jtfTranslate, jtfentry; 
    JButton jbtnTransform, jbtnRunAlgo, jbtnEntry, jbtnDept;
    JSlider jsliderOverlapLim_peri, jsliderOverlapLim_inter,jsliderOverlapLim_gen,
            jsliderDeficit,jsliderDept;
    GAFuncPanel pnl;
    JCheckBox gridOn;
    
    Timer timer;
    ArrayList<FuncNodeObj>funcNodeObjList;
    public GAFuncFrame(ArrayList<FuncNodeObj>graphNodeList, 
            ArrayList<CellObj>GLOBAL_CellObjList){
    
        setLocation(430,0);
        setSize(1500,1000);
        setTitle("Demonstration of Genetic Algorithm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /*
         * GLOBAL VARIABLES
         */
        funcNodeObjList=new ArrayList<FuncNodeObj>();
        funcNodeObjList.clear();
        funcNodeObjList.addAll(graphNodeList);
        timer=new Timer(150,this);
        timer.start();
        
        /*
        *   TRANFORMATION
        */
        jlblScale=new JLabel("Scale");
        jlblScale.setBounds(1025,20,100,40);
        
        jtfScale=new JTextField("3,3"); 
        jtfScale.setBounds(1150,20,100,40);
        
        jlblTranslate= new JLabel("Translate");
        jlblTranslate.setBounds(1025,70,100,40);
        
        jtfTranslate=new JTextField("-20,0");
        jtfTranslate.setBounds(1150,70,100,40);
        
        jbtnTransform=new JButton("Transform");
        jbtnTransform.setBounds(1275,40,175,50);
        
        /*
        *   PLACE DEPARTMENTS
        */
        jlblDept=new JLabel("O R G A N I Z E      F U N C T I O N S");
        jlblDept.setBounds(1025,770,250,40);
        
        jlblDeptFit=new JLabel("20");
        jlblDeptFit.setBounds(1225,820,250,40);
        
        jsliderDept=new JSlider(50,100,75);
        jsliderDept.setBounds(1025,820,175,40);
        
        jbtnDept=new JButton("Organize");
        jbtnDept.setBounds(1275,800,175,50);
        
        /*
        *   RUN ALGORITHM
        */
        
        jlblalgoname=new JLabel("R U N   A L G O R I T H M");
        jlblalgoname.setBounds(1025,630,175,40);

        jbtnRunAlgo=new JButton("run Algorithm");
        jbtnRunAlgo.setBounds(1275,625,175,50);
        
        /*
        *   FITNESS LABELS
        */
        jlblFitnessFields=new JLabel("S E T   F I T N E S S    L I M I T S : ");
        jlblFitnessFields.setBounds(1025,150,180,40);
        
        jlblFitnessReturned=new JLabel(" R E T U R N E D");
        jlblFitnessReturned.setBounds(1300,150,175,40);
        
        /*
        *   PERIPHERAL ZONE
        */
        jlblOverlap_peri=new JLabel("1. Fitness of overlap in peripheral zone");
        jlblOverlap_peri.setBounds(1025,200,400,40);
        
        jlblOverlapFit_peri=new JLabel("<-- peripheral fitness -->");
        jlblOverlapFit_peri.setBounds(1275,245,250,40);
        
        jlblOverlapFitnessSize_peri=new JLabel("20");
        jlblOverlapFitnessSize_peri.setBounds(1225,245,50,40);
        
        jsliderOverlapLim_peri=new JSlider(50,100,75);
        jsliderOverlapLim_peri.setBounds(1025,245,175,40);
        
        /*
        *   INTERSTITIAL ZONE
        */       
        jlblOverlap_inter=new JLabel("2. Fitness of overlap in interstitial zone");
        jlblOverlap_inter.setBounds(1025,300,400,40);
        
        jlblOverlapFit_inter=new JLabel("<-- interstitial fitness -->");
        jlblOverlapFit_inter.setBounds(1275,345,250,40);
        
        jlblOverlapFitnessSize_inter=new JLabel("20");
        jlblOverlapFitnessSize_inter.setBounds(1225,345,50,40);
        
        jsliderOverlapLim_inter=new JSlider(50,100,75);
        jsliderOverlapLim_inter.setBounds(1025,345,175,40);
        
        /*
        *   GENERAL ZONE
        */
        jlblOverlap_gen=new JLabel("3. Fitness of overlap in general zone");
        jlblOverlap_gen.setBounds(1025,400,400,40);
        
        jlblOverlapFit_gen=new JLabel("<-- general fitness -->");
        jlblOverlapFit_gen.setBounds(1275,445,250,40);
        
        jlblOverlapFitnessSize_gen=new JLabel("20");
        jlblOverlapFitnessSize_gen.setBounds(1225,445,50,40);
        
        jsliderOverlapLim_gen=new JSlider(50,100,75);
        jsliderOverlapLim_gen.setBounds(1025,445,175,40);        
        
        /*
        *   DEFICIT ZONE
        */
        jlblDeficit_gen=new JLabel("4. Fitness of overlap in deficit zone");
        jlblDeficit_gen.setBounds(1025,500,400,40);
        
        jlblDeficitFit_gen=new JLabel("<-- deficit fitness -->");
        jlblDeficitFit_gen.setBounds(1275,545,250,40);
        
        jlblDeficitFitnessSize_gen=new JLabel("20");
        jlblDeficitFitnessSize_gen.setBounds(1225,545,50,40);
          
        jsliderDeficit=new JSlider(50,100,75);
        jsliderDeficit.setBounds(1025,545,175,40);  
        

        /*
        * Grid On
        */
        gridOn=new JCheckBox("  G R I D    S W I T C H");
        gridOn.setBounds(1175,700,500,40);
        gridOn.setSelected(true);
        jlblGrid=new JLabel(" Switch the grid on or off");
        jlblGrid.setBounds(1025,700,150,40);
        
        /*
        *   ADD THE PANEL
        */
        pnl=new GAFuncPanel(graphNodeList, GLOBAL_CellObjList);
        pnl.setBounds(0,0,1000,1000);
        
        setLayout(null);
        
        add(pnl);
        
        add(jlblScale);
        add(jlblTranslate);
        add(jtfScale);
        add(jtfTranslate);
        add(jbtnTransform);
        
        add(jlblDept);
        add(jsliderDept);
        add(jlblDeptFit);
        add(jbtnDept);
        
        add(jlblalgoname);
        add(jbtnRunAlgo);        
        
        add(jlblOverlapFit_peri);
        add(jsliderOverlapLim_peri);
        add(jlblOverlapFitnessSize_peri);
        add(jlblOverlap_peri);
        
        add(jsliderOverlapLim_inter);
        add(jlblOverlapFitnessSize_inter);
        add(jlblOverlapFit_inter);
        add(jlblOverlap_inter);
        
        add(jlblOverlap_gen);
        add(jlblOverlapFit_gen);
        add(jlblOverlapFitnessSize_gen);
        add(jsliderOverlapLim_gen);
        
        add(jlblFitnessFields);
        add(jlblFitnessReturned); 
        
        add(jlblDeficit_gen);
        add(jlblDeficitFit_gen);
        add(jlblDeficitFitnessSize_gen);
        add(jsliderDeficit);
        
        add(gridOn);
        add(jlblGrid);
        
        
        gridOn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               if(gridOn.isSelected()==true){
                   pnl.showGrid=false;
               }else{
                   pnl.showGrid=true;
               }
           }
        });
                
        jbtnTransform.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double scaleX=Double.parseDouble(jtfScale.getText().split(",")[0]);
                double scaleY=Double.parseDouble(jtfScale.getText().split(",")[1]);
                double translateX=Double.parseDouble(jtfTranslate.getText().split(",")[0]);
                double translateY=Double.parseDouble(jtfTranslate.getText().split(",")[1]);
                pnl.scaleX=scaleX;
                pnl.scaleY=scaleY;
                pnl.translateX=translateX;
                pnl.translateY=translateY;
                pnl.Update(scaleX,scaleY,translateX,translateY);
            }
        });
        
        
        jbtnRunAlgo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	constructCells();
            }
        });
    }
    
    
    public void constructCells(){
        pnl.fitness_peri=0;
        pnl.fitness_cells_used_inter=0;
        pnl.fitness_cells_used_gen=0;
        
        pnl.funcObjList.clear();
        
        pnl.periCellObjList.clear();
        pnl.periPathList.clear();
        
        pnl.interCellObjList.clear();
        pnl.interPathList.clear();
        
        pnl.genPathList.clear();
        pnl.genCellObjList.clear();
        
        pnl.getPeriCells();
        pnl.getInterCells();
        pnl.getGeneralCells();
        
        
        
        double sc=1.0;
        
        for(int i=0; i<funcNodeObjList.size(); i++){
            FuncNodeObj node_obj=funcNodeObjList.get(i);
            String node_loc=node_obj.getLocation().toLowerCase();

            int node_num=(int)node_obj.getQnty();
            double node_ar=node_obj.getAr_each()/sc;
            String node_name=node_obj.getName();
            int node_id=node_obj.getId();
            int[] node_rgb=node_obj.getRgb();
            double[] aspect=node_obj.getAspectRatio();
            if(node_loc.equals("peripheral")){
                    pnl.allocatePeripheralFunction(node_num, node_ar, 
                            node_name, node_id, node_rgb, aspect);
            }else if(node_loc.equals("interstitial")){
                    pnl.allocateInterstitialFunction(node_num, node_ar,node_name,
                            node_id,node_rgb,aspect);	
            }else{
                    pnl.allocateGeneralFunction(node_num, node_ar,node_name,
                            node_id,node_rgb,aspect);	
            }
        }
        
        pnl.plottedCells(false);
        //pnl.plottedCells(true);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        *   PERIPHERAL SLIDER VALUE
        */
        
        double peri_sliderVal=(double)jsliderOverlapLim_peri.getValue()/100;   
        String peri_sliderValStr=String.valueOf(peri_sliderVal);
        jlblOverlapFitnessSize_peri.setText(""+peri_sliderValStr);
        
        /*
        *   OVERLAP PERIPHERAL FITNESS
        */
        
        pnl.fitness_peri=Double.parseDouble(jlblOverlapFitnessSize_peri.getText());
        
        /*
        *   INTERSTITIAL SLIDER VALUE
        */
        
        double inter_sliderVal=(double)jsliderOverlapLim_inter.getValue()/100;   
        String inter_sliderValStr=String.valueOf(inter_sliderVal);
        jlblOverlapFitnessSize_inter.setText(""+inter_sliderValStr);
        
        /*
        *   OVERLAP INTERSTITIAL FITNESS
        */
        
        pnl.fitness_inter=Double.parseDouble(jlblOverlapFitnessSize_inter.getText());
        
        /*
        *   GENERAL SLIDER VALUE
        */
        double gen_sliderVal=(double)jsliderOverlapLim_gen.getValue()/100;   
        String gen_sliderValStr=String.valueOf(gen_sliderVal);
        jlblOverlapFitnessSize_gen.setText(""+gen_sliderValStr);
        /*
        *   OVERLAP GENERAL FITNESS
        */
        pnl.fitness_gen=Double.parseDouble(jlblOverlapFitnessSize_gen.getText());
        
        /*
        *   DEFICIT SLIDER VALUE
        */
        double defi_sliderVal=(double)jsliderDeficit.getValue()/100;   
        String defi_sliderValStr=String.valueOf(defi_sliderVal);
        jlblDeficitFitnessSize_gen.setText(""+defi_sliderValStr);
        /*
        *   OVERLAP GENERAL FITNESS
        */
        pnl.fitness_defi=defi_sliderVal;
        
        repaint();
    }
}
