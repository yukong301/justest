package com.lsc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.AncestorListener;

import com.lsc.data.GlobalData;

public class MainPanel extends JPanel {
	private int x = 1, y = 1;
	private GlobalData gd = new GlobalData();
	private static int[][] chessArray = new int[15][15];// 15*15�����̣����ڻ����µ���,0���壬1�����壬2��û��
	// private static boolean white_flag = true, black_flag = true;
	private int type = 0;// ������
	public static boolean winflag = false;

	public MainPanel() {
		// super();
		// repaint();
		// this.setVisible(true);
		initData();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// ���м�Ϊ�жϵ������һ�㡣
				x = (e.getX() - 15) / 30;
				y = (e.getY() - 15) / 30;
				go(x, y);

			}
		});
		// this.setSize(400, 400);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		// System.out.println("------");
		// drawChess(g, x, y, 0);
	}

	public void initData() {
		x = 1;
		y = 1;
		type = 0;
		winflag = false;
		for (int i = 0; i < 15; i++) {// ��ʼ���������飬û��
			for (int j = 0; j < 15; j++) {
				chessArray[i][j] = 2;
			}
		}
	}

	// public void paint(Graphics g){
	// for(int i=0;i<15;i++){
	// for(int j=0;j<15;j++){//��15*15������
	// g.setColor(Color.red);
	// //g.drawRect(i, j, 20, 20);
	// g.drawLine(20, 20+(20*i), 20, 20+(20*i));
	// }
	// }
	// }
	public void draw(Graphics g) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {// ��15*15������
				g.setColor(Color.blue);
				// g.drawRect(i, j, 20, 20);
				g.drawLine(30 + (30 * i), 30, 30 + (30 * i), 450);// ����(y)��
				g.drawLine(30, 30 + (i * 30), 450, 30 + (i * 30));// ���ᣨx����
				g.drawString(i + "", 30 + (30 * i), 28);
				g.drawString(i + "", 15, 30 + (30 * i));

				if (i == 4 && j == 4 || i == 12 && j == 4 || i == 4 && j == 12
						|| i == 12 && j == 12) {
					g.setColor(Color.blue);
					g.fillOval(i * 30 - 3, j * 30 - 3, 6, 6);
				}

				int black_white = chessArray[i][j];// �����������飬�������Ӧ������
				drawChess(g, i, j, black_white);
			}
		}
	}

	public void drawChess(Graphics g, int x, int y, int black_white) {
		int x_tmp = 30 + 30 * x;
		int y_tmp = 30 + 30 * y;
		if (black_white == 0) {// ������
			g.setColor(Color.black);
			g.drawOval(x_tmp - 10, y_tmp - 10, 19, 19);
			g.fillOval(x_tmp - 10, y_tmp - 10, 19, 19);
			g.setColor(Color.black);
		} else if (black_white == 1) {// ������
			g.setColor(Color.black);
			g.drawOval(x_tmp - 10, y_tmp - 10, 19, 19);
			g.setColor(Color.white);
			g.fillOval(x_tmp - 10, y_tmp - 10, 19, 19);
		} else {

		}

	}

	public void go(int x, int y) {
		if (!winflag) {

			System.out.println(x + " " + y);
			if (chessArray[x][y] == 2) {// û����ſ�����
				if (type == 0) {

					chessArray[x][y] = 0;
					gd.pboardChange(x, y);
					GlobalData.M_BOARD[x][y] = 0;//
					GlobalData.T_BOARD[x][y] = 0;
					type++;// �����type ��Ϊ1����������
					for (int k = 0; k < 572; k++) {
						if (GlobalData.WIN[0][k] == 5) {
							winflag = true;
							repaint();
							JOptionPane.showMessageDialog(this, "��Ӯ��");
							return;
						}
					}
				}

				// chessArray[x][y] = 1;
				int i = gd.evaluateScore(1, chessArray);
				// System.out.println("score-->" + i);
				// gd.cboardChange(x, y);
				GlobalData.M_BOARD[x][y] = 1;
				GlobalData.T_BOARD[x][y] = 1;
				type--;

				for (int k = 0; k < 572; k++) {
					if (GlobalData.WIN[1][k] == 5) {
						winflag = true;
						repaint();
						JOptionPane.showMessageDialog(getParent(), "�㱻����Ű��");
						return;
					}
				}
				repaint();
			}
		} else {
			for (int k = 0; k < 572; k++) {
				if (GlobalData.WIN[0][k] == 5) {
					JOptionPane.showMessageDialog(getParent(), "Ҫ�������Ѿ�Ӯ��");

				} else if (GlobalData.WIN[1][k] == 5) {
					JOptionPane.showMessageDialog(getParent(),
							"APM�ٸ�Ҳû�ã��㱻����Ű��");
				}
			}
			// if (type == 1) {
			// chessArray[x][y] = 1;
			// System.out.println("��");
			// type = 0;
			// repaint();
			// }else
			// if (type == 0) {
			// chessArray[x][y] = 0;
			// System.out.println("��");
			// type = 1;
			// repaint();
			// }
		}
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		MainPanel mp = new MainPanel();
		// JButton jb = new JButton("test");
		// mp.add(jb);

		JPanel jp = new JPanel();
		jp.add(new JButton("test"));
		jf.setSize(490, 520);
		jf.setLayout(new BorderLayout());
		jf.add(mp);
		jf.add(jp, BorderLayout.NORTH);
		jf.setTitle("this tittle");
		jf.setVisible(true);
	}
}
