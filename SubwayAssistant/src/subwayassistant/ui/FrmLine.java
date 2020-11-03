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
	
	private List<Line> lines1 = new ArrayList<>();//�洢.txt�е���������
	public static final String linename = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("��ѯ");
	private JTextField edtLine = new JTextField(20);
	private JLabel labelTip = new JLabel("��������Ҫ��ѯ����·^^ <��ʽ�磺1����>");
	
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
				if(!(linename.equals("1����")||linename.equals("2����")||linename.equals("4���ߴ�����")||linename.equals("5����")||linename.equals("6����")||linename.equals("7����")||linename.equals("8����")||linename.equals("9����")||linename.equals("10����")||linename.equals("13����")||linename.equals("8�����϶�")||linename.equals("14���߶���")||linename.equals("14��������")||linename.equals("15����")||linename.equals("16����")||linename.equals("��ͨ��")||linename.equals("��ƽ��")||linename.equals("��ɽ��")||linename.equals("�׶�������")||linename.equals("������")||linename.equals("�෿��")||linename.equals("��ׯ��"))) {
					try {
						throw new Exception();
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "������·�����ڻ��ʽ����","����",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else {
					FrmShowStations dlg=new FrmShowStations(this,"��·����",true,lines1,linename);
					dlg.setVisible(true);
				}
			
		}
	}
}
