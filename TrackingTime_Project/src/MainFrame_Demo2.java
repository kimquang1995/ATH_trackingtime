import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame_Demo2 {  
  
    JFrame f = new JFrame("Tracking Time"); 
    JLabel Plan = new JLabel("noi dung 0");  
    JLabel Timelog = new JLabel("noi dung 1");  
    JLabel Addtag = new JLabel("noi dung 2");  
    JLabel Statistics = new JLabel("noi dung 3");  
    JLabel Logout = new JLabel("noi dung 4");  
    Icon icon0 = new ImageIcon("Images/file-icon.png");  
    Icon icon1 = new ImageIcon("Images/time-icon.png");  
    Icon icon2 = new ImageIcon("Images/addition-icon.png");  
    Icon icon3 = new ImageIcon("Images/flow-market-icon.png");  
    Icon icon4 = new ImageIcon("Images/inside-logout-icon.png");  

    JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT); 
      
    public void init()  
    {          
    	Plan.setPreferredSize(new Dimension(600, 340));  
        Timelog.setPreferredSize(new Dimension(600, 340));  
        Addtag.setPreferredSize(new Dimension(600, 340));  
        Statistics.setPreferredSize(new Dimension(600, 340));  
        Logout.setPreferredSize(new Dimension(600, 340));  

        tab.addTab("Plan", icon0, Plan, "Plan");     
        tab.addTab("TimeLog", icon1, Timelog, "TimeLog");    
        tab.addTab("  Add Tag", icon2, Addtag, "Add Tag");  
        tab.addTab("Statistics", icon3, Statistics, "Statistics");  
        tab.addTab("    Logout", icon4, Logout, "bookDesc");  

        tab.addChangeListener(new ChangeListener() {          //Tab change
            @Override  
            public void stateChanged(ChangeEvent e) {  
                // TODO Auto-generated method stub  
                System.out.println(tab.getSelectedIndex());  
            }  
        });  
        f.add(tab);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.pack();  
        f.setVisible(true);  
    }   
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
    	 try {
             // Set System L&F
         UIManager.setLookAndFeel(
             UIManager.getSystemLookAndFeelClassName());
      //   UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
         //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

     } 
     catch (UnsupportedLookAndFeelException e) {
        // handle exception
     }
     catch (ClassNotFoundException e) {
        // handle exception
     }
     catch (InstantiationException e) {
        // handle exception
     }
     catch (IllegalAccessException e) {
        // handle exception
     }
        new MainFrame_Demo2().init();        
    }     
}