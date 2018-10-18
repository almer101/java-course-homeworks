package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 * The program run without any arguments which
 * shows two lists and one button "next" which generates
 * the next prime number which is visible in both lists.
 * 
 * @author ivan
 *
 */
public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor which initializes the whole frame.
	 * 
	 * @param model
	 * 		the model of the list.
	 */
	public PrimDemo(PrimListModel model) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(400, 400);
		setSize(300, 200);
		
		initGUI(model);
	}

	/**
	 * This method initializes the GUI.
	 * 
	 * @param model	
	 * 		the model which communicates with the 
	 * 		two lists.
	 */
	private void initGUI(PrimListModel model) {
		setLayout(new BorderLayout());
		
		JPanel listsPanel = new JPanel();
		listsPanel.setLayout(new GridLayout(1, 2));
		
		JScrollPane scroll1 = new JScrollPane(new JList<Integer>(model));
		scroll1.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scroll2 = new JScrollPane(new JList<Integer>(model));
		scroll2.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		listsPanel.add(scroll1);
		listsPanel.add(scroll2);
		
		JButton next = new JButton("Next");
		next.addActionListener(e -> {
			model.next();
		});
		
		getContentPane().add(listsPanel, BorderLayout.CENTER);
		getContentPane().add(next, BorderLayout.PAGE_END);
	}

	/**
	 * This is the method called when the program is run
	 * 
	 * @param args
	 * 		the command line arguments.
	 */
	public static void main(String[] args) {
		PrimListModel model = new PrimListModel();	
		SwingUtilities.invokeLater(() -> {
			PrimDemo demo = new PrimDemo(model);
			demo.setVisible(true);
		});
	}
}
