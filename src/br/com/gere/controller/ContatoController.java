package br.com.gere.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import br.com.gere.helper.MySqlConection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ContatoController implements Initializable{
	@FXML
	TextField txtNome, txtTelefone;

	@FXML
	Button btnInserir;

	@FXML
	ListView<String> lstView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnInserir.setOnAction(l-> Inserir());
		PreencherLista();
	}
	public void Inserir(){
		if(!txtNome.getText().isEmpty() && !txtTelefone.getText().isEmpty()){
			Connection con = MySqlConection.ConectarDB();

			String sql = "INSERT INTO contact (name, phone) value (?, ?)";

			PreparedStatement parametros;
			//anifyvpiauhvuiva7cgvoiahv
			//TODO: TA FODA EM
			try {
				parametros = con.prepareStatement(sql);
				parametros.setString(1, txtNome.getText());
				parametros.setString(2, txtTelefone.getText());
				parametros.executeUpdate();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreencherLista();
		}
	}
	public void PreencherLista(){
		Connection con = MySqlConection.ConectarDB();
		lstView.getItems().clear();
		String sql = "select * from contact;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				String contato = rs.getString("name")+"-"+rs.getString("phone");
				lstView.getItems().add(contato);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
