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
import dominio.Pais;
import dominio.Persona;
import dominio.Telefono;

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
	private static final String listarPacientes = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, pais.idPais as idNacionalidad, pais.descripcion AS nacionalidad, telefono.telefono as telefono from personas per inner join pacientes pac on per.dni = pac.dni left join Paises pais on per.idNacionalidad = pais.IdPais left join TelefonosXPersonas telefono on per.dni = telefono.dni";
	private static final String listarPaciente = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, pais.idPais as idNacionalidad, pais.descripcion AS nacionalidad,domicilio.Direccion AS direccion, domicilio.Localidad as localidad, domicilio.Provincia as provincia, telefono.telefono as telefono from personas per inner join pacientes pac on per.dni = pac.dni left join Paises pais on per.idNacionalidad = pais.IdPais left join Domicilio domicilio on per.idDomicilio = domicilio.idDomicilio left join TelefonosXPersonas telefono on per.dni = telefono.dni where per.dni = ";
	
	private int agregarDomicilio(Paciente paciente) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idDomicilio = 0;
		
		try {
			
			statement = conexion.prepareStatement(insertDomicilio, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, paciente.getDomicilio().getDireccion());
			statement.setString(2, paciente.getDomicilio().getLocalidad());
			statement.setString(3, paciente.getDomicilio().getProvincia());
			statement.setInt(4, 1); 
			
			idDomicilio = statement.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idDomicilio;
	}
	
	private int agregarTelefono(Telefono nuevoTelefono) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int agregoTelefono = 0;
		
		try {
			
			statement = conexion.prepareStatement(insertTelefono);
			statement.setString(1, nuevoTelefono.getDni());
			statement.setString(2, nuevoTelefono.getTelefono());
			
			agregoTelefono = statement.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agregoTelefono;
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
			agregarTelefono(paciente.getTelefono());
			
			statement = conexion.prepareStatement(insertPersona);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getNombre());
			statement.setString(3, paciente.getApellido());
			statement.setString(4, paciente.getSexo());
			statement.setInt(5, paciente.getNacionalidad().getIdPais());

			SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);		
			SimpleDateFormat print = new SimpleDateFormat("yyyyddMM");		
			Long datesql = Long.parseLong(print.format(paciente.getFechaNacimiento()));
			java.sql.Date sqlDate = new java.sql.Date(datesql);		
			statement.setDate(6, sqlDate);
			
			statement.setString(7, paciente.getCorreo());
			
			// ACA Cuando se inserta el domicilio, hay qu tomar el ID y asignarselo
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
			ResultSet rs = st.executeQuery(listarPacientes);
			
			while(rs.next()) {
				Paciente persona = new Paciente();
				persona.setDni(rs.getString("dni"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				persona.setSexo(rs.getString("sexo"));
				persona.setFechaNacimiento(rs.getDate("fechaNacimiento"));
				persona.setCorreo(rs.getString("correo"));
				persona.setActivo(rs.getBoolean("activo"));
				
				Pais nacionalidad = new Pais();
				nacionalidad.setDescripcion(rs.getString("nacionalidad"));
				
				persona.setNacionalidad(nacionalidad);				
				
				listaPacientes.add(persona);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaPacientes;
	}

	@Override
	public boolean modificar(Paciente paciente) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean modificado = false;
		
		try {
			statement = conexion.prepareStatement(updatePersona);
			
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getNombre());
			statement.setString(3, paciente.getApellido());
			statement.setString(4, paciente.getSexo());
			statement.setInt(5, paciente.getNacionalidad().getIdPais());
			statement.setDate(6, (Date) paciente.getFechaNacimiento());
			statement.setString(7, paciente.getCorreo());
			statement.setInt(8, paciente.getDomicilio().getIdDomicilio());
			statement.setBoolean(9, paciente.isActivo());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificado = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modificado;
	}

	@Override
	public Paciente obtenerPaciente(String dniPaciente) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Paciente paciente = new Paciente();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarPaciente + "'"+ dniPaciente +"'");
			
			rs.next();
			
				paciente.setDni(rs.getString("dni"));
				paciente.setNombre(rs.getString("nombre"));
				
				System.out.println("entro al impl" + paciente.getDni());
				paciente.setApellido(rs.getString("apellido"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
				paciente.setCorreo(rs.getString("correo"));
				paciente.setActivo(rs.getBoolean("activo"));
				
				Pais nacionalidad = new Pais();
				nacionalidad.setDescripcion(rs.getString("nacionalidad"));
				
				paciente.setNacionalidad(nacionalidad);		
				
				Domicilio domicilio = new Domicilio();
				domicilio.setDireccion(rs.getString("direccion"));
				domicilio.setLocalidad(rs.getString("localidad"));
				domicilio.setProvincia(rs.getString("provincia"));
				
				paciente.setDomicilio(domicilio);		
				
				Telefono telefono = new Telefono();
				telefono.setTelefono(rs.getString("telefono"));
				
				paciente.setTelefono(telefono);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return paciente;
	}

}
