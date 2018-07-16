
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class Memorize extends SetBackground{
	JLabel label; 
	JButton OpenFile, GoBtn;
	static String fileName;
	static File file;
	String wordbook;

	public Memorize(ImageIcon ico){
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
			switch(fileName.substring(0, 4))
			{
			case "Voca":
				Memorize1 m1=new Memorize1(new ImageIcon("image/backM_2.png"));
				m1.CreateWord1.setText(fileName);
				TestMate.stMain.setContentPane(m1);
				break;
			case "Defi":
				Memorize2 m2=new Memorize2(new ImageIcon("image/backM2.png"));
				m2.CreateWord1.setText(fileName);
				TestMate.stMain.setContentPane(m2);
				break;
			case "Sent":
				Memorize3 m3=new Memorize3(new ImageIcon("image/backM3.png"));
				m3.CreateWord1.setText(fileName);
				TestMate.stMain.setContentPane(m3);
				break;
			}
		}

		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }


	}
}

class Memorize1 extends SetBackground
{
	JLabel CreateWord1;
	JLabel totalC,CompC,nonC,Index, left,right; 
	JRadioButton manual,auto,sequen,random,all,excep;
	JCheckBox comp;
	JComboBox<String> cbBox1, cbBox2;
	Vector<String> word, mean, same, ans; //�ܾ� �� ���Ǿ� ���Ǿ� ����� ����
	Vector<Integer> ran, seq;//���� , ���� �� ����� ����
	BufferedReader in; //���� �б��
	int currIndex=0; //������½� ���� �ε���
	//boolean[] finish; //�ϱ�Ϸ� üũ�� �迭
	int randomIndex=0;
	JTextArea ta;//�ڵ��ѱ�� ����� ���Է� (�ӽ�)
	Timer timer;//�ڵ��ѱ��� ������

	public Memorize1(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);
		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		
			totalC = new JLabel("�� ī�� ����");
			totalC.setBounds(198, 8, 100, 20);
			totalC.setForeground(Color.white);
			totalC.setFont(new Font("����",Font.BOLD,11));
			add(totalC);

			CompC = new JLabel("�Ϸ� ����");
			CompC.setBounds(352, 8, 100, 20);
			CompC.setForeground(Color.white);
			CompC.setFont(new Font("����",Font.BOLD,11));
			add(CompC);

			nonC = new JLabel("�̾ϱ� ����");
			nonC.setBounds(485, 8, 100, 20);
			nonC.setForeground(Color.white);
			nonC.setFont(new Font("����",Font.BOLD,11));
			add(nonC);

			Index = new JLabel("�ε���ǥ��");
			Index.setBounds(630, 8, 100, 20);
			Index.setForeground(Color.white);
			Index.setFont(new Font("����",Font.BOLD,11));
			add(Index);

			ButtonGroup ma = new ButtonGroup();

			manual = new JRadioButton();
			manual.setBounds(110, 322, 30, 30);
			manual.setBorderPainted(false);
			manual.setFocusPainted(false);
			manual.setContentAreaFilled(false);
			ma.add(manual);
			add(manual);
			manual.setSelected(true); //�⺻ ����

			auto = new JRadioButton();
			auto.setBounds(110, 342, 30, 30);
			auto.setBorderPainted(false);
			auto.setFocusPainted(false);
			auto.setContentAreaFilled(false);
			ma.add(auto);
			add(auto);
			auto.addItemListener(new AutoNext());



			ButtonGroup ord = new ButtonGroup();

			sequen = new JRadioButton();
			sequen.setBounds(210, 322, 30, 30);
			sequen.setBorderPainted(false);
			sequen.setFocusPainted(false);
			sequen.setContentAreaFilled(false);
			ord.add(sequen);
			add(sequen);
			sequen.setSelected(true);// �⺻����

			random = new JRadioButton();
			random.setBounds(210, 342, 30, 30);
			random.setBorderPainted(false);
			random.setFocusPainted(false);
			random.setContentAreaFilled(false);
			ord.add(random);
			add(random);

			ButtonGroup rng = new ButtonGroup();

			all = new JRadioButton();
			all.setBounds(330, 322, 30, 30);
			all.setBorderPainted(false);
			all.setFocusPainted(false);
			all.setContentAreaFilled(false);
			rng.add(all);
			add(all);
			all.addItemListener(new CheckWord());


			excep = new JRadioButton();
			excep.setBounds(330, 342, 30, 30);
			excep.setBorderPainted(false);
			excep.setFocusPainted(false);
			excep.setContentAreaFilled(false);
			rng.add(excep);
			add(excep);
			excep.setSelected(true);// �⺻������ ����

			comp = new JCheckBox();
			comp.setBounds(650, 319, 20, 20);
			comp.setBorderPainted(false);
			comp.setFocusPainted(false);
			comp.setContentAreaFilled(false);
			add(comp);
			comp.addItemListener(new Check());

			String[] cbBoxItem={"�ܾ�", "��","���Ǿ�","���Ǿ�"};
			cbBox1=new JComboBox<String>();
			for(int i=0; i<cbBoxItem.length;i++)
				cbBox1.addItem(cbBoxItem[i]);
			cbBox1.setBounds(97, 120, 293, 30);
			add(cbBox1);
			cbBox1.addActionListener(new ComboBoxChange());

			cbBox2=new JComboBox<String>();
			for(int i=0; i<cbBoxItem.length;i++)
				cbBox2.addItem(cbBoxItem[i]);
			cbBox2.setBounds(407, 120, 293, 30);
			add(cbBox2);
			cbBox2.addActionListener(new ComboBoxChange());

			left=new JLabel();
			left.setBounds(100, 160, 200, 100);
			left.setFont(new Font("Gothic",Font.BOLD,30));
			add(left);

			right=new JLabel();
			right.setBounds(407, 160, 200, 100);
			right.setFont(new Font("Gothic",Font.BOLD,30));
			add(right);

			JButton ntButton=new JButton(new ImageIcon("image/right_icon.png"));
			ntButton.setBorderPainted(false); // ��ư ��輱 ����
			ntButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
			ntButton.setContentAreaFilled(false);//��ư���� ��� ����
			ntButton.setBounds(738, 195, 40, 40);
			add(ntButton);
			ntButton.addActionListener(new Next());

			JButton backButton=new JButton(new ImageIcon("image/left_icon.png"));
			backButton.setBorderPainted(false); // ��ư ��輱 ����
			backButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
			backButton.setContentAreaFilled(false);//��ư���� ��� ����
			backButton.setBounds(23, 195, 40, 40);
			add(backButton);
			backButton.addActionListener(new Back());
			loadFileInVetor();//������ ���;ȿ� ����ְ�
			makeSeqArr(); //�������� �ε��� ����


			ta=new JTextArea("3"); //�ڵ������ 
			ta.setBounds(650, 347, 50, 20);
			add(ta);

			totalC.setText(String.valueOf(word.size()));//�� ī�� ���� ǥ��
			left.setText(word.get(0));
			right.setText(word.get(0));
			
		new MenuButton(this,new Memorize(new ImageIcon("image/backM.png")));


	}

		class Timer extends Thread //�ڵ��ѱ��
		{
			int time;

			public Timer(int time)
			{
				this.time=time;

			}

			public void run()
			{
				while(true)
				{         


					if(random.isSelected())
					{

						outPutWord(cbBox1,left,ran.get(randomIndex));
						outPutWord(cbBox2,right,ran.get(randomIndex));

						if(randomIndex<ran.size()-1)
							randomIndex++;
						else if(randomIndex==ran.size())
						{
							//manual.setSelected(true);
							timer.interrupt();                     
						}

						CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
						nonC.setText(String.valueOf(ran.size()));
						Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
					}
					else
					{
						outPutWord(cbBox1,left,seq.get(currIndex));
						outPutWord(cbBox2,right,seq.get(currIndex));
						if(currIndex<seq.size()-1)
							currIndex++;
						else if(currIndex==seq.size())
						{
							//manual.setSelected(true);
							timer.interrupt();                  
						}

						CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
						nonC.setText(String.valueOf(ran.size()));
						Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
					}
					if(comp.isSelected())
						comp.setSelected(false);

					try 
					{
						if(timer.isAlive())
							sleep(time*1000);
					}   
					catch (InterruptedException e) 
					{
						return;
					}

				}
			}

		}


		void makeSeqArr() //������� �������ε����� ����� �迭�� �����.
		{
			seq=new Vector<Integer>();
			for(int i=0;i<word.size();i++)
				seq.add(i);
		}

		class AutoNext implements ItemListener //�ڵ��ѱ��
		{
			public void itemStateChanged(ItemEvent e)
			{
				JRadioButton rb=(JRadioButton)e.getSource();

				if(rb.isSelected())
				{
					timer=new Timer(Integer.parseInt(ta.getText()));
					timer.start();
				}
				else
				{            
					timer.interrupt();
				}
			}

		}

		class CheckWord implements ItemListener //��¹���������ư ���� �̺�Ʈ
		{
			public void itemStateChanged(ItemEvent e) 
			{
				JRadioButton rb=(JRadioButton)e.getSource();

				if(rb.isSelected())
				{
					currIndex=0;
					randomIndex=0;
					makeSeqArr();
					makeRandomArr();
					comp.setEnabled(false);
				}
				else
					comp.setEnabled(true);

			}
		}
		class Random implements ItemListener //����������ư Ŭ�������� �̺�Ʈ
		{
			public void itemStateChanged(ItemEvent e) 
			{
				JRadioButton rb=(JRadioButton)e.getSource();

				if(rb==random)
				{
					//makeRandomArr();
					randomIndex=0;
				}
				else
				{
					currIndex=0;
				}
			}

		}
		class Next implements ActionListener //������ư �������� �̺�Ʈ
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(random.isSelected())
				{   
					if(randomIndex<ran.size()-1)
					{
						randomIndex++;
						outPutWord(cbBox1,left,ran.get(randomIndex));
						outPutWord(cbBox2,right,ran.get(randomIndex));
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
				}
				else
				{



					if(currIndex<seq.size()-1)
					{
						currIndex++;
						outPutWord(cbBox1,left,seq.get(currIndex));
						outPutWord(cbBox2,right,seq.get(currIndex));
					}

					CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(seq.size()));
					Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
				}
				if(comp.isSelected())
					comp.setSelected(false);
			}

		}

		class Back implements ActionListener //�ڷΰ��� ��ư �������� �̺�Ʈ
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(random.isSelected())
				{            
					if(randomIndex>0)
					{
						randomIndex--;
						outPutWord(cbBox1,left,ran.get(randomIndex));
						outPutWord(cbBox2,right,ran.get(randomIndex));
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
				}



				else
				{
					if(currIndex>0)
					{
						currIndex--;
						outPutWord(cbBox1,left,seq.get(currIndex));
						outPutWord(cbBox2,right,seq.get(currIndex));
					}

					CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(seq.size()));
					Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
				}   
				if(comp.isSelected())
					comp.setSelected(false);
			}
		}

		class Check implements ItemListener //�ϱ�Ϸ� ��ư üũ�Ǿ����� �̺�Ʈ
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					if(random.isSelected())
					{
						seq.removeElement(ran.get(randomIndex));
						ran.removeElementAt(randomIndex);
						randomIndex--;
					}
					else
					{
						ran.removeElement(seq.get(currIndex));
						seq.removeElementAt(currIndex);
						currIndex--;
					}

				}

			}

		}

		void makeRandomArr() //���� ����ŭ ��ġ�� �ʴ� ���� �� ����
		{

			boolean check=true;

			for(int i=0; i<word.size();i++)
			{
				int rd=(int)(Math.random()*word.size());
				for(int j=0;j<i;j++)
				{            
					if(ran.get(j)==rd)
						check=false;
				}
				if(check==true)
					ran.add(i, rd);
				else
				{
					check=true;
					i--;
				}   
			}
		}

		void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
		{
			word=new Vector<String>();
			mean=new Vector<String>();
			same=new Vector<String>();
			ans=new Vector<String>();
			ran=new Vector<Integer>();

			StringTokenizer st;
			String str=null;
			try {
				in=new BufferedReader(new FileReader(Memorize.file.getPath()));
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
			makeRandomArr();
		}

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
		void outPutWord(JComboBox<String> cBox, JLabel la, int index) //�ܾ ���̺� �߰��ϴ� �޼ҵ�
		{
			switch((String)cBox.getSelectedItem())
			{
			case "�ܾ�":
				la.setText(word.get(index));
				break;
			case "��":
				la.setText(mean.get(index));
				break;
			case "���Ǿ�":
				la.setText(same.get(index));
				break;
			case "���Ǿ�":
				la.setText(ans.get(index));
				break;
			}

		}

		private class ComboBoxChange implements ActionListener //��¼��� �޺��ڽ����ú����̺�Ʈ
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(random.isSelected())
				{
					outPutWord(cbBox1,left,ran.get(randomIndex));
					outPutWord(cbBox2,right,ran.get(randomIndex));


				}
				else
				{
					outPutWord(cbBox1,left,currIndex);
					outPutWord(cbBox2,right,currIndex);

				}
			}
		}
}

class Memorize2 extends SetBackground
{
	//�������� �ϱ�������

	JLabel totalC,CompC,nonC,Index;
	JTextArea left, right;
	JRadioButton manual,auto,sequen,random,all,excep;
	JCheckBox comp;
	JLabel CreateWord1;

	Vector<String> word,ans; //�ܾ� �� ����� ����
	Vector<Integer> ran, seq;//���� , ���� �� ����� ����
	BufferedReader in; //���� �б��
	int currIndex=0; //������½� ���� �ε���
	//boolean[] finish; //�ϱ�Ϸ� üũ�� �迭
	int randomIndex=0;
	JTextArea ta;//�ڵ��ѱ�� ����� ���Է� (�ӽ�)
	Timer timer;//�ڵ��ѱ��� ������



	public Memorize2(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		totalC = new JLabel("�� ī�� ����");
		totalC.setBounds(198, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		CompC = new JLabel("�Ϸ� ����");
		CompC.setBounds(352, 8, 100, 20);
		CompC.setForeground(Color.white);
		CompC.setFont(new Font("����",Font.BOLD,11));
		this.add(CompC);

		nonC = new JLabel("�̾ϱ� ����");
		nonC.setBounds(485, 8, 100, 20);
		nonC.setForeground(Color.white);
		nonC.setFont(new Font("����",Font.BOLD,11));
		this.add(nonC);

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(630, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		//JRadioButton manual,auto,sequen,random,all,excep;
		ButtonGroup ma = new ButtonGroup();

		manual = new JRadioButton();
		manual.setBounds(110, 322, 30, 30);
		manual.setBorderPainted(false);
		manual.setFocusPainted(false);
		manual.setContentAreaFilled(false);
		ma.add(manual);
		this.add(manual);
		manual.setSelected(true); //�⺻ ����

		auto = new JRadioButton();
		auto.setBounds(110, 342, 30, 30);
		auto.setBorderPainted(false);
		auto.setFocusPainted(false);
		auto.setContentAreaFilled(false);
		ma.add(auto);
		this.add(auto);
		auto.addItemListener(new AutoNext());


		ButtonGroup ord = new ButtonGroup();

		sequen = new JRadioButton();
		sequen.setBounds(210, 322, 30, 30);
		sequen.setBorderPainted(false);
		sequen.setFocusPainted(false);
		sequen.setContentAreaFilled(false);
		ord.add(sequen);
		this.add(sequen);
		sequen.setSelected(true);// �⺻����

		random = new JRadioButton();
		random.setBounds(210, 342, 30, 30);
		random.setBorderPainted(false);
		random.setFocusPainted(false);
		random.setContentAreaFilled(false);
		ord.add(random);
		this.add(random);

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(330, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);
		all.addItemListener(new CheckWord());


		excep = new JRadioButton();
		excep.setBounds(330, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);
		excep.setSelected(true);// �⺻������ ����

		comp = new JCheckBox();
		comp.setBounds(650, 319, 20, 20);
		comp.setBorderPainted(false);
		comp.setFocusPainted(false);
		comp.setContentAreaFilled(false);
		this.add(comp);
		comp.addItemListener(new Check());



		left=new JTextArea("�������");
		left.setBounds(96, 125, 618, 37);
		left.setEditable(false);
		left.setLineWrap(true);
		left.setFont(new Font("Gothic",Font.BOLD,20));
		//this.add(left);
		JScrollPane leftSp = new JScrollPane(left);
		leftSp.setHorizontalScrollBarPolicy(leftSp.HORIZONTAL_SCROLLBAR_NEVER);
		leftSp.setVerticalScrollBarPolicy(leftSp.VERTICAL_SCROLLBAR_ALWAYS);
		leftSp.setBounds(96, 125, 618, 37);
		this.add(leftSp);

		right=new JTextArea("���������");
		right.setBounds(96, 165, 618, 120);
		right.setEditable(false);
		right.setLineWrap(true);
		right.setFont(new Font("Gothic",Font.BOLD,30));
		//this.add(right);
		JScrollPane rightSp = new JScrollPane(right);
		rightSp.setHorizontalScrollBarPolicy(rightSp.HORIZONTAL_SCROLLBAR_NEVER);
		rightSp.setVerticalScrollBarPolicy(rightSp.VERTICAL_SCROLLBAR_ALWAYS);
		rightSp.setBounds(96, 165, 618, 120);
		this.add(rightSp);

		JButton ntButton=new JButton(new ImageIcon("image/right_icon.png"));
		ntButton.setBorderPainted(false); // ��ư ��輱 ����
		ntButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		ntButton.setContentAreaFilled(false);//��ư���� ��� ����
		ntButton.setBounds(738, 195, 40, 40);
		this.add(ntButton);
		ntButton.addActionListener(new Next());

		JButton backButton=new JButton(new ImageIcon("image/left_icon.png"));
		backButton.setBorderPainted(false); // ��ư ��輱 ����
		backButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		backButton.setContentAreaFilled(false);//��ư���� ��� ����
		backButton.setBounds(23, 195, 40, 40);
		this.add(backButton);
		backButton.addActionListener(new Back());
		loadFileInVetor();//������ ���;ȿ� ����ְ�
		makeSeqArr(); //�������� �ε��� ����

		new MenuButton(this,new Memorize(new ImageIcon("image/backM.png")));


		ta=new JTextArea("3"); //�ڵ������ 
		ta.setBounds(650, 347, 50, 20);
		this.add(ta);

		totalC.setText(String.valueOf(word.size())); //�� ī�� ���� ǥ��
		left.setText(word.get(0));
		right.setText(ans.get(0));
	}

	class Timer extends Thread //�ڵ��ѱ��
	{
		int time;

		public Timer(int time)
		{
			this.time=time;

		}

		public void run()
		{
			while(true)
			{         


				if(random.isSelected())
				{

					outPutWord(left,ran.get(randomIndex));
					outPutWord(right,ran.get(randomIndex));

					if(randomIndex<ran.size()-1)
						randomIndex++;
					else if(randomIndex==ran.size())
					{
						//manual.setSelected(true);
						timer.interrupt();                     
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
				}
				else
				{
					outPutWord(left,seq.get(currIndex));
					outPutWord(right,seq.get(currIndex));
					if(currIndex<seq.size()-1)
						currIndex++;
					else if(currIndex==seq.size())
					{
						//manual.setSelected(true);
						timer.interrupt();                  
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
				}
				if(comp.isSelected())
					comp.setSelected(false);

				try 
				{
					if(timer.isAlive())
						sleep(time*1000);
				}   
				catch (InterruptedException e) 
				{
					return;
				}

			}
		}

	}


	void makeSeqArr() //������� �������ε����� ����� �迭�� �����.
	{
		seq=new Vector<Integer>();
		for(int i=0;i<word.size();i++)
			seq.add(i);
	}

	class AutoNext implements ItemListener //�ڵ��ѱ��
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb.isSelected())
			{
				timer=new Timer(Integer.parseInt(ta.getText()));
				timer.start();
			}
			else
			{            
				timer.interrupt();
			}
		}

	}

	class CheckWord implements ItemListener //��¹���������ư ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb.isSelected())
			{
				currIndex=0;
				randomIndex=0;
				makeSeqArr();
				makeRandomArr();
				comp.setEnabled(false);
			}
			else
				comp.setEnabled(true);

		}
	}
	class Random implements ItemListener //����������ư Ŭ�������� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==random)
			{
				//makeRandomArr();
				randomIndex=0;
			}
			else
			{
				currIndex=0;
			}
		}

	}
	class Next implements ActionListener //������ư �������� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(random.isSelected())
			{            
				if(randomIndex<ran.size()-1)
				{
					randomIndex++;
					outPutWord(left,ran.get(randomIndex));
					outPutWord(right,ran.get(randomIndex));
				}

				CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(ran.size()));
				Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
			}
			else
			{



				if(currIndex<seq.size()-1)
				{
					currIndex++;
					outPutWord(left,seq.get(currIndex));
					outPutWord(right,seq.get(currIndex));
				}

				CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(seq.size()));
				Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
			}
			if(comp.isSelected())
				comp.setSelected(false);
		}

	}

	class Back implements ActionListener //�ڷΰ��� ��ư �������� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(random.isSelected())
			{            
				if(randomIndex>0)
				{
					randomIndex--;
					outPutWord(left,ran.get(randomIndex));
					outPutWord(right,ran.get(randomIndex));
				}

				CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(ran.size()));
				Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
			}



			else
			{
				if(currIndex>0)
				{
					currIndex--;
					outPutWord(left,seq.get(currIndex));
					outPutWord(right,seq.get(currIndex));
				}

				CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(seq.size()));
				Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
			}   
			if(comp.isSelected())
				comp.setSelected(false);
		}
	}

	class Check implements ItemListener //�ϱ�Ϸ� ��ư üũ�Ǿ����� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			if(e.getStateChange()==ItemEvent.SELECTED)
			{
				if(random.isSelected())
				{
					seq.removeElement(ran.get(randomIndex));
					ran.removeElementAt(randomIndex);
					randomIndex--;//�߰�
				}
				else
				{
					ran.removeElement(seq.get(currIndex));
					seq.removeElementAt(currIndex);
					currIndex--;//�߰�
				}

			}

		}

	}

	void makeRandomArr() //���� ����ŭ ��ġ�� �ʴ� ���� �� ����
	{

		boolean check=true;

		for(int i=0; i<word.size();i++)
		{
			int rd=(int)(Math.random()*word.size());
			for(int j=0;j<i;j++)
			{            
				if(ran.get(j)==rd)
					check=false;
			}
			if(check==true)
				ran.add(i, rd);
			else
			{
				check=true;
				i--;
			}   
		}
	}

	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		ans=new Vector<String>();
		ran=new Vector<Integer>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Memorize.file.getPath()));
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

		makeRandomArr();
	}

	void outPutWord(JTextArea la, int index) //�ܾ ���̺� �߰��ϴ� �޼ҵ�
	{
		if(la==left)
		{
			la.setText(word.get(index));
		}
		else
		{			
			la.setText(ans.get(index));
		}

	}
}

class Memorize3 extends SetBackground
{
	//�������� �ϱ�������

	JLabel totalC,CompC,nonC,Index; 
	JLabel CreateWord1;
	JTextArea left;
	String wordbook;
	JButton AddButton;
	JRadioButton manual,auto,sequen,random,all,excep;
	JCheckBox comp;
	JComboBox<String> cbBox1, cbBox2;
	Vector<String> word,ans; //�ܾ� �� ����� ����
	Vector<Integer> ran, seq;//���� , ���� �� ����� ����
	BufferedReader in; //���� �б��
	int currIndex=0; //������½� ���� �ε���
	//boolean[] finish; //�ϱ�Ϸ� üũ�� �迭
	int randomIndex=0;
	JTextArea ta;//�ڵ��ѱ�� ����� ���Է� (�ӽ�)
	Timer timer;//�ڵ��ѱ��� ������



	public Memorize3(ImageIcon ico){
		super(ico);

		setLayout(null);
		setBounds(0, 0, 800, 500);

		this.setSize(820,540);
		this.setVisible(true);

		CreateWord1 = new JLabel();
		CreateWord1.setBounds(220, 70, 200, 50);
		this.add(CreateWord1);

		totalC = new JLabel("�� ī�� ����");
		totalC.setBounds(198, 8, 100, 20);
		totalC.setForeground(Color.white);
		totalC.setFont(new Font("����",Font.BOLD,11));
		this.add(totalC);

		CompC = new JLabel("�Ϸ� ����");
		CompC.setBounds(352, 8, 100, 20);
		CompC.setForeground(Color.white);
		CompC.setFont(new Font("����",Font.BOLD,11));
		this.add(CompC);

		nonC = new JLabel("�̾ϱ� ����");
		nonC.setBounds(485, 8, 100, 20);
		nonC.setForeground(Color.white);
		nonC.setFont(new Font("����",Font.BOLD,11));
		this.add(nonC);

		Index = new JLabel("�ε���ǥ��");
		Index.setBounds(630, 8, 100, 20);
		Index.setForeground(Color.white);
		Index.setFont(new Font("����",Font.BOLD,11));
		this.add(Index);

		//JRadioButton manual,auto,sequen,random,all,excep;
		ButtonGroup ma = new ButtonGroup();

		manual = new JRadioButton();
		manual.setBounds(110, 322, 30, 30);
		manual.setBorderPainted(false);
		manual.setFocusPainted(false);
		manual.setContentAreaFilled(false);
		ma.add(manual);
		this.add(manual);
		manual.setSelected(true); //�⺻ ����

		auto = new JRadioButton();
		auto.setBounds(110, 342, 30, 30);
		auto.setBorderPainted(false);
		auto.setFocusPainted(false);
		auto.setContentAreaFilled(false);
		ma.add(auto);
		this.add(auto);
		auto.addItemListener(new AutoNext());


		ButtonGroup ord = new ButtonGroup();

		sequen = new JRadioButton();
		sequen.setBounds(210, 322, 30, 30);
		sequen.setBorderPainted(false);
		sequen.setFocusPainted(false);
		sequen.setContentAreaFilled(false);
		ord.add(sequen);
		this.add(sequen);
		sequen.setSelected(true);// �⺻����

		random = new JRadioButton();
		random.setBounds(210, 342, 30, 30);
		random.setBorderPainted(false);
		random.setFocusPainted(false);
		random.setContentAreaFilled(false);
		ord.add(random);
		this.add(random);

		ButtonGroup rng = new ButtonGroup();

		all = new JRadioButton();
		all.setBounds(330, 322, 30, 30);
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		all.setContentAreaFilled(false);
		rng.add(all);
		this.add(all);
		all.addItemListener(new CheckWord());


		excep = new JRadioButton();
		excep.setBounds(330, 342, 30, 30);
		excep.setBorderPainted(false);
		excep.setFocusPainted(false);
		excep.setContentAreaFilled(false);
		rng.add(excep);
		this.add(excep);
		excep.setSelected(true);// �⺻������ ����

		comp = new JCheckBox();
		comp.setBounds(650, 319, 20, 20);
		comp.setBorderPainted(false);
		comp.setFocusPainted(false);
		comp.setContentAreaFilled(false);
		this.add(comp);
		comp.addItemListener(new Check());


		left=new JTextArea("�������");
		left.setBounds(99, 120, 293, 167);
		left.setEditable(false);
		left.setLineWrap(true);
		left.setFont(new Font("Gothic",Font.BOLD,30));
		//this.add(left);
		JScrollPane leftSp=new JScrollPane(left);
		leftSp.setHorizontalScrollBarPolicy(leftSp.HORIZONTAL_SCROLLBAR_NEVER);
		leftSp.setVerticalScrollBarPolicy(leftSp.VERTICAL_SCROLLBAR_ALWAYS);
		leftSp.setBounds(99, 120, 618, 167);
		this.add(leftSp);

	

		JButton ntButton=new JButton(new ImageIcon("image/right_icon.png"));
		ntButton.setBorderPainted(false); // ��ư ��輱 ����
		ntButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		ntButton.setContentAreaFilled(false);//��ư���� ��� ����
		ntButton.setBounds(738, 195, 40, 40);
		this.add(ntButton);
		ntButton.addActionListener(new Next());

		JButton backButton=new JButton(new ImageIcon("image/left_icon.png"));
		backButton.setBorderPainted(false); // ��ư ��輱 ����
		backButton.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		backButton.setContentAreaFilled(false);//��ư���� ��� ����
		backButton.setBounds(23, 195, 40, 40);
		this.add(backButton);
		backButton.addActionListener(new Back());
		loadFileInVetor();//������ ���;ȿ� ����ְ�
		makeSeqArr(); //�������� �ε��� ����

		new MenuButton(this,new Memorize(new ImageIcon("image/backM.png")));


		ta=new JTextArea("3"); //�ڵ������ 
		ta.setBounds(650, 347, 50, 20);
		this.add(ta);

		totalC.setText(String.valueOf(word.size())); //�� ī�� ���� ǥ��
		outPutWord(left,currIndex);

	}

	class Timer extends Thread //�ڵ��ѱ��
	{
		int time;

		public Timer(int time)
		{
			this.time=time;

		}

		public void run()
		{
			while(true)
			{         


				if(random.isSelected())
				{

					outPutWord(left,ran.get(randomIndex));
				
					if(randomIndex<ran.size()-1)
						randomIndex++;
					else if(randomIndex==ran.size())
					{
						
						timer.interrupt();                     
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
				}
				else
				{
					outPutWord(left,seq.get(currIndex));
				
					if(currIndex<seq.size()-1)
						currIndex++;
					else if(currIndex==seq.size())
					{
						timer.interrupt();                  
					}

					CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
					nonC.setText(String.valueOf(ran.size()));
					Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
				}
				if(comp.isSelected())
					comp.setSelected(false);

				try 
				{
					if(timer.isAlive())
						sleep(time*1000);
				}   
				catch (InterruptedException e) 
				{
					return;
				}

			}
		}

	}


	void makeSeqArr() //������� �������ε����� ����� �迭�� �����.
	{
		seq=new Vector<Integer>();
		for(int i=0;i<word.size();i++)
			seq.add(i);
	}

	class AutoNext implements ItemListener //�ڵ��ѱ��
	{
		public void itemStateChanged(ItemEvent e)
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb.isSelected())
			{
				timer=new Timer(Integer.parseInt(ta.getText()));
				timer.start();
			}
			else
			{            
				timer.interrupt();
			}
		}

	}

	class CheckWord implements ItemListener //��¹���������ư ���� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb.isSelected())
			{
				currIndex=0;
				randomIndex=0;
				makeSeqArr();
				makeRandomArr();
				comp.setEnabled(false);
			}
			else
				comp.setEnabled(true);

		}
	}
	class Random implements ItemListener //����������ư Ŭ�������� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			JRadioButton rb=(JRadioButton)e.getSource();

			if(rb==random)
			{				
				randomIndex=0;
			}
			else
			{
				currIndex=0;
			}
		}

	}
	class Next implements ActionListener //������ư �������� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(random.isSelected())
			{            
				if(randomIndex<ran.size()-1)
				{
					randomIndex++;
					outPutWord(left,ran.get(randomIndex));
					//outPutWord(right,ran.get(randomIndex));
				}

				CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(ran.size()));
				Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
			}
			else
			{



				if(currIndex<seq.size()-1)
				{
					currIndex++;
					outPutWord(left,seq.get(currIndex));
					//outPutWord(right,seq.get(currIndex));
				}

				CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(seq.size()));
				Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
			}
			if(comp.isSelected())
				comp.setSelected(false);
		}

	}

	class Back implements ActionListener //�ڷΰ��� ��ư �������� �̺�Ʈ
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(random.isSelected())
			{            
				if(randomIndex>0)
				{
					randomIndex--;
					outPutWord(left,ran.get(randomIndex));
					//outPutWord(right,ran.get(randomIndex));
				}

				CompC.setText(String.valueOf(word.size()-ran.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(ran.size()));
				Index.setText((randomIndex+1)+" of "+String.valueOf(ran.size()));
			}



			else
			{
				if(currIndex>0)
				{
					currIndex--;
					outPutWord(left,seq.get(currIndex));
					//outPutWord(right,seq.get(currIndex));
				}

				CompC.setText(String.valueOf(word.size()-seq.size())); //�Ϸ� ����ǥ��
				nonC.setText(String.valueOf(seq.size()));
				Index.setText((currIndex+1)+" of "+String.valueOf(seq.size()));
			}   
			if(comp.isSelected())
				comp.setSelected(false);
		}
	}

	class Check implements ItemListener //�ϱ�Ϸ� ��ư üũ�Ǿ����� �̺�Ʈ
	{
		public void itemStateChanged(ItemEvent e) 
		{
			if(e.getStateChange()==ItemEvent.SELECTED)
			{
				if(random.isSelected())
				{
					seq.removeElement(ran.get(randomIndex));
					ran.removeElementAt(randomIndex);
					randomIndex--;//�߰�
				}
				else
				{
					ran.removeElement(seq.get(currIndex));
					seq.removeElementAt(currIndex);
					currIndex--;//�߰�
				}

			}

		}

	}

	void makeRandomArr() //���� ����ŭ ��ġ�� �ʴ� ���� �� ����
	{

		boolean check=true;

		for(int i=0; i<word.size();i++)
		{
			int rd=(int)(Math.random()*word.size());
			for(int j=0;j<i;j++)
			{            
				if(ran.get(j)==rd)
					check=false;
			}
			if(check==true)
				ran.add(i, rd);
			else
			{
				check=true;
				i--;
			}   
		}
	}

	void loadFileInVetor() //������ �о�ͼ� ���Ϳ� ����
	{
		word=new Vector<String>();
		ans=new Vector<String>();
		ran=new Vector<Integer>();

		StringTokenizer st;
		String str=null;
		try {
			in=new BufferedReader(new FileReader(Memorize.file.getPath()));
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

		makeRandomArr();
	}

	void outPutWord(Object la, int index) //�ܾ ���̺� �߰��ϴ� �޼ҵ�
	{
		
			JTextArea ta=(JTextArea)la;
			StringBuilder sb=new StringBuilder(word.get(index));
			sb.insert(sb.indexOf("<")+1, ans.get(index));
			sb.deleteCharAt(sb.indexOf("\t"));
			ta.setText(sb.toString());
	
	}


}