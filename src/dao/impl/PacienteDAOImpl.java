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
import dominio.Cobertura;
import dominio.Domicilio;
import dominio.Paciente;
import dominio.Pais;
import dominio.Persona;
import dominio.Telefono;

public class PacienteDAOImpl implements IPacienteDAO{
	private static final String insertPersona = "Insert into personas (dni, nombre, apellido, sexo, idNacionalidad, fechaNacimiento, correo, idDomicilio) values (?,?,?,?,?,?,?,?)";
	private static final String insertPaciente = "Insert into pacientes (dni, idCobertura) values (?,?)";
	private static final String insertDomicilio = "insert into Domicilio (Direccion, Localidad, Provincia, Pais)";
	private static final String insertTelefono = "insert into TelefonosXPersonas (DNI, Telefono) values (?,?)";
	private static final String deletePaciente = "update pacientes set Activo = 0 where dni =?";
	private static final String deletePersona = "update personas set Activo = 0 where dni=?";
	private static final String deleteTelefono = "update TelefonosXPersonas set Activo = 0 where dni=?";
	private static final String deleteDomicilio = "update domicilio set Activo = 0 where idDomicilio=?";
	private static final String listarPacientes = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, pais.idPais as idNacionalidad, pais.descripcion AS nacionalidad, telefono.telefono as telefono, pac.IdPaciente as IdPaciente from personas per inner join pacientes pac on per.dni = pac.dni left join Paises pais on per.idNacionalidad = pais.IdPais left join TelefonosXPersonas telefono on per.dni = telefono.dni";
	private static final String listarPaciente = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, nacionalidad.idPais as idNacionalidad, nacionalidad.descripcion AS nacionalidad,domicilio.iddomicilio as iddomicilio, domicilio.Direccion AS direccion, domicilio.Localidad as localidad, domicilio.Provincia as provincia, pais.idPais as idpais, pais.descripcion as pais,telefono.telefono as telefono,c.IdCobertura as idcobertura, c.Descripcion as cobertura from personas per inner join pacientes pac on per.dni = pac.dni left join Paises nacionalidad on per.idNacionalidad = nacionalidad.IdPais left join Domicilio domicilio on per.idDomicilio = domicilio.idDomicilio left join Paises pais on domicilio.Pais = pais.idPais left join TelefonosXPersonas telefono on per.dni = telefono.dni left join coberturas c on c.IdCobertura = pac.IdCobertura where per.dni = ";
	private static final String existePaciente = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS existe_registro FROM personas p WHERE p.dni = ?";
	
	private int agregarDomicilio(Paciente paciente) {
		
		Statement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idDomicilio = 0;
		
		try {
			statement = conexion.createStatement();			
			String query = insertDomicilio + "values('"+paciente.getDomicilio().getDireccion() +"','"+paciente.getDomicilio().getLocalidad() +"', '"+ paciente.getDomicilio().getProvincia() +"'," + paciente.getDomicilio().getPais().getIdPais()+")";
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);	
			ResultSet rs = statement.getGeneratedKeys();
			
			if (rs.next()){
				idDomicilio = Integer.parseInt(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idDomicilio;
	}
	
	private boolean agregarTelefono(Telefono nuevoTelefono) {
		
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
			int idDomicilio = agregarDomicilio(paciente);
			
			statement = conexion.prepareStatement(insertPersona);
			statement.setString(1, paciente.getDni());
			statement.setString(2, paciente.getNombre());		
			statement.setString(3, paciente.getApellido());
			statement.setString(4, paciente.getSexo());	
			statement.setInt(5, paciente.getNacionalidad().getIdPais());

			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
			String fechaNacimientoString = print.format(paciente.getFechaNacimiento());
			Date sqlDate = Date.valueOf(fechaNacimientoString);
			statement.setDate(6, sqlDate);
			
			statement.setString(7, paciente.getCorreo());
			statement.setInt(8, idDomicilio);
			
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
			
			agregarTelefono(paciente.getTelefono());
			
			
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
		boolean eliminoDomicilio = false;
		
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
			
			statement = conexion.prepareStatement(deleteTelefono);
			statement.setString(1, dniPaciente);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoTelefono = true;
			}
			
			Paciente paciente = obtenerPaciente(dniPaciente);
			statement = conexion.prepareStatement(deleteDomicilio);
			statement.setInt(1, paciente.getDomicilio().getIdDomicilio());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoDomicilio = true;
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(eliminoPaciente == true && eliminoPersona == true && eliminoTelefono == true && eliminoDomicilio == true) {
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
				persona.setIdPaciente(rs.getInt("IdPaciente"));
				
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
	public int modificar(Paciente paciente) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int modificado = 0;
		
		try {
			
			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
			String fechaNacimientoString = print.format(paciente.getFechaNacimiento());
			Date sqlDate = Date.valueOf(fechaNacimientoString);
			String updatePersona = "update personas set nombre='"+paciente.getNombre()+ "', apellido='" + paciente.getApellido()+"', sexo='"+paciente.getSexo() +"', idNacionalidad='"+ paciente.getNacionalidad().getIdPais()+"', fechaNacimiento='"+sqlDate+"', correo='"+ paciente.getCorreo()+"' where dni ='"+paciente.getDni()+"'";
			System.out.println(updatePersona);
			statement = conexion.prepareStatement(updatePersona);
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificado = 1;			
			}
			String updatePaciente = "update pacientes set idCobertura="+ paciente.getCobertura().getId()+" where dni='"+ paciente.getDni()+"'";	
			System.out.println(updatePaciente);
			statement = conexion.prepareStatement(updatePaciente);
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificado = 1;			
			}
			String updateDomicilio = "update domicilio set direccion='"+ paciente.getDomicilio().getDireccion()+"', localidad='"+ paciente.getDomicilio().getLocalidad()+"', provincia='"+paciente.getDomicilio().getProvincia()+"', pais="+paciente.getDomicilio().getPais().getIdPais()+" where idDomicilio="+ paciente.getDomicilio().getIdDomicilio();
			System.out.println(updateDomicilio);
			statement = conexion.prepareStatement(updateDomicilio);
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificado = 1;			
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
				paciente.setApellido(rs.getString("apellido"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
				paciente.setCorreo(rs.getString("correo"));
				paciente.setActivo(rs.getBoolean("activo"));
				
				Pais nacionalidad = new Pais();
				nacionalidad.setIdPais(rs.getInt("idnacionalidad"));
				nacionalidad.setDescripcion(rs.getString("nacionalidad"));
				
				paciente.setNacionalidad(nacionalidad);		
				
				Domicilio domicilio = new Domicilio();
				domicilio.setIdDomicilio(rs.getInt("iddomicilio"));
				domicilio.setDireccion(rs.getString("direccion"));
				domicilio.setLocalidad(rs.getString("localidad"));
				domicilio.setProvincia(rs.getString("provincia"));
				
				Pais paisDomicilio = new Pais();
				paisDomicilio.setIdPais(rs.getInt("idPais"));
				paisDomicilio.setDescripcion(rs.getString("pais"));
				domicilio.setPais(paisDomicilio);
				
				paciente.setDomicilio(domicilio);		
				
				Telefono telefono = new Telefono();
				telefono.setTelefono(rs.getString("telefono"));
				
				paciente.setTelefono(telefono);
				
				Cobertura coberturaPaciente = new Cobertura();
				coberturaPaciente.setId(rs.getInt("idcobertura"));
				coberturaPaciente.setDescripcion(rs.getString("cobertura"));
				paciente.setCobertura(coberturaPaciente);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return paciente;
	}

	@Override
	public boolean existe(String dniPaciente) {
	    PreparedStatement st;
	    Connection conexion = Conexion.getConexion().getSQLConexion();	    
	    int existe = 0;
	    try {
	        st = conexion.prepareStatement(existePaciente);
	        st.setString(1, dniPaciente);
	        ResultSet rs = st.executeQuery();
	        if (rs.next()) {
	            existe = rs.getInt("existe_registro");	            
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }	    
		if (existe == 1) return true;
	    return false;
	}

}
