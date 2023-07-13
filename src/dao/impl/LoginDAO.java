package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dominio.Login;
import dominio.Usuario;

public class LoginDAO {
	private String query = "SELECT * FROM Usuarios WHERE Correo = ? AND Password = ?";
	private String queryObtenerUsuario = "SELECT IdUsuario, EsAdministrador, Correo, DNI FROM Usuarios WHERE Correo = ?";
	
	public boolean iniciarSesion(Login login) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		ResultSet filas;		
		int resultado = 0;
		try {
			 statement = conexion.getSQLConexion().prepareStatement(query);
			 
			 statement.setString(1, login.getEmail());
			 statement.setString(2, login.getPassword());
			 
			 filas = statement.executeQuery();

			 if(filas.next()) resultado = 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			conexion.cerrarConexion();
		}		
		if(resultado == 1) return true;
		return false;
	}
	
	public Usuario obtenerUsuario(String email) {
		
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		ResultSet rs;
		try {

			statement = conexion.getSQLConexion().prepareStatement(queryObtenerUsuario);
			statement.setString(1, email);
			rs = statement.executeQuery();
		
			if(rs.next()) {
				return new Usuario(rs.getInt("IdUsuario"), rs.getBoolean("EsAdministrador"), rs.getString("Correo"), rs.getString("dni"));
			}
			
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally {
			conexion.cerrarConexion();
		}
		return null;
	}
}
