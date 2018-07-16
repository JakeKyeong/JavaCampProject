
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

//--------------------------------------------------------생성 메뉴 메인 접속
public class Create extends SetBackground 
{
	JLabel label; 
	JTextField CreateWord1,CreateWord2;
	JTextArea CreateWord3;
	String fileName;
	static File file=null;
	JButton OpenFile, GoBtn;
	JButton createMA, createMB, createMC, createMD;

	public Create(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		createMA = new JButton(new ImageIcon("image/CM_An.png"));
		createMA.setBounds(130, 135, 129, 157);
		createMA.setPressedIcon(new ImageIcon("image/CM_Ay.png"));
		createMA.setRolloverIcon(new ImageIcon("image/CM_Ay.png"));
		createMA.setBorderPainted(false);
		createMA.setFocusPainted(false);
		createMA.setContentAreaFilled(false);
		createMA.addMouseListener(new ButtonListener());
		this.add(createMA);

		createMB = new JButton(new ImageIcon("image/CM_Bn.png"));
		createMB.setBounds(325, 135, 129, 157);
		createMB.setPressedIcon(new ImageIcon("image/CM_By.png"));
		createMB.setRolloverIcon(new ImageIcon("image/CM_By.png"));
		createMB.setBorderPainted(false);
		createMB.setFocusPainted(false);
		createMB.setContentAreaFilled(false);
		createMB.addMouseListener(new ButtonListener());
		this.add(createMB);

		createMC = new JButton(new ImageIcon("image/CM_Cn.png"));
		createMC.setBounds(520, 135, 129, 157);
		createMC.setPressedIcon(new ImageIcon("image/CM_Cy.png"));
		createMC.setRolloverIcon(new ImageIcon("image/CM_Cy.png"));
		createMC.setBorderPainted(false);
		createMC.setFocusPainted(false);
		createMC.setContentAreaFilled(false);
		createMC.addMouseListener(new ButtonListener());
		this.add(createMC);

		
		OpenFile = new JButton(new ImageIcon("image\\OpenFile.png"));
		OpenFile.setBounds(185, 295, 165, 50);
		OpenFile.setBorderPainted(false);
		OpenFile.setFocusPainted(false);
		OpenFile.setContentAreaFilled(false);
		OpenFile.addActionListener(new OpenFileListener());
		this.add(OpenFile);

		label = new JLabel();
		label.setBounds(360, 310,300, 30);
		this.add(label);

		GoBtn = new JButton(new ImageIcon("image\\GoButton.png"));
		GoBtn.setBounds(670, 295, 55, 50);
		GoBtn.setBorderPainted(false);
		GoBtn.setFocusPainted(false);
		GoBtn.setContentAreaFilled(false);
		this.add(GoBtn);
		GoBtn.addMouseListener(new ButtonListener());

		new MenuButton(this,new PanelMain(new ImageIcon("image/back.png")));
	}

	class OpenFileListener implements ActionListener{
		JFileChooser chooser;

		public void actionPerformed(ActionEvent e) {
			chooser = new JFileChooser();
			int ret = chooser.showOpenDialog(null);
			if(ret == JFileChooser.APPROVE_OPTION){
				System.out.println("파일 선택");
				file=chooser.getSelectedFile();
				fileName = chooser.getName(chooser.getSelectedFile());
				label.setText(fileName);
			}
		}
	}

	private class ButtonListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			if(btn==createMA)
			{
				CreateA a=new CreateA(new ImageIcon("image/backCA.png"));
				TestMate.stMain.setContentPane(a);
			}else if(btn==createMB)
			{
				CreateB b=new CreateB(new ImageIcon("image/backCB.png"));
				TestMate.stMain.setContentPane(b);
			}else if(btn==createMC)
			{
				CreateC c=new CreateC(new ImageIcon("image/backCC.png"));
				TestMate.stMain.setContentPane(c);
			}else if(btn==GoBtn)
			{
				//불러온 텍스트 파일명 (앞자리)분류에 따라서 해당 유형에 맞는 생성 페이지로 이동시켜야 함
				//불러온 텍스트 파일명은 생성페이지 (암기장 이름)란의 JField의 고정값으로 넘긴다.
				if(file!=null)
				{
					switch(fileName.substring(0, 4))
					{
					case "Voca":
						CreateA a=new CreateA(new ImageIcon("image/backCA.png"));
						a.CreateWord1.setText(fileName);
						TestMate.stMain.setContentPane(a);
						break;
					case "Defi":
						CreateB b=new CreateB(new ImageIcon("image/backCB.png"));
						b.CreateWord1.setText(fileName);
						TestMate.stMain.setContentPane(b);
						break;
					case "Sent":
						CreateC c=new CreateC(new ImageIcon("image/backCC.png"));
						c.CreateWord1.setText(fileName);
						TestMate.stMain.setContentPane(c);
						break;
					}
				}

			}
		}

		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }

	}
}

//--------------------------------------------------------생성-A.어휘 유형
class CreateA extends SetBackground
{
	JTextField CreateWord1,CreateWord2,CreateWord3_1,CreateWord3_2,CreateWord3_3,
	CreateWord4_1,CreateWord4_2,CreateWord4_3,CreateWord5_1,CreateWord5_2,CreateWord5_3;
	String wordbook;
	JButton AddButton;

	public CreateA(ImageIcon ico){
		super(ico);
		wordbook="txt파일 이름";
		//메인패널에 만든패널 연결        
		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JTextField(wordbook,150);
		CreateWord1.setBounds(220,95,500,30);

		CreateWord2 = new JTextField(150);
		CreateWord2.setBounds(180,147,540,30);

		CreateWord3_1 = new JTextField();
		CreateWord3_1.setBounds(180,195,160,30);
		CreateWord3_2 = new JTextField();
		CreateWord3_2.setBounds(360,195,160,30);
		CreateWord3_3 = new JTextField();
		CreateWord3_3.setBounds(540,195,160,30);

		CreateWord4_1 = new JTextField();
		CreateWord4_1.setBounds(180,240,160,30);
		CreateWord4_2 = new JTextField();
		CreateWord4_2.setBounds(360,240,160,30);
		CreateWord4_3 = new JTextField();
		CreateWord4_3.setBounds(540,240,160,30);

		CreateWord5_1 = new JTextField();
		CreateWord5_1.setBounds(180,288,160,30);
		CreateWord5_2 = new JTextField();
		CreateWord5_2.setBounds(360,288,160,30);
		CreateWord5_3 = new JTextField();
		CreateWord5_3.setBounds(540,288,160,30);


		this.add(CreateWord1);
		this.add(CreateWord2);
		this.add(CreateWord3_1);
		this.add(CreateWord3_2);
		this.add(CreateWord3_3);
		this.add(CreateWord4_1);
		this.add(CreateWord4_2);
		this.add(CreateWord4_3);
		this.add(CreateWord5_1);
		this.add(CreateWord5_2);
		this.add(CreateWord5_3);


		AddButton = new JButton(new ImageIcon("image/AddButton.png")); 
		AddButton.setBorderPainted(false); // 버튼 경계선 제거
		AddButton.setFocusPainted(false); //포커스(선택했던 버튼 표시) 제거
		AddButton.setContentAreaFilled(false);//버튼영역 배경 제거
		this.add(AddButton);
		AddButton.setBounds(310,340,180,40);
		AddButton.addMouseListener(new Append());


		new MenuButton(this,new Create(new ImageIcon("image/backCM.png")));
	}

	void textAppend() throws IOException
	{
		FileWriter fw = null;
		if(Create.file==null)
		{

			String str="Voca_"+CreateWord1.getText()+".txt";
			try {
				File file1=new File(str);
				fw =new FileWriter(file1,true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			fw.write(CreateWord2.getText());
			fw.write("/");

			String[] ans=new String[9];
			ans[0]=CreateWord3_1.getText();
			ans[1]=CreateWord3_2.getText();
			ans[2]=CreateWord3_3.getText();
			ans[3]=CreateWord4_1.getText();
			ans[4]=CreateWord4_2.getText();
			ans[5]=CreateWord4_3.getText();
			ans[6]=CreateWord5_1.getText();
			ans[7]=CreateWord5_2.getText();
			ans[8]=CreateWord5_3.getText();
			System.out.println("// "+CreateWord3_3.getText()+"  \\");
			int k=0;
			for(int i=0;i<ans.length;i++)
			{
				fw.write(ans[i]);
				fw.write(", ");
				k++;
				if(k%3==0&&k!=9)
					fw.write("/");

			}
			fw.write(CreateC.LINE_SEPARATOR);
			fw.flush();
			fw.close();

			CreateWord2.setText("");
			CreateWord3_1.setText("");
			CreateWord3_2.setText("");
			CreateWord3_3.setText("");
			CreateWord4_1.setText("");
			CreateWord4_2.setText("");
			CreateWord4_3.setText("");
			CreateWord5_1.setText("");
			CreateWord5_2.setText("");
			CreateWord5_3.setText("");
		}
		else
		{
			try {
				fw =new FileWriter(Create.file,true);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			fw.write(CreateWord2.getText());
			fw.write("/");

			String[] ans=new String[9];
			ans[0]=CreateWord3_1.getText();
			ans[1]=CreateWord3_2.getText();
			ans[2]=CreateWord3_3.getText();
			ans[3]=CreateWord4_1.getText();
			ans[4]=CreateWord4_2.getText();
			ans[5]=CreateWord4_3.getText();
			ans[6]=CreateWord5_1.getText();
			ans[7]=CreateWord5_2.getText();
			ans[8]=CreateWord5_3.getText();
			System.out.println("// "+CreateWord3_3.getText()+"  \\");
			int k=0;
			for(int i=0;i<ans.length;i++)
			{            
				fw.write(ans[i]);
				fw.write(", ");
				k++;

				if(k%3==0&&k!=9)
					fw.write("/");

			}
			fw.write(CreateC.LINE_SEPARATOR);
			fw.flush();
			fw.close();

			CreateWord2.setText("");
			CreateWord3_1.setText("");
			CreateWord3_2.setText("");
			CreateWord3_3.setText("");
			CreateWord4_1.setText("");
			CreateWord4_2.setText("");
			CreateWord4_3.setText("");
			CreateWord5_1.setText("");
			CreateWord5_2.setText("");
			CreateWord5_3.setText("");
		}
	}

	class Append implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {
			try {
				textAppend();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
}

//--------------------------------------------------------생성-B.정의 유형
class CreateB extends SetBackground
{
	JTextField CreateWord1,CreateWord2;
	JTextArea CreateWord3;
	String wordbook;
	JButton AddButton;

	public CreateB(ImageIcon ico){
		super(ico);
		wordbook="txt파일 이름";

		//메인패널에 만든패널 연결        
		setLayout(null);
		setBounds(0, 0, 800, 500);


		this.setSize(820,540);
		this.setVisible(true);



		CreateWord1 = new JTextField(wordbook,150);
		CreateWord1.setBounds(210,95,500,30);

		CreateWord2 = new JTextField(150);
		CreateWord2.setBounds(170,158,540,30);

		CreateWord3 = new JTextArea(540,250);


		this.add(CreateWord1);
		this.add(CreateWord2);
		JScrollPane scr=new JScrollPane(CreateWord3);
		scr.setSize(540, 100);
		scr.setBounds(170,210,540,100);
		this.add(scr);

		AddButton = new JButton(new ImageIcon("image/AddButton.png")); 
		AddButton.setBorderPainted(false); // 버튼 경계선 제거
		AddButton.setFocusPainted(false); //포커스(선택했던 버튼 표시) 제거
		AddButton.setContentAreaFilled(false);//버튼영역 배경 제거
		this.add(AddButton);
		AddButton.setBounds(310,340,180,40);
		AddButton.addMouseListener(new Append());

		new MenuButton(this,new Create(new ImageIcon("image/backCM.png")));


	}
	void textAppend() throws IOException
	{
		FileWriter fw = null;
		if(Create.file==null)
		{

			String str="Defi_"+CreateWord1.getText()+".txt";
			try {
				File file1=new File(str);
				fw =new FileWriter(file1,true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fw.write(CreateWord2.getText());
			fw.write("/");
			fw.write(CreateWord3.getText());
			fw.write(CreateC.LINE_SEPARATOR);
			fw.flush();
			fw.close();
			CreateWord2.setText("");
			CreateWord3.setText("");
		}
		else
		{
			try {
				fw =new FileWriter(Create.file,true);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			fw.write(CreateWord2.getText());
			fw.write("/");
			fw.write(CreateWord3.getText());
			fw.write(CreateC.LINE_SEPARATOR);
			fw.flush();
			fw.close();
			CreateWord2.setText("");
			CreateWord3.setText("");

		}
	}

	class Append implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {
			try {
				if(!(CreateWord2.getText().equals(""))&&!(CreateWord3.getText().equals("")))
					textAppend();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

}




//--------------------------------------------------------생성-C.문장 유형

class CreateC extends SetBackground
{
	static final String LINE_SEPARATOR 
	= System.getProperty("line.separator");

	JTextField CreateWord1;
	JTextArea CreateWord3;
	String wordbook;
	JButton AddButton;



	public CreateC(ImageIcon ico){
		super(ico);
		wordbook="txt파일 이름";

		//메인패널에 만든패널 연결        
		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JTextField(wordbook,150);
		CreateWord1.setBounds(210,95,500,30);
		this.add(CreateWord1);

		CreateWord3 = new JTextArea(540,250);

		JScrollPane scr=new JScrollPane(CreateWord3);
		scr.setSize(540, 100);
		scr.setBounds(100,190,610,130);
		this.add(scr);

		AddButton = new JButton(new ImageIcon("image/AddButton.png")); 
		AddButton.setBorderPainted(false); // 버튼 경계선 제거
		AddButton.setFocusPainted(false); //포커스(선택했던 버튼 표시) 제거
		AddButton.setContentAreaFilled(false);//버튼영역 배경 제거
		this.add(AddButton);
		AddButton.setBounds(310,340,180,40);
		AddButton.addMouseListener(new Append());

		new MenuButton(this,new Create(new ImageIcon("image/backCM.png")));
	}

	class Append implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {
			try {
				if(!(CreateWord3.getText().equals("")))
						textAppend();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

	void textAppend() throws IOException
	{
		FileWriter fw = null;
		if(Create.file==null)
		{

			String str="Sent_"+CreateWord1.getText()+".txt";
			try {
				File file1=new File(str);
				fw =new FileWriter(file1,true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			str=CreateWord3.getText();
			StringBuilder sb=new StringBuilder(str);
			try{
				String as=sb.substring(str.indexOf("<")+1, str.indexOf(">"));
				sb.delete(sb.indexOf("<")+1, sb.indexOf(">"));
				sb.insert(sb.indexOf("<")+1, "\t");
				str=sb.toString();
				fw.write(str);
				fw.write("/");
				fw.write(as);
				fw.write(LINE_SEPARATOR);
				fw.flush();
				fw.close();
			}
			catch(StringIndexOutOfBoundsException e)
			{			}
			CreateWord3.setText("");
		}
		else
		{
			try {
				fw =new FileWriter(Create.file,true);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			String str=CreateWord3.getText();
			StringBuilder sb=new StringBuilder(str);
			String as=sb.substring(str.indexOf("<")+1, str.indexOf(">"));
			sb.delete(sb.indexOf("<")+1, sb.indexOf(">"));
			sb.insert(sb.indexOf("<")+1, "\t");
			str=sb.toString();
			fw.write(str);
			fw.write("/");
			fw.write(as);
			fw.write(LINE_SEPARATOR);
			fw.flush();
			fw.close();

			CreateWord3.setText("");

		}
	}
}