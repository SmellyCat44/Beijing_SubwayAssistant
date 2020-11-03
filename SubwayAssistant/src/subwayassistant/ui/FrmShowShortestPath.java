package subwayassistant.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import subwayassistant.model.Line;

public class FrmShowShortestPath extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("好的");
	private JTextArea display = new JTextArea("");
	
	public FrmShowShortestPath(JDialog f, String s, boolean b, String path) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		display.append(path);//由于label无法换行 于是改用JTextArea
		workPane.add(display);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		
		this.setSize(400, 800);//尺寸need modify
		
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
}
