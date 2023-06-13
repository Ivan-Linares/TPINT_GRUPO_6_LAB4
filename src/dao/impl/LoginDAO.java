package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dominio.Login;

public class LoginDAO {
	private String query = "SELECT * FROM Usuarios WHERE Email = ? AND Password = ?";
	
	public int iniciarSesion(Login login) {
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
		return resultado;
	}
}
