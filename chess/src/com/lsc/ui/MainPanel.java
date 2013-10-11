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
	private static int[][] chessArray = new int[15][15];// 15*15的棋盘，用于画已下的棋,0黑棋，1，白棋，2，没棋
	// private static boolean white_flag = true, black_flag = true;
	private int type = 0;// 黑先下
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
				// 以中间为判断点击了哪一点。
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
		for (int i = 0; i < 15; i++) {// 初始化棋盘数组，没棋
			for (int j = 0; j < 15; j++) {
				chessArray[i][j] = 2;
			}
		}
	}

	// public void paint(Graphics g){
	// for(int i=0;i<15;i++){
	// for(int j=0;j<15;j++){//画15*15的棋盘
	// g.setColor(Color.red);
	// //g.drawRect(i, j, 20, 20);
	// g.drawLine(20, 20+(20*i), 20, 20+(20*i));
	// }
	// }
	// }
	public void draw(Graphics g) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {// 画15*15的棋盘
				g.setColor(Color.blue);
				// g.drawRect(i, j, 20, 20);
				g.drawLine(30 + (30 * i), 30, 30 + (30 * i), 450);// 画竖(y)轴
				g.drawLine(30, 30 + (i * 30), 450, 30 + (i * 30));// 画横（x）轴
				g.drawString(i + "", 30 + (30 * i), 28);
				g.drawString(i + "", 15, 30 + (30 * i));

				if (i == 4 && j == 4 || i == 12 && j == 4 || i == 4 && j == 12
						|| i == 12 && j == 12) {
					g.setColor(Color.blue);
					g.fillOval(i * 30 - 3, j * 30 - 3, 6, 6);
				}

				int black_white = chessArray[i][j];// 遍历棋盘数组，画出棋对应的棋子
				drawChess(g, i, j, black_white);
			}
		}
	}

	public void drawChess(Graphics g, int x, int y, int black_white) {
		int x_tmp = 30 + 30 * x;
		int y_tmp = 30 + 30 * y;
		if (black_white == 0) {// 画黑棋
			g.setColor(Color.black);
			g.drawOval(x_tmp - 10, y_tmp - 10, 19, 19);
			g.fillOval(x_tmp - 10, y_tmp - 10, 19, 19);
			g.setColor(Color.black);
		} else if (black_white == 1) {// 画白棋
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
			if (chessArray[x][y] == 2) {// 没有棋才可以下
				if (type == 0) {

					chessArray[x][y] = 0;
					gd.pboardChange(x, y);
					GlobalData.M_BOARD[x][y] = 0;//
					GlobalData.T_BOARD[x][y] = 0;
					type++;// 下完后type 变为1，到白子下
					for (int k = 0; k < 572; k++) {
						if (GlobalData.WIN[0][k] == 5) {
							winflag = true;
							repaint();
							JOptionPane.showMessageDialog(this, "你赢了");
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
						JOptionPane.showMessageDialog(getParent(), "你被电脑虐了");
						return;
					}
				}
				repaint();
			}
		} else {
			for (int k = 0; k < 572; k++) {
				if (GlobalData.WIN[0][k] == 5) {
					JOptionPane.showMessageDialog(getParent(), "要定，你已经赢了");

				} else if (GlobalData.WIN[1][k] == 5) {
					JOptionPane.showMessageDialog(getParent(),
							"APM再高也没用，你被电脑虐了");
				}
			}
			// if (type == 1) {
			// chessArray[x][y] = 1;
			// System.out.println("白");
			// type = 0;
			// repaint();
			// }else
			// if (type == 0) {
			// chessArray[x][y] = 0;
			// System.out.println("黑");
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
