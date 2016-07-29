package br.com.gere.helper;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MySqlConection {

	public static Connection ConectarDB(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://10.107.134.70/contatos", "root", "root");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return con;
	}
}
