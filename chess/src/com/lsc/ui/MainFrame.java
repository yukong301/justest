package com.lsc.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lsc.data.GlobalData;

public class MainFrame extends JFrame {

	GlobalData gd = new GlobalData();
	MainPanel mainPanel = new MainPanel();// 棋盘panel

	// private MainPanel mp;
	public MainFrame() {

		this.setTitle("五子棋游戏");

		this.setLayout(new BorderLayout());// 把MainFrame的布局设为边框式，上面放菜单，下面放棋盘
		JPanel jpMenu = new JPanel();// 菜单面板
		jpMenu.setLayout(new BorderLayout());

		JMenuBar jmb = new JMenuBar();
		JMenu menu = new JMenu("菜  单");
		JMenuItem jmi = new JMenuItem("开始");
		JMenuItem jmi1 = new JMenuItem("退出");
		menu.add(jmi);
		menu.add(jmi1);

		// JMenu net = new JMenu("局域网对战");
		// JMenuItem jmi2 = new JMenuItem("连接主机");
		// net.add(jmi2);

		JMenu about = new JMenu("关于");
		JMenuItem jmi3 = new JMenuItem("版本信息");
		jmi1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				over();
			}
		});

		jmi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				re();
			}
		});
		about.add(jmi3);

		jmb.add(menu);
		// jmb.add(net);
		jmb.add(about);
		jpMenu.add(jmb, BorderLayout.WEST);

		this.add(jpMenu, BorderLayout.NORTH);
		this.add(mainPanel);// 不知为何如果加上BorderLayout.SOUTH这个panel就显示不出来
		this.setSize(490, 540);
		this.setLocationRelativeTo(this);
		this.setVisible(true);

	}

	public void over() {
		if (!gd.start && !mainPanel.winflag) {
			if (JOptionPane.showConfirmDialog(this, "游戏下在进行中，是否退出？", "游戏下在进行中",
					JOptionPane.YES_NO_OPTION) == 0) {
				System.exit(0);
			}
		} else if (gd.start) {
			System.exit(0);
		} else {
			return;
		}
	}

	public void re() {
		if (!gd.start && !mainPanel.winflag) {
			if (JOptionPane.showConfirmDialog(this, "游戏下在进行中，是否重新开始？",
					"游戏下在进行中", JOptionPane.YES_NO_OPTION) == 0) {
				GlobalData.initData();
				mainPanel.initData();
				mainPanel.repaint();
			}
		} 
			
		 else {
			 GlobalData.initData();
				mainPanel.initData();
				mainPanel.repaint();
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
