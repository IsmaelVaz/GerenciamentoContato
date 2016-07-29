package br.com.gere.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.gere.helper.MySqlConection;

public class GerenciamentoModel {

	private int id;
	private String nome;
	private String telefone;

	@Override
	public String toString(){

		return getNome()+" - "+getTelefone();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public static ArrayList<GerenciamentoModel> Select(){
		Connection con= MySqlConection.ConectarDB();
		String sql = "select * from contact order by id desc;";
		ArrayList<GerenciamentoModel> lstgere = new ArrayList<>();
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				GerenciamentoModel gere = new GerenciamentoModel();

				gere.setId(rs.getInt("id"));
				gere.setNome(rs.getString("Name"));
				gere.setTelefone(rs.getString("phone"));

				lstgere.add(gere);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstgere;
	}
	public static boolean Inserir(GerenciamentoModel gere){
		Connection con= MySqlConection.ConectarDB();
		String sql = "INSERT INTO contact (name, phone) value (?, ?)";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, gere.getNome());
			parametros.setString(2, gere.getTelefone());
			parametros.executeUpdate();
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean Atualizar(GerenciamentoModel gere){
		Connection con= MySqlConection.ConectarDB();
		String sql = "update contact set name= ?, phone= ? where id = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, gere.getNome());
			parametros.setString(2, gere.getTelefone());
			parametros.setInt(3, gere.getId());
			parametros.executeUpdate();
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean Deletar(GerenciamentoModel gere){
		Connection con= MySqlConection.ConectarDB();
		String sql = "delete from contact where id = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setInt(1, gere.getId());
			parametros.executeUpdate();
			con.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static GerenciamentoModel PreparaEdt(GerenciamentoModel gere){
		Connection con= MySqlConection.ConectarDB();
		GerenciamentoModel newGere= new GerenciamentoModel();
		String sql = "select * from contact where id = ? order by id asc;";
		try {
			PreparedStatement parametros;

			parametros = con.prepareStatement(sql);
			parametros.setInt(1, gere.getId());
			ResultSet rs = parametros.executeQuery();

			while(rs.next()){
				newGere.setNome(rs.getString("Name"));
				newGere.setTelefone(rs.getString("phone"));
				newGere.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newGere;
	}

}
