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
	
	private List<Line> lines2 = new ArrayList<>();//�洢.txt�е���������
	private Result result;
	private Map map;
	private List<String> list1 = new ArrayList<>(); //�洢��������վ��ĵ����ߵ����֣����б���
	private List<List<String>> lists = new ArrayList<>(); //�洢��������վ��ĵ����ߵ����֣���list1������ӽ�lists��
	private List<Integer> passStation = new ArrayList<>(); //���澭��վ���������е��±�

	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("��ѯ");
	private JTextField edtStart = new JTextField(20);
	private JTextField edtEnd = new JTextField(20);
	private JLabel labelStart = new JLabel("���:");
	private JLabel labelEnd = new JLabel("�յ�:");
	private JLabel labelTip = new JLabel("��������Ҫ��ѯ�������յ�^^");
	
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

		//������վ�����stations��
		for(int i=0;i<GetData.linenum;i++) {
			stations.addAll(lines.get(i).getStations());
		}
		map = new Map(stations);//��map�Ǳ�map
		
		//��ʼ������վ���ľ���Ϊ1
		for(int i=0;i<lines.size();i++) {
			for(int j=0;j<lines.get(i).getStations().size()-1;j++) {
				map.initDis(lines.get(i).getStations().get(j), lines.get(i).getStations().get(j+1));
			}
		}
		
		//�����·��
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
					JOptionPane.showMessageDialog(null, "���/�յ㲻��Ϊ�գ�","����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			else if(flag1<0 || flag2<0){
				try {
						throw new Exception();
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "���/�յ㲻���ڣ�","����",JOptionPane.ERROR_MESSAGE);
						return;
					}
			}
			else if(start.equals(end)) {
				try {
					throw new Exception();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "�����յ㲻����ͬ��","����",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else{//��ѯshortest path
				int i = map.getIndex(start);
				int j = map.getIndex(end);
				int shortest = result.getMinDis(i,j);//���޸�
				if(shortest == 999999) {
					try{
						throw new Exception();
						} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "��վ�㲻�ɴ","����",JOptionPane.ERROR_MESSAGE);
						return;
						}
				}
				shortest++;
				String path = start+"��"+end+"�辭��"+shortest+"��վ\n";
				
				passStation = result.indexToList(i,j);//�洢���·��
				
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
				
				//�洢���˳�վ
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
				
				path+="��Ҫ����"+transfer.size()+"�Σ�";
				for(int a=0;a<transfer.size();a++) {
					path+=(transfer.get(a)+" ");
				}
				path+="\n";
				
				FrmShowShortestPath dlg=new FrmShowShortestPath(this,"���·������",true,path);
				dlg.setVisible(true);
			}
			
		}
	}
}
