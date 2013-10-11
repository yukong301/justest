package com.lsc.data;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GobangGame {
	public static void main(String[] args) {
		GameF game = new GameF();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.show();
	}
}

class GameF extends JFrame {
	public GameF() {
		Container contentPane = getContentPane();
		final Panel panel = new Panel();
		// contentPane.setBackground(new Color(255, 182, 147));
		contentPane.add(panel);
		setSize(560, 560);
		setResizable(false);
		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("ѡ��");
		JMenuItem menuStart = new JMenuItem("��ʼ��Ϸ");
		menuStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.ResetGame();
				panel.repaint();
			}
		});
		JMenuItem menuExit = new JMenuItem("�˳�");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuBar.add(menu);
		menu.add(menuStart);
		menu.add(menuExit);
		// menuButton

		this.setJMenuBar(menuBar);
	}
}

class Panel extends JPanel {
	// private URL blackImgURL = GobangGame.class.getResource("black.gif");
	// private ImageIcon black = new ImageIcon(blackImgURL);
	// private URL whiteImgURL = GobangGame.class.getResource("white.gif");
	// private ImageIcon white = new ImageIcon(whiteImgURL);
	// private URL currentImgURL = GobangGame.class.getResource("current.gif");
	// private ImageIcon current = new ImageIcon(currentImgURL);
	private int i, j, k, m, n, icount;
	private int[][] board = new int[15][15];
	private boolean[][][] ptable = new boolean[15][15][572];
	private boolean[][][] ctable = new boolean[15][15][572];
	private int[][] cgrades = new int[15][15];
	private int[][] pgrades = new int[15][15];
	private int cgrade, pgrade;
	private int[][] win = new int[2][572];
	private int oldx, oldy;
	private int bout = 1;
	private int pcount, ccount;
	private boolean player, computer, over, pwin, cwin, tie, start;
	private int mat, nat, mde, nde;

	public Panel() {
		addMouseListener(new Xiazi());
		this.ResetGame();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < 15; i++)
			g.setColor(Color.blue);
		for (int j = 0; j < 15; j++) {
			g.drawLine(50, 50 + j * 30, 470, 50 + j * 30);
		}
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				g.setColor(Color.blue);
				g.drawLine(50 + j * 30, 50, 50 + j * 30, 470);
			}
		for (int i = 0; i < 15; i++) {
			String number = Integer.toString(i);
			g.setColor(Color.blue);
			g.drawString(number, 46 + 30 * i, 45);
		}
		for (int i = 1; i < 15; i++) {
			String number = Integer.toString(i);
			g.setColor(Color.blue);
			g.drawString(number, 33, 53 + 30 * i);
		}
		paintComponent(g);
	}

	class Xiazi extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (!over) {
				oldx = e.getX();
				oldy = e.getY();
				mouseClick();
				repaint();
			}
		}
	}

	// ��Ϸ��ʼ��
	public void ResetGame() {
		// ��ʼ������
		for (i = 0; i < 15; i++)
			for (j = 0; j < 15; j++) {
				this.pgrades[i][j] = 0;
				this.cgrades[i][j] = 0;
				this.board[i][j] = 2;
			}
		// �������е������ӿ��������Ȩֵ
		// ��
		for (i = 0; i < 15; i++)
			for (j = 0; j < 11; j++) {
				for (k = 0; k < 5; k++) {
					this.ptable[j + k][i][icount] = true;
					this.ctable[j + k][i][icount] = true;
				}
				icount++;
			}
		// ��
		for (i = 0; i < 15; i++)
			for (j = 0; j < 11; j++) {
				for (k = 0; k < 5; k++) {
					this.ptable[i][j + k][icount] = true;
					this.ctable[i][j + k][icount] = true;
				}
				icount++;
			}
		// ��б
		for (i = 0; i < 11; i++)
			for (j = 0; j < 11; j++) {
				for (k = 0; k < 5; k++) {
					this.ptable[j + k][i + k][icount] = true;
					this.ctable[j + k][i + k][icount] = true;
				}
				icount++;
			}
		// ��б
		// for (i = 0; i < 11; i++)
		// for (j = 15; j >= 4; j--) {
		// for (k = 0; k < 5; k++) {
		// this.ptable[j - k][i + k][icount] = true;
		// this.ctable[j - k][i + k][icount] = true;
		// }
		// icount++;
		// }

		for (int x = 4; x < 15; x++) // �ζԽ��ߨu
		{
			for (int y = 0; y < 11; y++) {
				for (int k = 0; k < 5; k++) {
					this.ptable[x - k][y + k][icount] = true;
					this.ctable[x - k][y + k][icount] = true;
					// PTABLE[x - k][y + k][count] = true;
					// CTABLE[x - k][y + k][count] = true;
				}
				icount++;
			}
		}
		for (i = 0; i <= 1; i++)
			// ��ʼ�����Ӱ����ϵ�ÿ��Ȩֵ�ϵ�������
			for (j = 0; j < 572; j++)
				this.win[i][j] = 0;
		this.player = true;
		this.icount = 0;
		this.ccount = 0;
		this.pcount = 0;
		this.start = true;
		this.over = false;
		this.pwin = false;
		this.cwin = false;
		this.tie = false;
		this.bout = 1;
	}

	public void ComTurn() { // �ҳ����ԣ����ӣ�������ӵ�
		for (i = 0; i < 15; i++)
			// ���������ϵ���������
			for (j = 0; j < 15; j++) {
				this.pgrades[i][j] = 0; // ������ĺ��ӽ�����������
				if (this.board[i][j] == 2) // �ڻ�û�����ӵĵط�����
					for (k = 0; k < 572; k++)
						// ���������̿����ӵ��ϵĺ�������Ȩֵ��������������������ӵ������Ӧ������
						if (this.ptable[i][j][k]) {
							switch (this.win[0][k]) {
							case 1: // һ����
								this.pgrades[i][j] += 5;
								break;
							case 2: // ������
								this.pgrades[i][j] += 50;
								break;
							case 3: // ������
								this.pgrades[i][j] += 180;
								break;
							case 4: // ������
								this.pgrades[i][j] += 400;
								break;
							}
						}
				this.cgrades[i][j] = 0;// ������İ��ӵĽ�����������
				if (this.board[i][j] == 2) // �ڻ�û�����ӵĵط�����
					for (k = 0; k < 572; k++)
						// ���������̿����ӵ��ϵİ�������Ȩֵ��������������������ӵ������Ӧ������
						if (this.ctable[i][j][k]) {
							switch (this.win[1][k]) {
							case 1: // һ����
								this.cgrades[i][j] += 5;
								break;
							case 2: // ������
								this.cgrades[i][j] += 52;
								break;
							case 3: // ������
								this.cgrades[i][j] += 100;
								break;
							case 4: // ������
								this.cgrades[i][j] += 400;
								break;
							}
						}
			}
		if (this.start) { // ��ʼʱ������������
			if (this.board[4][4] == 2) {
				m = 4;
				n = 4;
			} else {
				m = 5;
				n = 5;
			}
			this.start = false;
		} else {
			for (i = 0; i < 15; i++)
				for (j = 0; j < 15; j++)
					if (this.board[i][j] == 2) { // �ҳ������Ͽ����ӵ�ĺ��Ӱ��ӵĸ������Ȩֵ���ҳ����Ե�������ӵ�
						if (this.cgrades[i][j] >= this.cgrade) {
							this.cgrade = this.cgrades[i][j];
							this.mat = i;
							this.nat = j;
						}
						if (this.pgrades[i][j] >= this.pgrade) {
							this.pgrade = this.pgrades[i][j];
							this.mde = i;
							this.nde = j;
						}
					}
			if (this.cgrade >= this.pgrade) { // ������ӵ�������ӵ��Ȩֵ�Ⱥ��ӵ�������ӵ�Ȩֵ������Ե�������ӵ�Ϊ���ӵ�������ӵ㣬�����෴
				m = mat;
				n = nat;
			} else {
				m = mde;
				n = nde;
			}
		}
		this.cgrade = 0;
		this.pgrade = 0;
		this.board[m][n] = 1; // ��������λ��
		ccount++;
		if ((ccount == 50) && (pcount == 50)) // ƽ���ж�
		{
			this.tie = true;
			this.over = true;
		}
		for (i = 0; i < 572; i++) {
			if (this.ctable[m][n][i] && this.win[1][i] != 7)
				this.win[1][i]++; // �����ӵ����������ӿ��ܵļ��ص�ǰ������
			if (this.ptable[m][n][i]) {
				this.ptable[m][n][i] = false;
				this.win[0][i] = 7;
			}
		}
		this.player = true; // ��������
		this.computer = false; // �������ӽ���
	}

	public void mouseClick() {
		if (!this.over)
			if (this.player) {
				if (this.oldx < 520 && this.oldy < 520) {
					int m1 = m, n1 = n;
					m = (oldx - 33) / 30;
					n = (oldy - 33) / 30;
					if (this.board[m][n] == 2) {
						this.bout++;
						this.board[m][n] = 0;
						pcount++;
						if ((ccount == 50) && (pcount == 50)) {
							this.tie = true;
							this.over = true;
						}
						for (i = 0; i < 572; i++) {
							if (this.ptable[m][n][i] && this.win[0][i] != 7)
								this.win[0][i]++; // �����ӵ����������ӿ��ܵļ��ص�ǰ������
							if (this.ctable[m][n][i]) {
								this.ctable[m][n][i] = false;
								this.win[1][i] = 7;
							}
						}
						this.player = false;
						this.computer = true;
					} else {
						m = m1;
						n = n1;
					}
				}
			}
	}

	public void updatePaint(Graphics g) {
		if (!this.over) { // ������ֵ�������
			if (this.computer)
				this.ComTurn(); // �õ�������ӵ�
			// ������ǰ�����ϵ�������������ж���Ӯ
			for (i = 0; i <= 1; i++)
				for (j = 0; j < 572; j++) {
					if (this.win[i][j] == 5)
						if (i == 0) { // ��Ӯ
							this.pwin = true;
							this.over = true; // ��Ϸ����
							break;
						} else {
							this.cwin = true; // ����Ӯ
							this.over = true;
							break;
						}
					if (this.over) // һ�����������˳����̱���
						break;
				}
			g.setFont(new Font("�����п�", 0, 20));
			g.setColor(Color.RED);
			// ������ǰ������������
			for (i = 0; i < 15; i++)
				for (j = 0; j < 15; j++) { // ���boardԪ��ֵΪ0��������괦Ϊ����
					if (this.board[i][j] == 0) {
						// g.drawImage(
						// black.getImage(),
						// i * 30 + 31,
						// j * 30 + 31,
						// black.getImage().getWidth(
						// black.getImageObserver()) - 3,
						// black.getImage().getHeight(
						// black.getImageObserver()) - 3, black
						// .getImageObserver());
						g.setColor(Color.black);
						g.fillOval(i * 30 + 31, j * 30 + 31, 29, 29);
					}
					// ���boardԪ��ֵΪ1��������괦Ϊ����
					if (this.board[i][j] == 1) {
						g.setColor(Color.white);
						g.fillOval(i * 30 + 31, j * 30 + 31, 29, 29);
						// g.drawImage(
						// white.getImage(),
						// i * 30 + 31,
						// j * 30 + 31,
						// white.getImage().getWidth(
						// white.getImageObserver()) - 3,
						// white.getImage().getHeight(
						// white.getImageObserver()) - 3, white
						// .getImageObserver());
					}
				}
			// �������ӣ����ԣ���ǰ�����ӣ����ڱ���
			if (this.board[m][n] != 2) {
				g.setColor(Color.gray);
				g.drawOval(m * 30 + 31, n * 30 + 31, 29, 29);
				g.setColor(Color.white);
				g.fillOval(m * 30 + 31, n * 30 + 31, 29, 29);
			}
			// g.drawImage(
			// current.getImage(),
			// m * 30 + 31,
			// n * 30 + 31,
			// current.getImage().getWidth(current.getImageObserver()) - 4,
			// current.getImage()
			// .getHeight(current.getImageObserver()) - 4,
			// current.getImageObserver());
			// �ж���Ӯ���
			// ��Ӯ

//			if (this.pwin)
//			// g.drawString("��̫�����ˣ�����һ�������¿�ʼ��Ϸ..", 20, 200);
//			{
//				JOptionPane.showMessageDialog(this, "��Ӯ��");
//				return;
//			}
//			// ����Ӯ
//			if (this.cwin) {
//				JOptionPane.showMessageDialog(this, "����Ӯ��");
//				return;
//			}
//			// ƽ��
//			if (this.tie)
//				g.drawString("����ʤ��!����һ�������¿�ʼ��Ϸ..", 80, 200);
//			g.dispose();
		}
	}
}