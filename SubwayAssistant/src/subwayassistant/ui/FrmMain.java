package subwayassistant.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import subwayassistant.model.Line;
import subwayassistant.model.Map;
import subwayassistant.model.Result;
import subwayassistant.util.GetData;

public class FrmMain extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar();
	private JPanel statusBar = new JPanel();
	private JMenu menu_Manager=new JMenu("功能");
	JFrame jf=new JFrame();
	private JMenuItem  menuItem_Line=new JMenuItem("查询线路");
	private JMenuItem  menuItem_ShortestPath=new JMenuItem("查询最短路径");
	 
	
	private static List<Line> lines = new ArrayList<>();//存储.txt中的所有数据
	private List<String> stations = new ArrayList<String>();//存储所有站点
	
	public FrmMain() throws IOException {
		
		//读文件
		String filepath = "E:\\Software Engineering\\data.txt";
		GetData data = new GetData(filepath,lines);
		
		//北京地铁图片
		JLabel jl3=new JLabel(new ImageIcon("E:\\Software Engineering\\subway.jpg"));
		jf.add(jl3);
		jl3.setBounds(0, 100, 80, 60);
		jf.pack();
		jf.setVisible(true);
		 
		this.setTitle("北京地铁小助手");
		menu_Manager.add(menuItem_Line);
		menuItem_Line.addActionListener(this);
	    menu_Manager.add(menuItem_ShortestPath);
	    menuItem_ShortestPath.addActionListener(this);
	    menubar.add(menu_Manager);
	    
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label=new JLabel("欢迎使用北京地铁小助手^^");
		statusBar.add(label);
		this.getContentPane().add(statusBar,BorderLayout.SOUTH);
		    
		this.addWindowListener(new WindowAdapter(){   
		    	public void windowClosing(WindowEvent e){ 
		    		System.exit(0);
	             }
	        });
		    this.setVisible(true);
			this.setJMenuBar(menubar);
			
	 }
	 
	 public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==this.menuItem_Line){
				FrmLine dlg=new FrmLine(this,"查询线路",true,lines);
				dlg.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_ShortestPath){
				FrmShortestPath dlg=new FrmShortestPath(this,"查询最短路径",true,lines,stations);
				dlg.setVisible(true);
			}
	 }
	public static int isStation(String s) {
		// TODO Auto-generated method stub
		for(int i=0;i<GetData.linenum;i++) {
			if(lines.get(i).getStations().contains(s)) {
				return 1;
			}
		}
		return -1;
	}
}
