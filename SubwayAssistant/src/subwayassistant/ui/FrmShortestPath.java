package subwayassistant.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import subwayassistant.model.Line;
import subwayassistant.model.Map;
import subwayassistant.model.Result;
import subwayassistant.util.GetData;

public class FrmShortestPath extends JDialog implements ActionListener{
	
	private List<Line> lines2 = new ArrayList<>();//存储.txt中的所有数据
	private Result result;
	private Map map;
	private List<String> list1 = new ArrayList<>(); //存储经过单个站点的地铁线的名字，以列表储存
	private List<List<String>> lists = new ArrayList<>(); //存储经过所有站点的地铁线的名字，将list1依次添加进lists中
	private List<Integer> passStation = new ArrayList<>(); //储存经过站点在数组中的下标

	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("查询");
	private JTextField edtStart = new JTextField(20);
	private JTextField edtEnd = new JTextField(20);
	private JLabel labelStart = new JLabel("起点:");
	private JLabel labelEnd = new JLabel("终点:");
	private JLabel labelTip = new JLabel("请输入您要查询的起点和终点^^");
	
	public FrmShortestPath(FrmMain frmMain, String s, boolean b, List<Line> lines, List<String> stations) {
		super(frmMain,s,b);
		lines2.addAll(lines);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelStart);
		workPane.add(edtStart);
		workPane.add(labelEnd);
		workPane.add(edtEnd);
		workPane.add(labelTip);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 150);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});

		//把所有站点加入stations中
		for(int i=0;i<GetData.linenum;i++) {
			stations.addAll(lines.get(i).getStations());
		}
		map = new Map(stations);//此map非彼map
		
		//初始化各个站点间的距离为1
		for(int i=0;i<lines.size();i++) {
			for(int j=0;j<lines.get(i).getStations().size()-1;j++) {
				map.initDis(lines.get(i).getStations().get(j), lines.get(i).getStations().get(j+1));
			}
		}
		
		//求最短路径
		result = new Result(map.getSubwayline());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnOk) {
			int flag1=-1;int flag2=-1;
			String start  = this.edtStart.getText();
			String end  = this.edtEnd.getText();
			flag1=FrmMain.isStation(start);
			flag2=FrmMain.isStation(end);
			if(start==null || start.equals("") || end==null || end.equals(""))
				try {
					throw new Exception();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "起点/终点不能为空！","错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			else if(flag1<0 || flag2<0){
				try {
						throw new Exception();
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "起点/终点不存在！","错误",JOptionPane.ERROR_MESSAGE);
						return;
					}
			}
			else if(start.equals(end)) {
				try {
					throw new Exception();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "起点和终点不能相同！","错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else{//查询shortest path
				int i = map.getIndex(start);
				int j = map.getIndex(end);
				int shortest = result.getMinDis(i,j);//需修改
				if(shortest == 999999) {
					try{
						throw new Exception();
						} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "两站点不可达！","错误",JOptionPane.ERROR_MESSAGE);
						return;
						}
				}
				shortest++;
				String path = start+"到"+end+"需经过"+shortest+"个站\n";
				
				passStation = result.indexToList(i,j);//存储最短路径
				
				for(int k=0;k<passStation.size();k++) {
					List<String> list = new ArrayList<>();
					path+=(map.getName(passStation.get(k))+"(");
//					System.out.println(path);
					for(Line l:lines2) {
						int flag=0;
						for(String name:l.getStations()) {
							System.out.println(map.getName(passStation.get(i)));
							if(map.getName(passStation.get(i)).equals(name)){
								path+=(l.getName()+" ");
								list.add(l.getName());
								if(!list1.contains(name)) {
									list1.add(name);
									flag=1;
								}
							}
						}
						if(flag==1) lists.add(list);
					}
				}
				path+=")";
				path+="\n";
				
				//存储换乘车站
				List<String> transfer = new ArrayList<>();
				
				for(int p=2;p<lists.size();p++) {
					int flag=0;
					for(int q=0;q<lists.get(p).size();q++) {
						for(int w=0;w<lists.get(p-2).size();w++)
							if(lists.get(p-2).get(w).equals(lists.get(p).get(q))) {
								flag=1;break;
							}
					}
					if(flag==0) {
						if(!transfer.contains(list1.get(p-1))); transfer.add(list1.get(p-1));
					}
				}
				
				path+="\n";
				
				path+="需要换乘"+transfer.size()+"次：";
				for(int a=0;a<transfer.size();a++) {
					path+=(transfer.get(a)+" ");
				}
				path+="\n";
				
				FrmShowShortestPath dlg=new FrmShowShortestPath(this,"最短路径详情",true,path);
				dlg.setVisible(true);
			}
			
		}
	}
}
