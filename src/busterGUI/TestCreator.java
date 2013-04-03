package busterGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestCreator extends JFrame {

	private JPanel contentPane;
	private JTextField params;
	private JTextField casename;
	private JTextField exp;
	private JComboBox type;
	private JComboBox funcs;
	private JComboBox source;
	private Hashtable<String, ArrayList<String>> table;
	private String total;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCreator frame = new TestCreator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	
	public TestCreator() throws IOException {
		total=new String();
		GenerateTestCase gtc=new GenerateTestCase();
		gtc.checktestCase();
		table=gtc.getFuncJStable();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 544, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		funcs = new JComboBox();
		funcs.setBounds(12, 102, 154, 24);
		contentPane.add(funcs);
		
		source = new JComboBox();
		source.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				funcs.removeAllItems();
				String s=source.getSelectedItem().toString();
				ArrayList<String> list=table.get(s);
				for(String f : list)
					funcs.addItem(f);
			}
		});
		for(int i=0;i<table.size();i++)
			source.addItem(table.keySet().toArray()[i]);
		source.setBounds(12, 39, 154, 24);
		contentPane.add(source);
		
		JLabel lblSource = new JLabel("Source:");
		lblSource.setBounds(12, 12, 70, 15);
		contentPane.add(lblSource);
		
		JLabel lblFunctions = new JLabel("Functions:");
		lblFunctions.setBounds(12, 75, 154, 15);
		contentPane.add(lblFunctions);
		

		
		params = new JTextField();
		params.setText("first, second");
		params.setBounds(200, 98, 288, 34);
		contentPane.add(params);
		params.setColumns(10);
		
		JLabel lblParameters = new JLabel("Parameters:");
		lblParameters.setBounds(200, 72, 139, 20);
		contentPane.add(lblParameters);
		
		JButton btnAdd = new JButton("Add Test");
		btnAdd.setBounds(100, 295, 288, 25);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTestCase();
			}			
		});
		contentPane.add(btnAdd);
		
		casename = new JTextField();
		casename.setText("This is a test case");
		casename.setBounds(12, 166, 154, 19);
		contentPane.add(casename);
		casename.setColumns(10);
		
		JLabel lblTestCaseName = new JLabel("Test Case Name:");
		lblTestCaseName.setBounds(12, 138, 154, 15);
		contentPane.add(lblTestCaseName);
		
		JLabel lblExplanation = new JLabel("Explanation:");
		lblExplanation.setBounds(12, 197, 154, 15);
		contentPane.add(lblExplanation);
		
		exp = new JTextField();
		exp.setText("exp");
		exp.setBounds(12, 224, 476, 34);
		contentPane.add(exp);
		exp.setColumns(10);
		
		type = new JComboBox();
		type.setModel(new DefaultComboBoxModel(new String[] {"Equality", "Truthy", "Falsy", "Same", "Great", "Less"}));
		type.setBounds(200, 163, 288, 24);
		contentPane.add(type);
		
		JLabel lblAssertion = new JLabel("Assertion:");
		lblAssertion.setBounds(200, 144, 154, 15);
		contentPane.add(lblAssertion);
		
		JButton Save = new JButton("Save test file of selected Source");
		Save.setBounds(100, 332, 288, 25);
		contentPane.add(Save);
	}
	private void addTestCase(){
		total=total+"buster.testCase(\""+casename.getText()+"\"," +
				"{\n\t\""+exp.getText()+"\": function() {\n"
				+"\t\t"+getAssert()+"("+funcs.getSelectedItem().toString()+"("+params.getText()+"),THIS);\n\t}\n});\n"; // TODO textbox should be added to get value to be tested against, for now it is hard-coded
		System.out.print(total);
		System.out.print(total);
		
	}

	private String getAssert() {
		// TODO Auto-generated method stub
		return "assert.equals";
	}
}
