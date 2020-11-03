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
import javax.swing.JLabel;
import javax.swing.JPanel;

import subwayassistant.model.Line;
import subwayassistant.util.GetData;

public class FrmShowStations extends JDialog implements ActionListener {
	private List<Line> lines2 = new ArrayList<>();//存储.txt中的所有数据
	private static final long serialVersionUID = 1L;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("好的");
	
	public FrmShowStations(JDialog f, String s, boolean b, List<Line> lines1, String linename) {
		super(f, s, b);
		lines2.addAll(lines1);
		String ans = viewStations(linename,lines2)+"";
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		JLabel labelOid = new JLabel(ans);//打印指定线路所有stations
		workPane.add(labelOid);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(1400, 120);
		// 屏幕居中显示
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
		if (e.getSource() == this.btnOk) {
			this.setVisible(false);
			return;
		}
	}
	
	public List<String> viewStations(String linename, List<Line> lines) {
		for(int i=0;i<lines.size();i++) {
			if(linename.equals(lines.get(i).getName())) {
				return lines.get(i).getStations();
			}
		}
		return null;
	}
}