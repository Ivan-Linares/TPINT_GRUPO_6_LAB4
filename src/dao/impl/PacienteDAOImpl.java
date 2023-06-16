package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dao.IPacienteDAO;
import dominio.Domicilio;
import dominio.Paciente;
import dominio.Persona;

public class PacienteDAOImpl implements IPacienteDAO{
	private static final String insertPersona = "Insert into personas (dni, nombre, apellido, sexo, idNacionalidad, fechaNacimiento, correo, idDomicilio) values (?,?,?,?,?,?,?,?)";
	private static final String insertPaciente = "Insert into pacientes (dni, idCobertura) values (?,?)";
	private static final String insertDomicilio = "insert into Domicilio (Direccion, Localidad, Provincia, Pais) values (?,?,?,?)";
	private static final String insertTelefono = "insert into TelefonosXPersonas (DNI, Telefono) values (?,?)";
	private static final String deletePaciente = "update pacientes set Activo = 0 where dni =?";
	private static final String deletePersona = "update personas set Activo = 0 where dni=?";
	private static final String deleteTelefono = "update TelefonosXPersonas set Activo = 0 where dni=0";
	private static final String updatePaciente = "update pacientes set idCobertura=?, activo=? where dni=?";
	private static final String updatePersona = "update personas set dni=?, nombre=?, apellido=?, sexo=?, nacionalidad=?, fechaNac=?, correo=?, idDomicilio=?, activo=? where idPaciente=?";
	private static final String listar = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, pais.idPais as idNacionalidad, pais.descripcion AS nacionalidad from personas per inner join pacientes pac on per.dni = pac.dni left join Paises pais on per.idNacionalidad = pais.IdPais";
	
	private int agregarDomicilio(Paciente paciente) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int agregoDomicilio = 0;
		
		try {
			
			statement = conexion.prepareStatement(insertDomicilio);
			statement.setString(1, paciente.getDomicilio().getDireccion());
			statement.setString(2, paciente.getDomicilio().getLocalidad());
			statement.setString(3, paciente.getDomicilio().getProvincia());
			statement.setInt(4, 1); // arreglar
			
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
			statement.setString(4, paciente.getSexo());
			statement.setInt(5, 1); //Es ID nacionalidad

			SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);		
			SimpleDateFormat print = new SimpleDateFormat("yyyyddMM");		
			Long datesql = Long.parseLong(print.format(paciente.getFechaNacimiento()));
			java.sql.Date sqlDate = new java.sql.Date(datesql);		
			statement.setDate(6, sqlDate);
			
			statement.setString(7, paciente.getCorreo());
			statement.setInt(8, 1);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarPersona = true;
			}
			
			statement = conexion.prepareStatement(insertPaciente);
			statement.setString(1, paciente.getDni());
			statement.setInt(2, paciente.getCobertura().getId());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarPaciente = true;
			}
			
			statement = conexion.prepareStatement(insertTelefono);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getTelefono().getTelefono());
			
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
	public boolean eliminar(String dniPaciente) {
		
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
			statement.setString(1, dniPaciente);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoPaciente = true;
			}
			
			statement = conexion.prepareStatement(deletePersona);
			statement.setString(1, dniPaciente);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoPersona = true;			
			}
			
			//agregar validacion si tiene tel asociados
			statement = conexion.prepareStatement(deleteTelefono);
			statement.setString(1, dniPaciente);
			
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
	public ArrayList<Paciente> listarPacientes() {

		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listar);
			
			while(rs.next()) {
				Paciente persona = new Paciente();
				persona.setDni(rs.getString("dni"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				persona.setSexo(rs.getString("sexo"));
				persona.setFechaNacimiento(rs.getDate("fechaNacimiento"));
				persona.setCorreo(rs.getString("correo"));
				persona.setActivo(rs.getBoolean("activo"));
				persona.setNacionalidad(rs.getString("nacionalidad"));
				
				
				listaPacientes.add(persona);
			}
			
			
			
			
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaPacientes;
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
			statement.setString(4, paciente.getSexo());
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
