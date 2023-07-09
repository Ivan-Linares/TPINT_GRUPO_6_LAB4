package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import dao.IUsuarioDAO;
import dominio.Domicilio;
import dominio.Medico;
import dominio.Pais;
import dominio.Usuario;

public class UsuarioDAOImpl implements IUsuarioDAO {
	private static final String insertUsuario="Insert into Usuarios(DNI, Correo, Password, EsAdministrador) values (?,?,?,?)";
	private static final String deleteUsuario="update Usuarios set Activo = 0 where idUsuario=?";
	private static final String listarUsuarios="select idusuario, dni, correo, password, esadministrador, activo from Usuarios";
	private static final String listarUsuario="select idusuario, dni, correo, password, esadministrador, activo from Usuarios where idusuario=";

	@Override
	public boolean agregar(Usuario nuevoUsuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean state = false;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try {
			statement = conexion.prepareStatement(insertUsuario);
			statement.setString(1, nuevoUsuario.getDni());
			statement.setString(2, nuevoUsuario.getCorreo());		
			statement.setString(3, nuevoUsuario.getPassword());
			statement.setBoolean(4, nuevoUsuario.isEsAdministrador());	
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				state = true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public boolean eliminar(int idUsuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		boolean state = false;
		
		try
		{

			statement = conexion.prepareStatement(deleteUsuario);
			statement.setInt(1, idUsuario);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				state = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public boolean modificar(Usuario usuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean state = false;
		
		try {
			
			String updateUsuario = "update Usuario set Correo ='"+ usuario.getCorreo()+"', Password ='"+ usuario.getPassword()+"' where idUsuario="+ usuario.getId();	
			statement = conexion.prepareStatement(updateUsuario);
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				state = true;		
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public ArrayList<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario obtener(int idUsuario) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Usuario usuario = new Usuario();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarUsuario + idUsuario);
			
			rs.next();
			usuario.setId(rs.getInt("idUsuario"));
			usuario.setDni(rs.getString("dni"));
			usuario.setCorreo(rs.getString("correo"));
			usuario.setPassword(rs.getString("password"));
			usuario.setEsAdministrador(rs.getBoolean("esadministrador"));
			usuario.setActivo(rs.getBoolean("activo"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return usuario;
	}

}
