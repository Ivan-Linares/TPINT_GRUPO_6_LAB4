package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IPacienteDAO;
import dominio.Paciente;
import dominio.Persona;

public class PacienteDAOImpl implements IPacienteDAO{
	private static final String insertPersona = "Insert into personas (dni, nombre, apellido, sexo, nacionalidad, fechaNac, correo, idDomicilio, activo) values (?,?,?,?,?,?,?,?,?)";
	private static final String insertPaciente = "Insert into pacientes (dni, idCobertura, activo) values (?,?,?)";
	private static final String insertDomicilio = "insert into Domicilio (Direccion, Localidad, Provincia, Pais, Activo) values (?,?,?,?,?)";
	private static final String insertTelefono = "insert into TelefonosXPersonas (DNI, Telefono, Activo) values (?,?,?)";
	private static final String deletePaciente = "delete from pacientes where id=?";
	private static final String deletePersona = "delete from personas where dni=?";
	private static final String deleteTelefono = "delete from TelefonosXPersonas where dni=0";
	private static final String updatePaciente = "update pacientes set idCobertura=?, activo=? where dni=?";
	private static final String updatePersona = "update personas set dni=?, nombre=?, apellido=?, sexo=?, nacionalidad=?, fechaNac=?, correo=?, idDomicilio=?, activo=? where idPaciente=?";
	private static final String listar = "select * from personas";
	
	private int agregarDomicilio(Paciente paciente) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int agregoDomicilio = 0;
		
		try {
			
			statement = conexion.prepareStatement(insertDomicilio);
			statement.setString(1, paciente.getDomicilio().getDireccion());
			statement.setString(2, paciente.getDomicilio().getLocalidad());
			statement.setString(3, paciente.getDomicilio().getProvincia());
			statement.setString(4, paciente.getNacionalidad());
			statement.setBoolean(5, paciente.isActivo());
			agregoDomicilio = statement.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agregoDomicilio;
	}
	
	@Override
	public boolean agregar(Paciente paciente) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean insertarPersona = false;
		boolean insertarPaciente = false;
		boolean insertarTelefono = false;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try
		{
			agregarDomicilio(paciente);
			
			statement = conexion.prepareStatement(insertPersona);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getNombre());
			statement.setString(3, paciente.getApellido());
			statement.setLong(4, paciente.getSexo());
			statement.setString(5, paciente.getNacionalidad());
			statement.setDate(6, (Date) paciente.getFechaNacimiento());
			statement.setString(7, paciente.getCorreo());
			statement.setInt(8, paciente.getDomicilio().getIdDomicilio());
			statement.setBoolean(9, paciente.isActivo());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarPersona = true;
			}
			
			statement = conexion.prepareStatement(insertPaciente);
			statement.setString(1, paciente.getDni());
			statement.setInt(2, paciente.getCobertura().getId());
			statement.setBoolean(3, paciente.isActivo());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarPaciente = true;
			}
			
			statement = conexion.prepareStatement(insertTelefono);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getTelefono().getTelefono());
			statement.setBoolean(3, paciente.isActivo());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(insertarPaciente == true && insertarPersona == true) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean eliminar(Paciente paciente_a_eliminar) {
		
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
		
		boolean eliminoPaciente = false;
		boolean eliminoPersona = false;
		boolean eliminoTelefono = false;
		
		try
		{
			statement = conexion.prepareStatement(deletePaciente);
			statement.setInt(1, paciente_a_eliminar.getIdPaciente());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoPaciente = true;
			}
			
			statement = conexion.prepareStatement(deletePersona);
			statement.setString(1, paciente_a_eliminar.getDni());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoPersona = true;
			}
			
			statement = conexion.prepareStatement(deleteTelefono);
			statement.setString(1, paciente_a_eliminar.getDni());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoTelefono = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(eliminoPaciente == true && eliminoPersona == true && eliminoTelefono == true) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Paciente> listarPacientes() {
		
		return null;
	}

	@Override
	public boolean modificar(int ID) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean modificar = false;
		
		Paciente paciente = null;
		
		for (Paciente x : listarPacientes()) {
			if(x.getIdPaciente() == ID) {
				paciente = x;
			}
		}
		
		try {
			
			statement = conexion.prepareStatement(updatePersona);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getNombre());
			statement.setString(3, paciente.getApellido());
			statement.setLong(4, paciente.getSexo());
			statement.setString(5, paciente.getNacionalidad());
			statement.setDate(6, (Date) paciente.getFechaNacimiento());
			statement.setString(7, paciente.getCorreo());
			statement.setInt(8, paciente.getDomicilio().getIdDomicilio());
			statement.setBoolean(9, paciente.isActivo());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificar = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modificar;
	}

}
