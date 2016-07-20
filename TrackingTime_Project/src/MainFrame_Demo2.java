import java.awt.Dimension;
import java.awt.Toolkit;

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
    
  //  JLabel Plan = new JLabel("noi dung 0");  
   // JLabel Timelog = new JLabel("noi dung 1");  
  //  JLabel Addtag = new JLabel("noi dung 2");  
   // JLabel Statistics = new JLabel("noi dung 3");  
 //   JLabel Logout = new JLabel("noi dung 4");  
    Icon icon0 = new ImageIcon("Images/file-icon.png");  
    Icon icon1 = new ImageIcon("Images/time-icon.png");  
    Icon icon2 = new ImageIcon("Images/addition-icon.png");  
    Icon icon3 = new ImageIcon("Images/flow-market-icon.png");  
    Icon icon4 = new ImageIcon("Images/inside-logout-icon.png");  

    JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT); 
    Statistics stat = new Statistics();
    ManageTag Addtag = new ManageTag();
    TimeLog Timelog  = new TimeLog();
    public void init()  
    {          
    	 Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    	    int x = (int) ((dimension.getWidth() - tab.getWidth()) / 2);
    	    int y = (int) ((dimension.getHeight() - tab.getHeight()) / 2);
    	//Plan.setPreferredSize(new Dimension(600, 340));  
    	    tab.contains(x, y);
     //  Addtag.setLocation(x, y);
     //   Addtag.setPreferredSize(new Dimension(x,y));  
      //  Statistics.setPreferredSize(new Dimension(600, 340));  
       // Logout.setPreferredSize(new Dimension(600, 340));  
    	
       // tab.addTab("Plan", icon0, Plan, "Plan");     
        tab.addTab("TimeLog", icon1, Timelog.getContentPane(), "TimeLog");    
        tab.addTab("  Add Tag", icon2, Addtag.getContentPane(), "Add Tag");  
        tab.addTab("Statistics", icon3, stat.getContentPane(), "Statistics");  
    //    tab.addTab("    Logout", icon4, Logout, "bookDesc");  

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
        Dimension d = new Dimension(770, 650);
        f.setSize(d);

        f.setResizable(false);

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