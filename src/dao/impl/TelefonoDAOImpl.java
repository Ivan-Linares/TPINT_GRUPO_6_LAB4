package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dao.ITelefonoDAO;
import dominio.HorariosTrabajo;
import dominio.Telefono;

public class TelefonoDAOImpl implements ITelefonoDAO {
	private static final String insertTelefono = "insert into TelefonosXPersonas (DNI, Telefono) values (?,?)";
	private static final String deleteTelefono = "update TelefonosXPersonas set Activo = 0 where dni=? and telefono=?";
	private static final String reactiveTelefono = "update TelefonosXPersonas set Activo = 1 where dni=? and telefono=?";
	private static final String listarTelefonos = "select dni, telefono, activo from TelefonosXPersonas";
	private static final String listarTelefonosPorPersona = "select dni, telefono, activo from TelefonosXPersonas where dni = '";
	private static final String updateTelefono = "update TelefonosXPersonas set telefono=? where dni=?";
	
	@Override
	public boolean agregar(Telefono nuevoTelefono) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean agregoTelefono = false;
		
		try {
			
			statement = conexion.prepareStatement(insertTelefono);
			statement.setString(1, nuevoTelefono.getDni());
			statement.setString(2, nuevoTelefono.getTelefono());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				agregoTelefono = true;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agregoTelefono;
	}

	@Override
	public boolean eliminar(String dni, String telefono) {
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
		
		boolean eliminoTelefono = false;
		
		try
		{
			statement = conexion.prepareStatement(deleteTelefono);
			statement.setString(1, dni);
			statement.setString(2, telefono);
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoTelefono = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return eliminoTelefono;
	}

	@Override
	public boolean modificar(Telefono telefono) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Telefono> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Telefono> listarPorPersona(String dni) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Telefono> listaT = new ArrayList<Telefono>();
		
		try 
		{
			st = conexion.createStatement();
			
			ResultSet rs = st.executeQuery(listarTelefonosPorPersona + dni + "'");
			
			while(rs.next()) {
				Telefono telefono = new Telefono();
				telefono.setDni(rs.getString("dni"));
				telefono.setTelefono(rs.getString("telefono"));
				telefono.setActivo(rs.getBoolean("activo"));
				listaT.add(telefono);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaT;
	}

	@Override
	public boolean reactivar(String dni, String telefono) {
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
			statement = conexion.prepareStatement(reactiveTelefono);
			statement.setString(1, dni);
			statement.setString(2, telefono);
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

}
