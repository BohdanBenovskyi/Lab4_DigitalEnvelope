package com.benovskyi.bohdan.digital.envelope;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {

	/*
	private JButton button = new JButton("Press");
	private JTextField input = new JTextField("", 5);
	private JLabel label = new JLabel("Input:");
	private JRadioButton radio1 = new JRadioButton("Select this");
	private JRadioButton radio2 = new JRadioButton("Select that");
	private JCheckBox check = new JCheckBox("Check", false);
	*/
	
	private JLabel lblMsg = new JLabel("Введіть повідомлення:");
	private JLabel lblEncrKey = new JLabel("Зашифрований ключ:");
	private JLabel lblEncrMsg = new JLabel("Зашифроване повідомлення:");
	private JLabel lblDS = new JLabel("Цифровий підпис:");
	private JLabel lblHash1 = new JLabel("Хеш 1:");
	private JLabel lblHash2 = new JLabel("Хеш 2:");
	
	private JTextField txtMsg = new JTextField("");
	private JTextField txtEncrKey = new JTextField("");
	private JTextField txtEncrMsg = new JTextField("");
	private JTextField txtDS = new JTextField("");
	private JTextField txtMsgDecrypted = new JTextField("");
	private JTextField txtHash1 = new JTextField("");
	private JTextField txtHash2 = new JTextField("");
	
	private JButton btnGenKey = new JButton("Згенерувати ключ");
	private JButton btnBeginEncrypt = new JButton("Почати шифрування");
	private JButton btnBeginDecrypt = new JButton("Почати дешифрування");
	
	
	public Main() {
	    super("Лабораторна №4 - цифровий конверт");
	    this.setBounds(100,100,600,250);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    Container container = this.getContentPane();
	    container.setLayout(new GridLayout(8,2));
	    container.add(lblMsg);
	    container.add(txtMsg);

	    container.add(btnGenKey);
	    container.add(btnBeginEncrypt);
	    
	    container.add(lblEncrKey);
	    container.add(txtEncrKey);
	    
	    container.add(lblEncrMsg);
	    container.add(txtEncrMsg);
	    
	    container.add(lblDS);
	    container.add(txtDS);
	    
	    container.add(btnBeginDecrypt);
	    container.add(txtMsgDecrypted);
	    
	    container.add(lblHash1);
	    container.add(txtHash1);
	    
	    container.add(lblHash2);
	    container.add(txtHash2);
	}
	
	public static void main(String[] args) {
		Main app = new Main();
		app.setVisible(true);
	}

}
