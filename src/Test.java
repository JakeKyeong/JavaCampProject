
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class Test extends SetBackground{
	JLabel label; 
	JButton OpenFile, GoBtn;
	static String fileName;
	static File file=null;

	public Test(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		OpenFile = new JButton(new ImageIcon("image\\OpenFile2.png"));
		OpenFile.setBounds(132, 237, 162, 37);
		OpenFile.setBorderPainted(false);
		OpenFile.setFocusPainted(false);
		OpenFile.setContentAreaFilled(false);
		OpenFile.addActionListener(new OpenFileListener());
		this.add(OpenFile);

		label = new JLabel();
		label.setBounds(300, 240,300, 30);
		this.add(label);

		GoBtn = new JButton(new ImageIcon("image\\GoButton.png"));
		GoBtn.setBounds(607, 230, 55, 50);
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
				System.out.println("���� ����");
				fileName = chooser.getName(chooser.getSelectedFile());
				label.setText(fileName);
				file=chooser.getSelectedFile(); 
			}
		}
	}

	private class ButtonListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			//�ҷ��� �ؽ�Ʈ ���ϸ� (���ڸ�)�з��� ���� �ش� ������ �´� ���� �������� �̵����Ѿ� ��
			//�ҷ��� �ؽ�Ʈ ���ϸ��� ���������� (�ϱ��� �̸�)���� JField�� ���������� �ѱ��.
			if(file!=null)
			{
			switch(fileName.substring(0, 4))
			{
				case "Voca": //�⺻ ����:������
					Test1_A t1a=new Test1_A(new ImageIcon("image/backT1.png"));
					t1a.CreateWord1.setText(fileName);
					TestMate.stMain.setContentPane(t1a);
					break;
				case "Defi": //�⺻ ����:�ܴ��
					Test2_B t2b=new Test2_B(new ImageIcon("image/backT3.png"));
					t2b.CreateWord1.setText(fileName);
					TestMate.stMain.setContentPane(t2b);
					break;
				case "Sent": //�⺻ ����:��ĭ��
					Test3_C t3c=new Test3_C(new ImageIcon("image/backT3.png"));
					t3c.CreateWord1.setText(fileName);
					t3c.quizType.setVisible(false);//<---------��������
					TestMate.stMain.setContentPane(t3c);
					break;
			
					default :
						break;
				}
			}
		}

		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }


	}
}

class Test1_A extends SetBackground
{
	//������ ����
	//������ JLabel + ������ (�̹�����ư)
	//������ ���� ���� : �̹��� ��ư ���� �ؽ�Ʈ ����
	//�����̸� �ʷϻ�, Ʋ���ų� ���亸��� ������

	JTextArea label;
	JTextField range1,range2;
	JLabel CreateWord1, answercnt, wrongcnt;
	JButton Answer;
	String [] quiz_type={"������ ����","�ܴ�� ����"};
	JComboBox quizType;
	JLabel totalC,CompC,nonC,Index; 
	JRadioButton all,excep;
	Vector<String> word,mean,same,ans;//�ܾ�, ��, ���Ǿ�, ���Ǿ ������ ����
	int currIndex=-1; //��������� �ε��� <--------------��������
	BufferedReader in;
	JLabel[] question=new JLabel[4];
	int[] ran=new int[4];
	int yourRight=0; //<-----------------���� ���� ����
	int max, min; //<---------------�ε��� ���� ������


	Test1_A(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		quizType = new JComboBox(quiz_type);
		quizType.setBounds(544, 81, 160, 25);
		quizType.setFont(new Font("����",Font.BOLD,11));
		this.add(quizType);
		quizType.addItemListener(new ComboBoxChange(this)); //�ܴ������ ��ȯ���ִ� ������ -->�۵��ȵ�.......

		Answer = new JButton(new ImageIcon("image\\AnswerButton.png"));
		Answer.setBounds(580, 312, 150, 75);
		Answer.setBorderPainted(false);
		Answer.setFocusPainted(false);
		Answer.setContentAreaFilled(false);
		this.add(Answer);
		Answer.addActionListener(new WhatIsAnswer()); //<-------------���亸�� �̺�Ʈ������
		
		JButton newFile=new JButton();
		newFile.setBorderPainted(false); // ��ư ��輱 ����
		newFile.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		newFile.setContentAreaFilled(false);//��ư���� ��� ����
		newFile.setBounds(325, 345, 230, 45);
		this.add(newFile);
		newFile.addActionListener(new OpenFileListener2());

		////setting
		totalC = new JLabel(" ");
		totalC.setBounds(500, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(642, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(110, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);
		all.setSelected(true);


		excep = new JRadioButton();
		excep.setBounds(110, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);

		range1=new JTextField("1"); //�ε��� ���� ������ ���� �ؽ�Ʈ �ڽ�
		range1.setBounds(202,346,30,20);
		this.add(range1);

		range2=new JTextField();
		range2.setBounds(260,346,30,20);
		this.add(range2);
		loadFileInVetor();
		range2.setText(Integer.toString(word.size()));

		answercnt = new JLabel();
		answercnt.setBounds(410,325,100,20);
		this.add(answercnt);

		wrongcnt = new JLabel();
		wrongcnt.setBounds(510,325,100,20);
		this.add(wrongcnt);

		JButton open = new JButton(new ImageIcon("image/fileOpen.png"));
		open.setBorderPainted(false); // ��ư ��輱 ����
		open.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		open.setContentAreaFilled(false);//��ư���� ��� ����
		open.setBounds(329, 356,235,25);
		this.add(open);

		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));

		Select select=new Select();
		question[0]=new JLabel();
		question[0].setBounds(100, 220, 200, 30);
		question[0].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[0]);
		question[0].addMouseListener(select);

		question[1]=new JLabel();
		question[1].setBounds(410, 220, 200, 30);
		question[1].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[1]);
		question[1].addMouseListener(select);

		question[2]=new JLabel();
		question[2].setBounds(100, 255, 200, 30);
		question[2].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[2]);
		question[2].addMouseListener(select);

		question[3]=new JLabel();
		question[3].setBounds(410, 255, 200, 30);
		question[3].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[3]);
		question[3].addMouseListener(select);

		label=new JTextArea("������ �����մϴ�!");
		label.setBounds(98,122,610,90);
		label.setEditable(false);
		label.setLineWrap(true);
		label.setFont(new Font("Gothic",Font.BOLD,20));
		this.add(label);


		JButton next=new JButton(new ImageIcon("image/right_icon.png"));
		next.setBorderPainted(false); // ��ư ��輱 ����
		next.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		next.setContentAreaFilled(false);//��ư���� ��� ����
		next.setBounds(738, 195, 40, 40);
		this.add(next);
		next.addActionListener(new Next());

		max=word.size();
		min=0;
		totalC.setText(totalC.getText() + " " +word.size());
		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));

	}
	

	//------------------------------------------------

	class RangeSetting implements ItemListener //���� ���� �����ڽ� ���� ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==excep)
			{
				if(Integer.valueOf(range2.getText())>word.size())
					max=word.size();
				else if(Integer.valueOf(range2.getText())<0)
					max=word.size();
				else
					max=Integer.valueOf(range2.getText());

				if(Integer.valueOf(range1.getText())>word.size()-1)
					min=0;
				else if(Integer.valueOf(range1.getText())<0)
					min=0;
				else
					min=Integer.valueOf(range1.getText());

			}
			else
			{
				max=word.size();
				min=0;
			}
		}

	}

	//------------------------------------------------

	class WhatIsAnswer implements ActionListener //���亸�� ��ư Ŭ���� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex>-1&&currIndex!=word.size()) //<-----------------��������
				question[ran[0]].setForeground(Color.GREEN);
		}
	}

	//------------------------------------------------
	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		mean=new Vector<String>();
		same=new Vector<String>();
		ans=new Vector<String>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Test.file.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true)
		{         
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(str==null)
				break;

			st=new StringTokenizer(str,"/");
			word.add(st.nextToken());
			mean.add(st.nextToken());
			same.add(st.nextToken());
			ans.add(st.nextToken());


		}

		deleteBlank();
	}

	//-----------------------------------------------------
	class ComboBoxChange implements ItemListener //������ ->�ְ������� ��ȯ�� �߻��ϴ� ������ �̺�Ʈ
	{
		JPanel jp;
		ComboBoxChange(JPanel jp)
		{
			this.jp=jp;
		}
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox cbBox=(JComboBox)e.getSource();

			if(cbBox.getSelectedItem()=="�ܴ�� ����")
			{
				jp.removeAll();
				Test1_B n=new Test1_B(new ImageIcon("image/backT3.png"));
				n.CreateWord1.setText(Test.fileName);
				TestMate.stMain.setContentPane(n);
			}
		}
	}

	//-----------------------------------------------------

	void deleteBlank()//����ִ� ��������
	{
		String st1;
		StringBuilder str;
		for(int i=0;i<word.size();i++)
		{
			st1=mean.get(i);
			String[] split=st1.split(", ");
			str=new StringBuilder();
			for(int j=0;j<split.length;j++)
			{
				if(split[j].equals(" "))
					str.append(" ");
				else
					str.append(split[j]+" ");

				mean.set(i, str.toString());
			}
			st1=same.get(i);
			split=st1.split(", ");
			str=new StringBuilder();
			for(int j=0;j<split.length;j++)
			{
				if(split[j].equals(" "))
					str.append(" ");
				else
					str.append(split[j]+" ");

				same.set(i, str.toString());

			}
			st1=ans.get(i);
			split=st1.split(", ");
			str=new StringBuilder();
			for(int j=0;j<split.length;j++)
			{

				if(split[j].equals(" "))
					str.append(" ");
				else
					str.append(split[j]+" ");

				ans.set(i, str.toString());
			}
		}
	}

	//------------------------------------------------------

	class Select extends MouseAdapter //���콺�� ���� ���ý� ���� ó����
	{
		public void mouseClicked(MouseEvent e)
		{
			JLabel selectLa=(JLabel)e.getSource();
			if(currIndex!=-1&&currIndex!=word.size()) //<-----------��������
			{
				if(selectLa==question[ran[0]])
				{
					selectLa.setForeground(Color.GREEN);
					yourRight++;
				}
				else
				{
					question[ran[0]].setForeground(Color.GREEN);
					selectLa.setForeground(Color.RED);
				}         
			}
		}      
	}

	//------------------------------------------

	class Next implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex<max)
				currIndex++;


			for(int i=0;i<4;i++)
				question[i].setForeground(Color.BLACK);
			if(currIndex<max)
			{
				makeQs();
			}
			else if(currIndex==max) 
			{
				label.setText(yourRight + " �� ����! �����ϼ̽��ϴ�.");
				for(int i=0;i<question.length;i++)
				{
					question[i].setText("");
				}
				return;
			}         

			answercnt.setText(Integer.toString(yourRight));
			wrongcnt.setText(Integer.toString(currIndex-yourRight));
			Index.setText(Integer.toString(currIndex+1) +" of " + Integer.toString(word.size()));
			
		}

	}

	//------------------------------------------

	void makeQs()
	{
		boolean check=true;

		for(int i=0; i<ran.length;i++)
		{
			int rd=(int)(Math.random()*4);
			for(int j=0;j<i;j++)
			{            
				if(ran[j]==rd)
					check=false;
			}
			if(check==true)
			{
				ran[i] = rd;
			}
			else
			{
				check=true;
				i--;
			}
		}

	
			question[ran[0]].setText(word.get(currIndex));

		for(int i=1;i<ran.length;i++)
		{		
				question[ran[i]].setText(word.get(noIndexRandom()));
				
		}

		label.setText("�� : " +mean.get(currIndex)+"\n���Ǿ� : " + same.get(currIndex)+"\n���Ǿ� : " + ans.get(currIndex));
	}

	//------------------------------------------

	int noIndexRandom()
	{      
		while(true)
		{
			int rd=(int)(Math.random()*word.size());

			if(!(rd==currIndex))
				return rd;
			
			if(word.size()==1)
				return 0;
		}
		
	}

	//------------------------------------------

	int selectArr()
	{
		double mean=Math.random();
		double same=Math.random();
		double ans=Math.random();

		if(mean>same&&mean>ans)
			return 1;
		else if(same>ans&&same>mean)
			return 2;
		else
			return 3;
	}

	//------------------------------------------


}


class Test1_B extends SetBackground
{
	//�ܴ�� ����

	JLabel CreateWord1,answercnt,wrongcnt;
	String wordbook;
	JButton Answer;
	String [] quiz_type={"������ ����","�ܴ�� ����"};
	JComboBox quizType;
	JLabel totalC,CompC,nonC,Index; 
	JRadioButton all,excep;
	Vector<String> word,mean,same,ans;//�ܾ�, ��, ���Ǿ�, ���Ǿ ������ ����
	int currIndex = -1; //��������� �ε���
	BufferedReader in;
	JTextArea qus;
	JTextField answer,range1,range2;
	int yourRight=0;
	int max,min;


	Test1_B(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		JButton open = new JButton(new ImageIcon("image/fileOpen.png"));
		open.setBorderPainted(false); // ��ư ��輱 ����
		open.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		open.setContentAreaFilled(false);//��ư���� ��� ����
		open.setBounds(329, 356,235,25);
		this.add(open);

		quizType = new JComboBox(quiz_type);
		quizType.setBounds(544, 81, 160, 25);
		quizType.setFont(new Font("����",Font.BOLD,11));
		this.add(quizType);
		quizType.addItemListener(new ComboBoxChange(this));
		//   quizType.addActionListener(new selectType());


		Answer = new JButton(new ImageIcon("image\\AnswerButton.png"));
		Answer.setBounds(580, 312, 150, 75);
		Answer.setBorderPainted(false);
		Answer.setFocusPainted(false);
		Answer.setContentAreaFilled(false);
		this.add(Answer);
		Answer.addActionListener(new Check());


		////setting
		totalC = new JLabel(" ");
		totalC.setBounds(500, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(644, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);
		
		answercnt = new JLabel();
		answercnt.setBounds(410,325,100,20);
		this.add(answercnt);

		wrongcnt = new JLabel();
		wrongcnt.setBounds(510,325,100,20);
		this.add(wrongcnt);

		//JRadioButton manual,auto,sequen,random,all,excep;

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(110, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);

		excep = new JRadioButton();
		excep.setBounds(110, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);
		excep.addItemListener(new RangeSetting());

		qus=new JTextArea("������ �����մϴ�!");
		qus.setBounds(98,127,610,110);
		qus.setEditable(false);
		qus.setLineWrap(true);
		qus.setFont(new Font("Gothic",Font.BOLD,20));
		this.add(qus);

		answer=new JTextField();
		answer.setBounds(95, 246, 620, 40);
		this.add(answer);

		range1=new JTextField("1"); //�ε��� ���� ������ ���� �ؽ�Ʈ �ڽ�
		range1.setBounds(202,346,30,20);
		this.add(range1);

		range2=new JTextField();
		range2.setBounds(260,346,30,20);
		this.add(range2);
		loadFileInVetor();
		range2.setText(Integer.toString(word.size()));


		JButton next=new JButton(new ImageIcon("image/right_icon.png"));
		next.setBorderPainted(false); // ��ư ��輱 ����
		next.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		next.setContentAreaFilled(false);//��ư���� ��� ����
		next.setBounds(738, 195, 40, 40);
		this.add(next);
		next.addActionListener(new Next());

		max=word.size();
		min=0;

		totalC.setText(totalC.getText() + " " +word.size());

		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));
		JButton newFile=new JButton();
		newFile.setBorderPainted(false); // ��ư ��輱 ����
		newFile.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		newFile.setContentAreaFilled(false);//��ư���� ��� ����
		newFile.setBounds(325, 345, 230, 45);
		this.add(newFile);
		newFile.addActionListener(new OpenFileListener2());

	}
	
	

	class RangeSetting implements ItemListener //���� ���� �����ڽ� ���� ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==excep)
			{
				if(Integer.valueOf(range2.getText())>word.size())
					max=word.size();
				else if(Integer.valueOf(range2.getText())<0)
					max=word.size();
				else
					max=Integer.valueOf(range2.getText());

				if(Integer.valueOf(range1.getText())>word.size()-1)
					min=0;
				else if(Integer.valueOf(range1.getText())<0)
					min=0;
				else
					min=Integer.valueOf(range1.getText());

			}
			else
			{
				max=word.size();
				min=0;
			}
		}

	}


	//---------------------------------------------

	class ComboBoxChange implements ItemListener //������ ->�ְ������� ��ȯ�� �߻��ϴ� ������ �̺�Ʈ
	{
		JPanel jp;
		ComboBoxChange(JPanel jp)
		{
			this.jp=jp;
		}

		public void itemStateChanged(ItemEvent e)
		{
			JComboBox cbBox=(JComboBox)e.getSource();

			if(cbBox.getSelectedItem()=="������ ����")
			{

				jp.removeAll();
				Test1_A n=new Test1_A(new ImageIcon("image/backT1.png"));
				n.CreateWord1.setText(Test.fileName);
				TestMate.stMain.setContentPane(n);
			}
		}

	}

	//---------------------------------------------

	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		mean=new Vector<String>();
		same=new Vector<String>();
		ans=new Vector<String>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Test.file.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true)
		{         
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(str==null)
				break;

			st=new StringTokenizer(str,"/");
			word.add(st.nextToken());
			mean.add(st.nextToken());
			same.add(st.nextToken());
			ans.add(st.nextToken());


		}

		deleteBlank();

	}

	//------------------------------------------

	void deleteBlank()//����ִ� ��������
	{
		String st1;
		StringBuilder str;
		for(int i=0;i<word.size();i++)
		{
			st1=mean.get(i);
			String[] split=st1.split(", ");
			str=new StringBuilder();
			for(int j=0;j<split.length;j++)
			{

				if(split[j].equals(" "))
					str.append(" ");
				else
					str.append(split[j]+" ");

				mean.set(i, str.toString()+", ");
			}

		}
	}

	//------------------------------------------

	void checkAnswer()
	{
		

		if(word.get(currIndex).equals(answer.getText()))
		{
			yourRight++;
			answer.setForeground(Color.GREEN);
		}
		else
		{
			answer.setForeground(Color.RED);
			answer.setText(answer.getText()+"\t ���� :"+mean.get(currIndex));
		}
	}

	//------------------------------------------

	class Check implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(currIndex>=0) //<------------
				checkAnswer();
		}

	}

	//-------------------------------------------
	class Next implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex<max)
			{
				currIndex++;				
			}
			answer.setForeground(Color.BLACK);

			if(currIndex<max)
			{
				qus.setText("�� : " +mean.get(currIndex)+"\n���Ǿ� : " + same.get(currIndex)+"\n���Ǿ� : " + ans.get(currIndex));
			}
			else if(currIndex==max)
			{
				qus.setText(yourRight + " �� ����! �����ϼ̽��ϴ�.");
				return;
			}

			answer.setText("");
			answercnt.setText(Integer.toString(yourRight));
			wrongcnt.setText(Integer.toString(currIndex-yourRight));
			Index.setText(Integer.toString(currIndex+1) +" of " + Integer.toString(word.size()));

		}
	}
	//-------------------------------------------

}

class Test2_B extends SetBackground
{
	//�ܴ�� ����
	//������(JLabel) + ���(JTextField) 
	//����� ���� ���� ������ ��ġ�ϸ� JTextField�� ���� �ؽ�Ʈ�� �ʷϻ����� �ٲٰ�
	//����ġ�ϸ� ���������� ������ ǥ���Ѵ�
	//���亸�⸦ ������ ���������� �����Ѵ�

	JLabel CreateWord1,answercnt,wrongcnt;
	JButton Answer;
	String [] quiz_type={"������ ����","�ܴ�� ����"};
	JComboBox quizType;
	JLabel totalC,CompC,nonC,Index; 
	JRadioButton all,excep;
	Vector<String> word,ans;//�ܾ�, ��, ���Ǿ�, ���Ǿ ������ ����
	int currIndex=-1; //��������� �ε���
	BufferedReader in;
	JTextArea qus;
	JTextField answer,range1,range2;
	int yourRight=0;
	int max,min;


	public Test2_B(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		JButton open = new JButton(new ImageIcon("image/fileOpen.png"));
		open.setBorderPainted(false); // ��ư ��輱 ����
		open.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		open.setContentAreaFilled(false);//��ư���� ��� ����
		open.setBounds(329, 356,235,25);
		this.add(open);

		quizType = new JComboBox(quiz_type);
		quizType.setBounds(544, 81, 160, 25);
		quizType.setFont(new Font("����",Font.BOLD,11));
		quizType.setSelectedItem(quiz_type[1]);
		this.add(quizType);
		quizType.addItemListener(new ComboBoxChange(this));
		//   quizType.addActionListener(new selectType());

		Answer = new JButton(new ImageIcon("image\\AnswerButton.png"));
		Answer.setBounds(580, 312, 150, 75);
		Answer.setBorderPainted(false);
		Answer.setFocusPainted(false);
		Answer.setContentAreaFilled(false);
		this.add(Answer);
		Answer.addActionListener(new Check());


		////setting
		totalC = new JLabel(" ");
		totalC.setBounds(500, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		
		

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(644, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		//JRadioButton manual,auto,sequen,random,all,excep;

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(110, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);
		all.setSelected(true);


		excep = new JRadioButton();
		excep.setBounds(110, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);
		excep.addItemListener(new RangeSetting());

		qus=new JTextArea("������ �����մϴ�.");
		qus.setBounds(95,130,610,100);
		qus.setEditable(false);
		qus.setLineWrap(true);
		qus.setFont(new Font("����",Font.BOLD,15));
		this.add(qus);

		answer=new JTextField();
		answer.setBounds(95, 246, 620, 40);
		this.add(answer);

		range1=new JTextField("1"); //�ε��� ���� ������ ���� �ؽ�Ʈ �ڽ�
		range1.setBounds(202,346,30,20);
		this.add(range1);

		range2=new JTextField();
		range2.setBounds(260,346,30,20);

		this.add(range2);
		loadFileInVetor();
		range2.setText(Integer.toString(word.size()));
		
		answercnt = new JLabel();
		answercnt.setBounds(410,325,100,20);
		this.add(answercnt);

		wrongcnt = new JLabel();
		wrongcnt.setBounds(510,325,100,20);
		this.add(wrongcnt);


		JButton next=new JButton(new ImageIcon("image/right_icon.png"));
		next.setBorderPainted(false); // ��ư ��輱 ����
		next.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		next.setContentAreaFilled(false);//��ư���� ��� ����
		next.setBounds(738, 195, 40, 40);
		this.add(next);
		next.addActionListener(new Next());
		
		JButton newFile=new JButton();
		newFile.setBorderPainted(false); // ��ư ��輱 ����
		newFile.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		newFile.setContentAreaFilled(false);//��ư���� ��� ����
		newFile.setBounds(325, 341, 230, 50);
		this.add(newFile);
		newFile.addActionListener(new OpenFileListener2());

		max=word.size();
		min=0;

		totalC.setText(totalC.getText() + " " +word.size());


		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));
	}

	class RangeSetting implements ItemListener //���� ���� �����ڽ� ���� ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==excep)
			{
				if(Integer.valueOf(range2.getText())>word.size())
					max=word.size();
				else if(Integer.valueOf(range2.getText())<0)
					max=word.size();
				else
					max=Integer.valueOf(range2.getText());

				if(Integer.valueOf(range1.getText())>word.size()-1)
					min=0;
				else if(Integer.valueOf(range1.getText())<0)
					min=0;
				else
					min=Integer.valueOf(range1.getText());

				currIndex=min-1;

			}
			else
			{
				max=word.size();
				min=0;
				currIndex=min-1;
			}
		}

	}


	//---------------------------------------------

	class ComboBoxChange implements ItemListener //������ ->�ְ������� ��ȯ�� �߻��ϴ� ������ �̺�Ʈ
	{
		JPanel jp;
		ComboBoxChange(JPanel jp)
		{
			this.jp=jp;
		}

		public void itemStateChanged(ItemEvent e)
		{
			JComboBox cbBox=(JComboBox)e.getSource();

			if(cbBox.getSelectedItem()=="������ ����")
			{

				jp.removeAll();
				Test2_A n=new Test2_A(new ImageIcon("image/backT1.png")); //<---------------------???
				n.CreateWord1.setText(Test.fileName);
				TestMate.stMain.setContentPane(n);
			}
		}

	}

	//---------------------------------------------

	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		ans=new Vector<String>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Test.file.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true)
		{         
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(str==null)
				break;

			st=new StringTokenizer(str,"/");
			ans.add(st.nextToken());
			word.add(st.nextToken());

		}

		deleteBlank();
	}
	
	//------------------------------------------
	
	void deleteBlank()//����ִ� ��������
	{
		String st1;
		StringBuilder str;
		for(int i=0;i<word.size();i++)
		{
			String[] split;
			
			st1=ans.get(i);
			split=st1.split(", ");
			str=new StringBuilder();
			for(int j=0;j<split.length;j++)
			{

				if(split[j].equals(" "))
					str.append(" ");
				else
					str.append(split[j]+" ");

				ans.set(i, str.toString());
			}
		}
	}

	//------------------------------------------


	void checkAnswer()
	{
		if(answer.getText().replaceAll(" ", "").equals(ans.get(currIndex).replaceAll(" ", "")))//<-----��������
		{
			yourRight++;
			answer.setForeground(Color.GREEN);
		}		
		else
		{
			answer.setForeground(Color.RED);
			answer.setText(answer.getText()+"\t ���� :"+ans.get(currIndex));
		}
	}

	//------------------------------------------

	class Check implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(currIndex>-1 &&currIndex<word.size())
			{
				checkAnswer();
			}
		}
	}

	//-------------------------------------------
	class Next implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex<max)
				currIndex++;

			answer.setForeground(Color.BLACK);

			if(currIndex<max)
			{
				qus.setText(word.get(currIndex));
			}
			else if(currIndex==max)
			{
				qus.setText(yourRight + " �� ����! �����ϼ̽��ϴ�.");
				return;
			}

			answer.setText("");
			answercnt.setText(Integer.toString(yourRight));
			wrongcnt.setText(Integer.toString(currIndex-yourRight));
			Index.setText(Integer.toString(currIndex+1) +" of " + Integer.toString(word.size()));
		}
	}

}

class Test2_A extends SetBackground
{
	//������ ����

	JTextArea label;
	JTextField range1,range2;
	JLabel CreateWord1,answercnt,wrongcnt; 
	JButton Answer;
	String [] quiz_type={"������ ����","�ܴ�� ����"};
	JComboBox quizType;
	JLabel totalC,CompC,nonC,Index; 
	JRadioButton all,excep;
	Vector<String> word, ans;//�ܾ�, ��, ���Ǿ�, ���Ǿ ������ ����
	int currIndex=-1; //��������� �ε���
	BufferedReader in;
	JLabel[] question=new JLabel[4];
	int[] ran=new int[4];
	int yourRight=0; //<-----------------���� ���� ����
	int max, min; //<---------------�ε��� ���� ������
	boolean click=true;


	public Test2_A(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		
		answercnt = new JLabel();
		answercnt.setBounds(410,325,100,20);
		this.add(answercnt);

		wrongcnt = new JLabel();
		wrongcnt.setBounds(510,325,100,20);
		this.add(wrongcnt);
		
		
		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		JButton open = new JButton(new ImageIcon("image/fileOpen.png"));
		open.setBorderPainted(false); // ��ư ��輱 ����
		open.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		open.setContentAreaFilled(false);//��ư���� ��� ����
		open.setBounds(329, 356,235,25);
		this.add(open);


		quizType = new JComboBox(quiz_type);
		quizType.setBounds(544, 81, 160, 25);
		quizType.setFont(new Font("����",Font.BOLD,11));
		this.add(quizType);
		quizType.addItemListener(new ComboBoxChange(this)); //�ܴ������ ��ȯ���ִ� ������ -->�۵��ȵ�.......

		Answer = new JButton(new ImageIcon("image\\AnswerButton.png"));
		Answer.setBounds(580, 312, 150,75);
		Answer.setBorderPainted(false);
		Answer.setFocusPainted(false);
		Answer.setContentAreaFilled(false);
		this.add(Answer);
		Answer.addActionListener(new WhatIsAnswer()); //<-------------���亸�� �̺�Ʈ������


		////setting
		totalC = new JLabel(" ");
		totalC.setBounds(500, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);


		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(644, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		//JRadioButton manual,auto,sequen,random,all,excep;

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(110, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);
		all.setSelected(true);


		excep = new JRadioButton();
		excep.setBounds(110, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);
		excep.addItemListener(new RangeSetting());

		range1=new JTextField("1"); //�ε��� ���� ������ ���� �ؽ�Ʈ �ڽ�
		range1.setBounds(202,346,30,20);
		this.add(range1);

		range2=new JTextField();
		range2.setBounds(260,346,30,20);

		this.add(range2);
		loadFileInVetor();
		range2.setText(Integer.toString(word.size()));
		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));

		Select select=new Select();
		question[0]=new JLabel("");
		question[0].setBounds(100, 220, 200, 30);
		question[0].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[0]);
		question[0].addMouseListener(select);

		question[1]=new JLabel("");
		question[1].setBounds(410, 220, 200, 30);
		question[1].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[1]);
		question[1].addMouseListener(select);

		question[2]=new JLabel("");
		question[2].setBounds(100, 255, 200, 30);
		question[2].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[2]);
		question[2].addMouseListener(select);

		question[3]=new JLabel("");
		question[3].setBounds(410, 255, 200, 30);
		question[3].setFont(new Font("Gothic",Font.BOLD,15));
		this.add(question[3]);
		question[3].addMouseListener(select);

		label=new JTextArea("������ �����մϴ�!");
		label.setBounds(98,122,610,90);
		label.setEditable(false);
		label.setLineWrap(true);
		label.setFont(new Font("Gothic",Font.BOLD,20));
		this.add(label);
		
		JButton newFile=new JButton();
		newFile.setBorderPainted(false); // ��ư ��輱 ����
		newFile.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		newFile.setContentAreaFilled(false);//��ư���� ��� ����
		newFile.setBounds(325, 352, 230, 55);
		this.add(newFile);
		newFile.addActionListener(new OpenFileListener2());


		JButton next=new JButton(new ImageIcon("image/right_icon.png"));
		next.setBorderPainted(false); // ��ư ��輱 ����
		next.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		next.setContentAreaFilled(false);//��ư���� ��� ����
		next.setBounds(738, 195, 40, 40);
		this.add(next);
		next.addActionListener(new Next());

		max=word.size();
		min=0;
		totalC.setText(totalC.getText() + " " +word.size());
		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));
	}

	//------------------------------------------------

	class RangeSetting implements ItemListener //���� ���� �����ڽ� ���� ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==excep)
			{
				if(Integer.valueOf(range2.getText())>word.size())
					max=word.size();
				else if(Integer.valueOf(range2.getText())<0)
					max=word.size();
				else
					max=Integer.valueOf(range2.getText());

				if(Integer.valueOf(range1.getText())>word.size()-1)
					min=0;
				else if(Integer.valueOf(range1.getText())<0)
					min=0;
				else
					min=Integer.valueOf(range1.getText());

				currIndex=min-1;

			}
			else
			{
				max=word.size();
				min=0;
				currIndex=min-1;
			}
		}

	}

	//------------------------------------------------

	class WhatIsAnswer implements ActionListener //���亸�� ��ư Ŭ���� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex>-1 &&currIndex<word.size()) //<---------���Ⱑ �ٲ�
				question[ran[0]].setForeground(Color.GREEN);
		}
	}

	//------------------------------------------------
	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		ans=new Vector<String>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Test.file.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true)
		{         
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(str==null)
				break;

			st=new StringTokenizer(str,"/");
			
			word.add(st.nextToken());
			ans.add(st.nextToken());
			

		}

	}

	//-----------------------------------------------------
	class ComboBoxChange implements ItemListener //������ ->�ְ������� ��ȯ�� �߻��ϴ� ������ �̺�Ʈ
	{
		JPanel jp;
		ComboBoxChange(JPanel jp)
		{
			this.jp=jp;
		}
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox cbBox=(JComboBox)e.getSource();

			if(cbBox.getSelectedItem()=="�ܴ�� ����")
			{
				jp.removeAll();
				Test2_B n=new Test2_B(new ImageIcon("image/backT3.png"));
				n.CreateWord1.setText(Test.fileName);
				TestMate.stMain.setContentPane(n);
			}
		}

	}

	//-----------------------------------------------------

	//------------------------------------------------------

	class Select extends MouseAdapter //���콺�� ���� ���ý� ���� ó����
	{
		public void mouseClicked(MouseEvent e)
		{
			JLabel selectLa=(JLabel)e.getSource();

			if(selectLa==question[ran[0]])
			{
				selectLa.setForeground(Color.GREEN);
				if(click==true)
				{
					yourRight++;
					click=false;
				}
			}
			else
			{
				question[ran[0]].setForeground(Color.GREEN);
				selectLa.setForeground(Color.RED);
			}         


		}      
	}

	//------------------------------------------

	class Next implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex<max)
				currIndex++;

			for(int i=0;i<4;i++)
				question[i].setForeground(Color.BLACK);
			if(currIndex<max)
			{
				makeQs();
			}
			else if(currIndex==max)
			{
				label.setText(yourRight + " �� ����! �����ϼ̽��ϴ�.");
				return;
			}         
			click=true;
			answercnt.setText(Integer.toString(yourRight));
			wrongcnt.setText(Integer.toString(currIndex-yourRight));
			Index.setText(Integer.toString(currIndex+1) +" of " + Integer.toString(word.size()));
		}

	}

	//------------------------------------------

	void makeQs()
	{
		boolean check=true;

		for(int i=0; i<ran.length;i++)
		{
			int rd=(int)(Math.random()*4);
			for(int j=0;j<i;j++)
			{            
				if(ran[j]==rd)
					check=false;
			}
			if(check==true)
			{
				ran[i] = rd;
			}
			else
			{
				check=true;
				i--;
			}
		}


		question[ran[0]].setText(ans.get(currIndex));


		for(int i=1;i<ran.length;i++)
		{
			question[ran[i]].setText(ans.get(noIndexRandom()));
		}

		label.setText(word.get(currIndex));
	}

	//------------------------------------------

	int noIndexRandom()
	{      
		while(true)
		{
			int rd=(int)(Math.random()*word.size());

			if(!(rd==currIndex))
				return rd;
			
			if(word.size()==1)
				return 0;
		}
	}

	//------------------------------------------


}

class Test3_C extends SetBackground
{
	//��ĭ��

	JLabel label,answercnt, wrongcnt; 
	JLabel CreateWord1;
	JTextArea CreateWord3, qus;
	JButton Answer;
	String [] quiz_type={"������ ����","�ܴ�� ����"};
	JComboBox quizType;
	JLabel totalC,CompC,nonC,Index; 
	JRadioButton all,excep;
	Vector<String> word,ans;//�ܾ�, ��, ���Ǿ�, ���Ǿ ������ ����
	int currIndex=-1; //��������� �ε���
	BufferedReader in;
	JTextField answer,range1,range2;
	int yourRight=0;
	int max,min;


	public Test3_C(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);


		answercnt = new JLabel();
		answercnt.setBounds(410,325,100,20);
		this.add(answercnt);

		wrongcnt = new JLabel();
		wrongcnt.setBounds(510,325,100,20);
		this.add(wrongcnt);
		
		
		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		quizType = new JComboBox(quiz_type);
		quizType.setBounds(544, 81, 160, 25);
		quizType.setFont(new Font("����",Font.BOLD,11));
		quizType.setSelectedItem(quiz_type[1]);
		this.add(quizType);
		quizType.addItemListener(new ComboBoxChange(this));

		Answer = new JButton(new ImageIcon("image\\AnswerButton.png"));
		Answer.setBounds(580, 312, 150, 60);
		Answer.setBorderPainted(false);
		Answer.setFocusPainted(false);
		Answer.setContentAreaFilled(false);
		this.add(Answer);
		Answer.addActionListener(new Check());



		////setting
		totalC = new JLabel(" ");
		totalC.setBounds(500, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(644, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		//JRadioButton manual,auto,sequen,random,all,excep;

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(110, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);

		excep = new JRadioButton();
		excep.setBounds(110, 342, 30, 30);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);

		qus=new JTextArea("������ �����մϴ�.");
		qus.setBounds(95,130,610,100);
		qus.setEditable(false);
		qus.setLineWrap(true);
		qus.setFont(new Font("����",Font.BOLD,15));
		this.add(qus);

		answer=new JTextField();
		answer.setBounds(95, 246, 620, 40);
		this.add(answer);

		range1=new JTextField("1"); //�ε��� ���� ������ ���� �ؽ�Ʈ �ڽ�
		range1.setBounds(202,346,30,20);
		this.add(range1);

		range2=new JTextField();
		range2.setBounds(260,346,30,20);

		this.add(range2);
		loadFileInVetor();
		range2.setText(Integer.toString(word.size()));
		
		JButton newFile=new JButton();
		newFile.setBorderPainted(false); // ��ư ��輱 ����
		newFile.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		newFile.setContentAreaFilled(false);//��ư���� ��� ����
		newFile.setBounds(325, 345, 230, 45);
		this.add(newFile);
		newFile.addActionListener(new OpenFileListener2());


		JButton next=new JButton(new ImageIcon("image/right_icon.png"));
		next.setBorderPainted(false); // ��ư ��輱 ����
		next.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		next.setContentAreaFilled(false);//��ư���� ��� ����
		next.setBounds(738, 195, 40, 40);
		this.add(next);
		next.addActionListener(new Next());

		max=word.size();
		min=0;


		totalC.setText(totalC.getText() +" "+ Integer.toString(word.size()));



		new MenuButton(this,new Test(new ImageIcon("image/backT.png")));
	}

	class RangeSetting implements ItemListener //���� ���� �����ڽ� ���� ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==excep)
			{
				if(Integer.valueOf(range2.getText())>word.size())
					max=word.size();
				else if(Integer.valueOf(range2.getText())<0)
					max=word.size();
				else
					max=Integer.valueOf(range2.getText());

				if(Integer.valueOf(range1.getText())>word.size()-1)
					min=0;
				else if(Integer.valueOf(range1.getText())<0)
					min=0;
				else
					min=Integer.valueOf(range1.getText());

				currIndex=min-1;

			}
			else
			{
				max=word.size();
				min=0;
				currIndex=min-1;
			}
		}

	}


	//---------------------------------------------

	class ComboBoxChange implements ItemListener //������ ->�ְ������� ��ȯ�� �߻��ϴ� ������ �̺�Ʈ
	{
		JPanel jp;
		ComboBoxChange(JPanel jp)
		{
			this.jp=jp;
		}

		public void itemStateChanged(ItemEvent e)
		{
			JComboBox cbBox=(JComboBox)e.getSource();

			if(cbBox.getSelectedItem()=="������ ����")
			{

				jp.removeAll();
				Test2_A n=new Test2_A(new ImageIcon("image/backT1.png")); //<---------------------???

				TestMate.stMain.setContentPane(n);
			}
		}

	}

	//---------------------------------------------

	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		ans=new Vector<String>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Test.file.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true)
		{         
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(str==null)
				break;

			st=new StringTokenizer(str,"/");
			word.add(st.nextToken());
			ans.add(st.nextToken());
		}

	}

	//------------------------------------------


	void checkAnswer()
	{
		if(answer.getText().replaceAll(" ", "").equals(ans.get(currIndex).replaceAll(" ", "")))//<-----��������
		{
			yourRight++;
			answer.setForeground(Color.GREEN);
		}		
		else
		{
			answer.setForeground(Color.RED);
			answer.setText(answer.getText()+"\t ���� :"+ans.get(currIndex));
		}
	}

	//------------------------------------------

	class Check implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(currIndex>-1 &&currIndex<word.size()) //<---------���Ⱑ �ٲ�
				checkAnswer();

		}

	}

	//-------------------------------------------
	class Next implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(currIndex<max)
				currIndex++;

			answer.setForeground(Color.BLACK);

			if(currIndex<max)
			{
				qus.setText(word.get(currIndex));
			}
			else if(currIndex==max)
			{
				qus.setText(yourRight + " �� ����! �����ϼ̽��ϴ�.");
				return;
			}
			Index.setText(Integer.toString(currIndex+1) +" of " + Integer.toString(word.size()));
			answer.setText("");
			answercnt.setText(Integer.toString(yourRight));
			wrongcnt.setText(Integer.toString(currIndex-yourRight));

		}
	}
}

class OpenFileListener2 implements ActionListener{
	JFileChooser chooser;

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		int ret = chooser.showOpenDialog(null);
		if(ret == JFileChooser.APPROVE_OPTION){
			System.out.println("���� ����");
			Test.fileName = chooser.getName(chooser.getSelectedFile());
			//label.setText(Test.fileName);
			Test.file=chooser.getSelectedFile(); 
			
			if(Test.file!=null)
			{
			switch(Test.fileName.substring(0, 4))
			{
				case "Voca": //�⺻ ����:������
					Test1_A t1a=new Test1_A(new ImageIcon("image/backT1.png"));
					t1a.CreateWord1.setText(Test.fileName);
					TestMate.stMain.setContentPane(t1a);
					break;
				case "Defi": //�⺻ ����:�ܴ��
					Test2_B t2b=new Test2_B(new ImageIcon("image/backT3.png"));
					t2b.CreateWord1.setText(Test.fileName);
					TestMate.stMain.setContentPane(t2b);
					break;
				case "Sent": //�⺻ ����:��ĭ��
					Test3_C t3c=new Test3_C(new ImageIcon("image/backT3.png"));
					t3c.CreateWord1.setText(Test.fileName);
					TestMate.stMain.setContentPane(t3c);
					t3c.quizType.setVisible(false);//<---------��������
					break;
			
					default :
						break;
				}
			}
		}
	}
}