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
	MainPanel mainPanel = new MainPanel();// ����panel

	// private MainPanel mp;
	public MainFrame() {

		this.setTitle("��������Ϸ");

		this.setLayout(new BorderLayout());// ��MainFrame�Ĳ�����Ϊ�߿�ʽ������Ų˵������������
		JPanel jpMenu = new JPanel();// �˵����
		jpMenu.setLayout(new BorderLayout());

		JMenuBar jmb = new JMenuBar();
		JMenu menu = new JMenu("��  ��");
		JMenuItem jmi = new JMenuItem("��ʼ");
		JMenuItem jmi1 = new JMenuItem("�˳�");
		menu.add(jmi);
		menu.add(jmi1);

		// JMenu net = new JMenu("��������ս");
		// JMenuItem jmi2 = new JMenuItem("��������");
		// net.add(jmi2);

		JMenu about = new JMenu("����");
		JMenuItem jmi3 = new JMenuItem("�汾��Ϣ");
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
		this.add(mainPanel);// ��֪Ϊ���������BorderLayout.SOUTH���panel����ʾ������
		this.setSize(490, 540);
		this.setLocationRelativeTo(this);
		this.setVisible(true);

	}

	public void over() {
		if (!gd.start && !mainPanel.winflag) {
			if (JOptionPane.showConfirmDialog(this, "��Ϸ���ڽ����У��Ƿ��˳���", "��Ϸ���ڽ�����",
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
			if (JOptionPane.showConfirmDialog(this, "��Ϸ���ڽ����У��Ƿ����¿�ʼ��",
					"��Ϸ���ڽ�����", JOptionPane.YES_NO_OPTION) == 0) {
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
