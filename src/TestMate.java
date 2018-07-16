
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



//--------------------------------------------------------���� ���� �Լ�
class StartMain extends JFrame {
	static PanelMain panelMain;

	public StartMain(){
		this.setTitle("TestMate - �ϱ� �׽�Ʈ ���α׷�");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelMain=new PanelMain(new ImageIcon("image/back.png"));
		//scrollPane=new JScrollPane(panelMain);
		this.setContentPane(panelMain);
		this.setSize(820,540);
		this.setVisible(true);
	}

	//--------------------------------------------------------��ư Ŭ�� ������


}

//--------------------------------------------------------------------------
class PanelMain extends SetBackground
{
	JButton menu1,menu2,menu3, howTo;

	public PanelMain(ImageIcon ico)
	{
		super(ico);


		setLayout(null);
		setBounds(0, 0, 800, 500);


		howTo=new JButton(new ImageIcon("image/howTo.png")); 
		howTo.setBorderPainted(false); 
		howTo.setFocusPainted(false); 
		howTo.setContentAreaFilled(false);
		add(howTo);
		howTo.setBounds(252, 127, 275, 45);
		howTo.addMouseListener(new Button());
		//����-�޴� ��ư ������ ����+������ ����

		menu1 = new JButton(new ImageIcon("image/MenuButton1_n.png")); 
		menu2 = new JButton(new ImageIcon("image/MenuButton2_n.png")); 
		menu3 = new JButton(new ImageIcon("image/MenuButton3_n.png")); 

		menu1.setPressedIcon(new ImageIcon("image/MenuButton1_y.png"));
		menu2.setPressedIcon(new ImageIcon("image/MenuButton2_y.png"));
		menu3.setPressedIcon(new ImageIcon("image/MenuButton3_y.png"));
		menu1.setRolloverIcon(new ImageIcon("image/MenuButton1_y.png"));
		menu2.setRolloverIcon(new ImageIcon("image/MenuButton2_y.png"));
		menu3.setRolloverIcon(new ImageIcon("image/MenuButton3_y.png"));

		//��ư ���� & �����ϱ�
		menu1.setBorderPainted(false); // ��ư ��輱 ����
		menu1.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		menu1.setContentAreaFilled(false);//��ư���� ��� ����
		add(menu1);
		menu1.setBounds(213,188,118,175);
		menu1.addMouseListener(new Button());

		menu2.setBorderPainted(false); // ��ư ��輱 ����
		menu2.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		menu2.setContentAreaFilled(false);//��ư���� ��� ����
		add(menu2);
		menu2.setBounds(335,188,118,175);
		menu2.addMouseListener(new Button());

		menu3.setBorderPainted(false); // ��ư ��輱 ����
		menu3.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		menu3.setContentAreaFilled(false);//��ư���� ��� ����
		add(menu3);
		menu3.setBounds(454,188,118,175);
		menu3.addMouseListener(new Button());

		//�ϴ� ������ ���� �Լ� ȣ��
		new MenuButton(this,this);
	}

	private class Button implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			if(btn==menu1)
			{
				System.out.println("��� ù�������� �̵�");
				Create c=new Create(new ImageIcon("image/backCM.png"));
				TestMate.stMain.setContentPane(c);
			}else if(btn==menu2)
			{
				System.out.println("�ϱ� ù�������� �̵�");
				Memorize m=new Memorize(new ImageIcon("image/backM.png"));
				TestMate.stMain.setContentPane(m);
			}else if(btn==menu3)
			{
				System.out.println("�׽�Ʈ ù�������� �̵�");
				Test t=new Test(new ImageIcon("image/backT.png"));
				//  m1.CreateWord1.setText(fileName);
				TestMate.stMain.setContentPane(t);
			}
		}

		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }
	}
}


//--------------------------------------------------------��� ���� �Լ�
class SetBackground extends JPanel 
{
	Image backgr;

	SetBackground(ImageIcon ico)
	{
		super();
		backgr =  ico.getImage();

	}
	public void paintComponent(Graphics g){
		g.drawImage(backgr, 0, 0, this);//��� ȭ�鿡 ��� 
		setOpaque(false); //�׸��� ǥ���ϰ� ����, �����ϰ� ����
		super.paintComponent(g);
	}

}

//--------------------------------------------------------�ϴ� ������ ���� �Լ�
class MenuButton
{
	JButton prog_info,left_arrow,right_arrow,home;
	static JPanel back;

	MenuButton(JPanel frm,JPanel frm2)
	{

		back=frm2;
		prog_info = new JButton(new ImageIcon("image/prog_info.png")); 
		prog_info.setBorderPainted(false); // ��ư ��輱 ����
		prog_info.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		prog_info.setContentAreaFilled(false);//��ư���� ��� ����
		frm.add(prog_info);
		prog_info.setBounds(15,450,180,40);

		home = new JButton(new ImageIcon("image/home_icon.png")); 
		home.setBorderPainted(false); // ��ư ��輱 ����
		home.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		home.setContentAreaFilled(false);//��ư���� ��� ����
		frm.add(home);
		home.setBounds(680,450,40,40);
		home.addMouseListener(new FootButton());

		left_arrow = new JButton(new ImageIcon("image/left_icon.png")); 
		left_arrow.setBorderPainted(false); // ��ư ��輱 ����
		left_arrow.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		left_arrow.setContentAreaFilled(false);//��ư���� ��� ����
		frm.add(left_arrow);
		left_arrow.setBounds(620,450,40,40);
		if(!(frm==StartMain.panelMain))
			left_arrow.addMouseListener(new FootButton());

		right_arrow = new JButton(new ImageIcon("image/right_icon.png")); 
		right_arrow.setBorderPainted(false); // ��ư ��輱 ����
		right_arrow.setFocusPainted(false); //��Ŀ��(�����ߴ� ��ư ǥ��) ����
		right_arrow.setContentAreaFilled(false);//��ư���� ��� ����
		frm.add(right_arrow);
		right_arrow.setBounds(740,450,40,40);
		right_arrow.setEnabled(false);
		right_arrow.addMouseListener(new FootButton());

	}

	class FootButton implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JButton btn = (JButton)e.getSource();
			if(btn==home){
				TestMate.stMain.setContentPane(StartMain.panelMain);
			}else if(btn==left_arrow){

				right_arrow.setEnabled(true);
				TestMate.stMain.setContentPane(back);
			}
			else if(btn==right_arrow) {
				right_arrow.setEnabled(false);
				TestMate.stMain.setContentPane(back);
			}

		}
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }

	}
}



public class TestMate {
	public static StartMain stMain;
	public static Create CrMain;
	public static void main(String[] args) {
		stMain=new StartMain();

	}

}