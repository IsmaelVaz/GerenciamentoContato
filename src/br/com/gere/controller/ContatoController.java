package br.com.gere.controller;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.gere.helper.MySqlConection;
import br.com.gere.model.GerenciamentoModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ContatoController implements Initializable{
	@FXML
	TextField txtNome, txtTelefone;

	@FXML
	Button btnInserir;

	@FXML
	ListView<GerenciamentoModel> lstView;

	static int AUXID;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnInserir.setOnAction(l-> Inserir());

		/*lstView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			PrepararEdt();
		});*/
		lstView.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	Deletar();
		        	txtNome.setText("");
					txtTelefone.setText("");
					btnInserir.setText("Enviar");
		        }else if(click.getClickCount() == 1){
		        	PrepararEdt();
		        }
		    }
		});
		PreencherLista();
	}
	public void Deletar(){
		Connection con= MySqlConection.ConectarDB();
		GerenciamentoModel itemSelected = lstView.getSelectionModel().getSelectedItem();

		boolean retorno = GerenciamentoModel.Deletar(itemSelected);
		if(retorno){
			PreencherLista();
			txtNome.setText("");
			txtTelefone.setText("");
		}else{
			JOptionPane.showMessageDialog(null, "Erro ao Deletar!!");
		}
		PreencherLista();
		txtNome.setText("");
		txtTelefone.setText("");
	}
	public void Inserir(){
		Connection con= MySqlConection.ConectarDB();
		if(!txtNome.getText().isEmpty() && !txtTelefone.getText().isEmpty()){
			if(btnInserir.getText().equals("Enviar")){
				GerenciamentoModel gere = new GerenciamentoModel();
				gere.setNome(txtNome.getText());
				gere.setTelefone(txtTelefone.getText());

				boolean retorno = GerenciamentoModel.Inserir(gere);
				if(retorno){
					PreencherLista();
					txtNome.setText("");
					txtTelefone.setText("");
				}else{
					JOptionPane.showMessageDialog(null, "Erro ao inserir!!");
				}

			}else if(btnInserir.getText().equals("Atualizar")){
				GerenciamentoModel gere = new GerenciamentoModel();
				gere.setNome(txtNome.getText());
				gere.setTelefone(txtTelefone.getText());
				gere.setId(AUXID);

				boolean retorno = GerenciamentoModel.Atualizar(gere);
				if(retorno){
					PreencherLista();
					txtNome.setText("");
					txtTelefone.setText("");
				}else{
					JOptionPane.showMessageDialog(null, "Erro ao Atualizar!!");
				}
				PreencherLista();
				txtNome.setText("");
				txtTelefone.setText("");
				btnInserir.setText("Enviar");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Preencha o nome e o telefone!!");
		}
	}
	public void PrepararEdt(){
		GerenciamentoModel itemSelected = lstView.getSelectionModel().getSelectedItem();
		if(itemSelected!=null){
			GerenciamentoModel newGere = GerenciamentoModel.PreparaEdt(itemSelected);
			txtNome.setText(newGere.getNome());
			txtTelefone.setText(newGere.getTelefone());
			AUXID = newGere.getId();
			btnInserir.setText("Atualizar");
		}
	}
	public void PreencherLista(){
		lstView.getItems().clear();
		if(GerenciamentoModel.Select()!=null){
			lstView.getItems().addAll(GerenciamentoModel.Select());
		}
	}
}
