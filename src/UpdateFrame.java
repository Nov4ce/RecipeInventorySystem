import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class UpdateFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateFrame frame = new UpdateFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UpdateFrame() {
		setTitle("Recipe Inventory System\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(216, 234, 156));
		panel.setBounds(0, 0, 587, 347);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel titleName = new JLabel("Recipe Update");
		titleName.setForeground(new Color(240, 240, 240));
		titleName.setBounds(10, 10, 294, 28);
		titleName.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(titleName);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 587, 72);
		panel_1.setBackground(new Color(118, 146, 36));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBorder(null);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveButton.setBounds(419, 22, 135, 29);
		panel_1.add(saveButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 102, 387, 193);
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(408, 102, 169, 139);
		panel.add(panel_3);
		
		JButton addPictureButton = new JButton("Add Picture");
		addPictureButton.setBounds(430, 261, 135, 34);
		panel.add(addPictureButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(277, 174, 503, 255);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setIcon(new ImageIcon(UpdateFrame.class.getResource("/images/foods (1).png")));
	}
}
