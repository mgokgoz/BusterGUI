/*
* Mustafa Gökgöz - 1661222
* Barış Can Kurtulmuş - 1661313	
* Kağan ŞENGEZER - 16
* Onur 
*
*
*
*/
package busterGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import java.beans.PropertyVetoException;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Main extends JFrame {
	private static final long serialVersionUID = -884607538541211001L;

	private JPanel contentPane;
	private JTextField path;
	private JTextField pPath;
	private JTextField gPath;
	private TestCreator tc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Main() throws PropertyVetoException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException{
		//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		setTitle("Buster GUI");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 675);
		contentPane = new JPanel();
		contentPane.setBorder(UIManager.getBorder("CheckBox.border"));
		setContentPane(contentPane);
		tc=new TestCreator();
		final JInternalFrame tools = new JInternalFrame("Tools");
		tools.setClosed(true);
		tools.setClosable(true);
		tools.setBounds(114, 154, 462, 149);
		contentPane.add(tools);
		tools.getContentPane().setLayout(null);
		
		final JLabel res = new JLabel("None");
		res.setForeground(Color.BLACK);
		res.setBounds(76, 86, 202, 15);
		tools.getContentPane().add(res);
		
		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parse(pPath.getText());
				res.setText("Parsing completed");
				res.setForeground(Color.GREEN);
			}
		});
		btnParse.setBounds(331, 12, 117, 25);
		tools.getContentPane().add(btnParse);
		
		pPath = new JTextField();
		pPath.setText("lib/example.js");
		pPath.setBounds(12, 12, 307, 25);
		tools.getContentPane().add(pPath);
		pPath.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					generate(gPath.getText());
					res.setText("Generating completed");
					res.setForeground(Color.GREEN);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnGenerate.setBounds(331, 49, 117, 25);
		tools.getContentPane().add(btnGenerate);
		
		gPath = new JTextField();
		gPath.setText("lib/example.JSON");
		gPath.setColumns(10);
		gPath.setBounds(12, 49, 307, 25);
		tools.getContentPane().add(gPath);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(12, 86, 70, 15);
		tools.getContentPane().add(lblResult);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 140, 760, 478);
		contentPane.add(scrollPane);
				
		final JTextArea result = new javax.swing.JTextArea();
		scrollPane.setViewportView(result);
		result.setEditable(false);
		result.setForeground(new Color(0, 153, 51));
		result.setBackground(Color.BLACK);
		result.setFont(new Font("Monospaced", Font.PLAIN, 14));
		result.setTabSize(10);
		final JButton btnTest = new JButton("Run Test");
		btnTest.setBounds(12, 51, 197, 36);
		btnTest.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnTest.setText("Performing...");
				try {
					performTest(result, new File(path.getText().toString()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        btnTest.setText("Run Test");
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnTest);
		
		final JButton startServer = new JButton("Start Server");
		startServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(startServer(result, new File(path.getText().toString()))){
				startServer.setEnabled(false);
				startServer.setText("Server started");
				}
				else{
				startServer.setEnabled(true);
				startServer.setText("Start Server");
				}
				
			}
		});
		startServer.setBounds(221, 51, 185, 36);
		contentPane.add(startServer);
		
		JButton cls = new JButton("Clear Screen");
		cls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearScreen(result);
			}
		});
		cls.setBounds(418, 51, 138, 36);
		contentPane.add(cls);
		
		JButton btnDetailedResults = new JButton("Detailed Results");
		btnDetailedResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File f=new File(path.getText().toString());
					performDetailed(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDetailedResults.setBounds(568, 51, 204, 36);
		contentPane.add(btnDetailedResults);
		
		path = new JTextField();
		path.setText(System.getProperty("user.dir"));
		path.setBounds(12, 12, 544, 35);
		contentPane.add(path);
		path.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(568, 12, 204, 35);
		contentPane.add(btnBrowse);

		
		JButton btnTools = new JButton("Tools");
		btnTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tc.setVisible(true);
				try {
					if(tools.isClosed()){
						tools.setClosed(false);
						tools.setVisible(true);
					}
					else{
						tools.setClosed(true);
						tools.setVisible(false);
					}
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnTools.setBounds(568, 92, 204, 36);
		contentPane.add(btnTools);
		
		final JButton btnNewButton = new JButton("Run Autotesting");
		btnNewButton.addActionListener(new ActionListener() {
			Thread th;
			boolean run=false;
			public void actionPerformed(ActionEvent e) {
				if(!run){
					btnNewButton.setText("Autotest running...");				
					try {
						th=performAutoTest(result, new File(path.getText().toString()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					btnTest.setEnabled(false);
					btnTest.setText("Autotest Active!");
					run=true;
				}
				else{
					btnNewButton.setText("Run Autotesting");
					th.stop();
					btnTest.setEnabled(true);
					btnTest.setText("Run Test");
					run=false;
				}
			}

		});
		btnNewButton.setBounds(12, 99, 197, 29);
		contentPane.add(btnNewButton);
		
	}

	protected void generate(String filename) throws FileNotFoundException {
		CodeGen c=new CodeGen(filename);
		String JS=c.GenerateJS();
		try{
			FileWriter writer = new FileWriter(filename.split("\\.")[0] +".js");
			BufferedWriter out =new BufferedWriter(writer);
			out.write(JS);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	protected void parse(String filename){
		Parser p=new Parser(filename);
		String JSON=p.Parse();
		try{
			FileWriter writer = new FileWriter(filename.split("\\.")[0] +".JSON");
			BufferedWriter out =new BufferedWriter(writer);
			out.write(JSON);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	protected boolean startServer(JTextArea result, File workingDir) {
		ProccessRunner p= new ProccessRunner("buster", "server", workingDir);
		Thread t=new Thread(p);
		t.start();
		result.setText(result.getText()+"Server started. Capture browsers first if test was browser test!\n");
		return true;		
	}
	protected void clearScreen(JTextArea result) {
		result.setText("");	
	}
	protected void performDetailed(File workingDir) throws IOException {
		ArrayList<String> files = getList();
		int k;
		for(k=0;k<files.size();k++){
			files.get(k);
			Isolator i=new Isolator(files.get(k),true);
			i.isolate();
			ResponseReporter r=new ResponseReporter(files.get(k));
			r.report();
			r.write();
		}
		ProccessRunner p= new ProccessRunner("buster" ,"static", workingDir);
		Thread t=new Thread(p);
		t.start();
		java.awt.Desktop desktop =java.awt.Desktop.getDesktop();
		java.net.URI uri = null;
		try {
			uri = new java.net.URI( "http://localhost:8282/" );
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		try {
			desktop.browse(uri);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	protected ArrayList<String> getList(){
		Analyzer a= new Analyzer();
		ArrayList<String> files = new ArrayList<String>();
		files=a.getFileList();
		Scanner s= new Scanner();
		ArrayList<String> list = new ArrayList<String>();
		try {
			list=s.scan(files);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	protected ArrayList<String> getAllList(){
		Analyzer a= new Analyzer();
		ArrayList<String> files = new ArrayList<String>();
		files=a.getFileList();
		return files;
	}
	protected void performTest(JTextArea result, File workingDir) throws IOException {
		ArrayList<String> all = getAllList();
		ArrayList<String> files = getList();
		ArrayList<ResponseObject> responses=new ArrayList<ResponseObject>();
		result.setText(result.getText()+"\n"+"Files to be tested are: " +all);
		result.setText(result.getText()+"\n"+"Files found making XMLHttpRequest calls are: " +files);
		int k;
		for(k=0;k<files.size();k++){
			files.get(k);
			result.setText(result.getText()+"\n"+"Parsing test cases of " +files.get(k) +" ...");
			result.setText(result.getText()+"\n"+"Generating new test cases for " +files.get(k) +" ...\n");
			Isolator i=new Isolator(files.get(k),true);
			responses=i.isolate();
			ResponseReporter r=new ResponseReporter(files.get(k));
			r.report();
			r.write();
			Hashtable<String, String> bt=r.branchTypes();
			Hashtable<String, String> vt=r.getValues();
		    String[] lines= bt.keySet().toArray(new String[0]);
			int a;
			String s;
			for(a=0;a<lines.length;a++){
				s=lines[a];
				String var=bt.get(s);
				String val=vt.get(s);
				System.out.println(s+"\t"+var+"\t"+val);
			}
			result.append(printResponses(responses));
			result.append("Finished generating for "+ files.get(k) +"\n");
		}
		ProccessRunner p= new ProccessRunner("buster", "test",workingDir,result);
		Thread t=new Thread(p);
		t.start();
	}
	private String printResponses(ArrayList<ResponseObject> responses) {
		String s="User responses got:\n";
		for(ResponseObject ro : responses){
			s=s+ro.funcName+"\t"+ro.type+"\t"+ro.response+"\n";
		}
		return s;
	}
	protected Thread performAutoTest(JTextArea result, File workingDir) throws IOException {
		ArrayList<String> all = getAllList();
		ArrayList<String> files = getList();
		result.setText(result.getText()+"\n"+"Files to be tested are: " +all);
		result.setText(result.getText()+"\n"+"Files found making XMLHttpRequest calls are: " +files);
		int k;
		for(k=0;k<files.size();k++){
			files.get(k);
			result.setText(result.getText()+"\n"+"Parsing test cases of " +files.get(k) +" ...");
			result.setText(result.getText()+"\n"+"Generating new test cases for " +files.get(k) +" ...\n");
			Isolator i=new Isolator(files.get(k),true);
			i.isolate();
			ResponseReporter r=new ResponseReporter(files.get(k));
			r.report();
			r.write();
			result.append("Finished generating for "+ files.get(k) +"\n");
		}
		ProccessRunner p= new ProccessRunner("buster", "autotest",workingDir,result);
		Thread t=new Thread(p);
		t.start();
		return t;
	}
}
