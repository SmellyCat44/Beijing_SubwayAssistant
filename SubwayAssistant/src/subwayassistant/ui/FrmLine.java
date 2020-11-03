package subwayassistant.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import subwayassistant.model.Line;


public class FrmLine extends JDialog implements ActionListener{
	
	private List<Line> lines1 = new ArrayList<>();//存储.txt中的所有数据
	public static final String linename = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("查询");
	private JTextField edtLine = new JTextField(20);
	private JLabel labelTip = new JLabel("请输入您要查询的线路^^ <格式如：1号线>");
	
	public FrmLine(FrmMain frmMain, String s, boolean b, List<Line> lines) {
		super(frmMain,s,b);
		lines1.addAll(lines);
//		System.out.println(lines1.get(0).getStations());//ok
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(edtLine);
		workPane.add(labelTip);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 120);
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnOk) {
				String linename = this.edtLine.getText();
				if(!(linename.equals("1号线")||linename.equals("2号线")||linename.equals("4号线大兴线")||linename.equals("5号线")||linename.equals("6号线")||linename.equals("7号线")||linename.equals("8号线")||linename.equals("9号线")||linename.equals("10号线")||linename.equals("13号线")||linename.equals("8号线南段")||linename.equals("14号线东段")||linename.equals("14号线西段")||linename.equals("15号线")||linename.equals("16号线")||linename.equals("八通线")||linename.equals("昌平线")||linename.equals("房山线")||linename.equals("首都机场线")||linename.equals("西郊线")||linename.equals("燕房线")||linename.equals("亦庄线"))) {
					try {
						throw new Exception();
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "输入线路不存在或格式错误！","错误",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else {
					FrmShowStations dlg=new FrmShowStations(this,"线路详情",true,lines1,linename);
					dlg.setVisible(true);
				}
			
		}
	}
}
