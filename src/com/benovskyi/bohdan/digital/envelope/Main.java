package com.benovskyi.bohdan.digital.envelope;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;

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
	
	private JButton btnGenKey = new JButton("Генерація сеансових ключів");
	private JButton btnBeginEncrypt = new JButton("Почати шифрування");
	private JButton btnBeginDecrypt = new JButton("Почати дешифрування");
	
	private int sessionKey = 0;
    private int [] encryptMessage;
    private byte[] cipherSessionKey;
    private String decrMessage;
    String decrKey;
    byte[] cipherHash;
	
	
	public Main() {
	    super("Лабораторна №4 - цифровий конверт");
	    this.setBounds(100,100,600,250);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    Container container = this.getContentPane();
	    container.setLayout(new GridLayout(8,2, 5, 5));
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
	    
	    ActionListener actionListener = new TestActionListener();
        
	    btnGenKey.addActionListener(actionListener);
	    btnBeginEncrypt.addActionListener(actionListener);
	    btnBeginDecrypt.addActionListener(actionListener);
	    
	}
	
	public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
             if(e.getActionCommand().equals("Генерація сеансових ключів")) {
            	 LFSRUtil lfsr = new LFSRUtil("01101000010", 8);
                 sessionKey = lfsr.generate(8);
             }
             if(e.getActionCommand().equals("Почати шифрування")) {
            	//generate public and private key using rsa
                 if (!RSAUtil.areKeysPresent()) {
                     RSAUtil.generateKey();
                 }

                 //get public key
                 ObjectInputStream inputStream = null;
                 try {
                     //encrypt session key
                     inputStream = new ObjectInputStream(new FileInputStream(RSAUtil.PUBLIC_KEY_FILE));
                     final PublicKey publicKey = (PublicKey) inputStream.readObject();
                     cipherSessionKey = RSAUtil.encrypt(Integer.toString(sessionKey), publicKey);
                     txtEncrKey.setText(String.valueOf(cipherSessionKey));
                 } catch (IOException | ClassNotFoundException e1) {
                     e1.printStackTrace();
                 }
                 
               //encrypt message
                 CAST128Util cast128 = new CAST128Util();
                 try {
                     encryptMessage = cast128.encoding(txtMsg.getText(), Integer.toString(sessionKey));
                     txtEncrMsg.setText(String.valueOf(encryptMessage));
                     //encryptedMessage.setText(CAST128Util.encodedMessageInString);
                 } catch (UnsupportedEncodingException e1) {
                     e1.printStackTrace();
                 }

                 //calculate hash
                 SHA1Util sha1 = new SHA1Util();
                 byte[] dataBuffer = (txtMsg.getText()).getBytes();
                 String digest = sha1.digest(dataBuffer);
                 txtHash1.setText(digest);

                 //encrypt digital signature
                 try {
                     inputStream = new ObjectInputStream(new FileInputStream(RSAUtil.PUBLIC_KEY_FILE));
                     final PublicKey publicKey = (PublicKey) inputStream.readObject();
                     cipherHash = RSAUtil.encrypt(digest, publicKey);
                     txtDS.setText(String.valueOf(cipherHash));
                 } catch (IOException | ClassNotFoundException e1) {
                     e1.printStackTrace();
                 }
             }
             if(e.getActionCommand().equals("Почати дешифрування")) {
            	 ObjectInputStream inputStream = null;

                 //decrypt session key
                 try {
                     inputStream = new ObjectInputStream(new FileInputStream(RSAUtil.PRIVATE_KEY_FILE));
                     final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
                     decrKey = RSAUtil.decrypt(cipherSessionKey, privateKey);

                 } catch (IOException | ClassNotFoundException e1) {
                     e1.printStackTrace();
                 }

                 //decrypt message
                 CAST128Util cast128 = new CAST128Util();
                 try {
                     decrMessage = cast128.decoding(encryptMessage ,decrKey);
                     txtMsgDecrypted.setText(decrMessage);
                 } catch (UnsupportedEncodingException e1) {
                     e1.printStackTrace();
                 }

                 //decrypt hash
                 try {
                     inputStream = new ObjectInputStream(new FileInputStream(RSAUtil.PRIVATE_KEY_FILE));
                     final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
                     String decrHash = RSAUtil.decrypt(cipherHash, privateKey);
                     txtHash2.setText(decrHash);

                 } catch (IOException | ClassNotFoundException e1) {
                     e1.printStackTrace();
                 }
             }
        }
   }
	
	public static void main(String[] args) {
		Main app = new Main();
		app.setVisible(true);
	}

}
