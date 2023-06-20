package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dao.IMedicoDAO;
import dominio.Especialidad;
import dominio.HorariosTrabajo;
import dominio.Medico;
import dominio.Paciente;
import dominio.Pais;
import dominio.Telefono;

public class MedicoDAOImpl implements IMedicoDAO{

	private static final String insertPersona = "Insert into personas (dni, nombre, apellido, sexo, idNacionalidad, fechaNacimiento, correo, idDomicilio) values (?,?,?,?,?,?,?,?)";
	private static final String insertMedico = "Insert into medicos (dni) values (?)";
	private static final String insertDomicilio = "insert into Domicilio (Direccion, Localidad, Provincia, Pais)";
	private static final String insertTelefono = "insert into TelefonosXPersonas (DNI, Telefono) values (?,?)";
	private static final String deleteMedico = "update medicos set Activo=0 where dni=?";
	private static final String deletePersona = "update personas set Activo=0 where dni=?";
	private static final String deleteTelefono = "update TelefonosXPersonas set Activo=0 where dni=?";
	private static final String listarMedicos = "select per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.Activo as activo from personas per inner join medicos med on per.dni = med.dni";
	private static final String listarEspecialidades = "select esp.IdEspecialidad as IdEspecialidad, esp.Descripcion as Especialidad from medicos med inner join especialidadxmedico exm on exm.IdMedico = med.IdMedico inner join especialidades esp on esp.IdEspecialidad = exm.IdEspecialidad";
	
	private int agregarDomicilio(Medico medico) {
		
		Statement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idDomicilio = 0;
		
		try {
			statement = conexion.createStatement();			
			String query = insertDomicilio + "values('"+medico.getDomicilio().getDireccion() +"','"+medico.getDomicilio().getLocalidad() +"', '"+ medico.getDomicilio().getProvincia() +"', 1)";
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
	public boolean agregar(Medico medico) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean insertarPersona = false;
		boolean insertarMedico = false;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try {
			int idDomicilio = agregarDomicilio(medico);
			
			statement = conexion.prepareStatement(insertPersona);
			statement.setString(1, medico.getDni());
			statement.setString(2, medico.getNombre());		
			statement.setString(3, medico.getApellido());
			statement.setString(4, medico.getSexo());	
			statement.setInt(5, medico.getNacionalidad().getIdPais());
			
			SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);		
			SimpleDateFormat print = new SimpleDateFormat("yyyyddMM");		
			Long datesql = Long.parseLong(print.format(medico.getFechaNacimiento()));
			java.sql.Date sqlDate = new java.sql.Date(datesql);				
			statement.setDate(6, sqlDate);	
			
			statement.setString(7, medico.getCorreo());
			statement.setInt(8, idDomicilio);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarPersona = true;
			}
			
			statement = conexion.prepareStatement(insertMedico);
			statement.setString(1, medico.getDni());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				insertarMedico = true;
			}
			
			//agregarTelefono(medico.getTelefono());
			//agregarHorariosMedico(medico);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(insertarMedico == true && insertarPersona == true) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void agregarHorariosMedico(Medico medico) {
		boolean insertarHorario = false;
		HorariosTrabajoDAOImpl horariosDao = new HorariosTrabajoDAOImpl();

		ArrayList<HorariosTrabajo> horariosLista = medico.getHorariosTrabajo();
		for(HorariosTrabajo horarioTrabajoAgregar : horariosLista) {
			horariosDao.agregar(horarioTrabajoAgregar);
		}
	}

	@Override
	public boolean eliminar(String dniMedico) {
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
		
		boolean eliminoMedico = false;
		boolean eliminoPersona = false;
		boolean eliminoTelefono = false;
		
		try
		{

			statement = conexion.prepareStatement(deleteMedico);
			statement.setString(1, dniMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoMedico = true;
			}
			
			statement = conexion.prepareStatement(deletePersona);
			statement.setString(1, dniMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoPersona = true;			
			}
			
			//agregar validacion si tiene tel asociados
			statement = conexion.prepareStatement(deleteTelefono);
			statement.setString(1, dniMedico);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoTelefono = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(eliminoMedico == true && eliminoPersona == true && eliminoTelefono == true) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean modificar(Medico medico) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Medico> listarMedicos() {
		
		Statement st;
		//Statement st2;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Medico> listaMedicos = new ArrayList<Medico>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarMedicos);
			
			while(rs.next()) {
				Medico persona = new Medico();
				persona.setDni(rs.getString("dni"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				persona.setActivo(rs.getBoolean("activo"));
				//persona.setDiaAtencion(rs.getString("DiaAtencion"));
				//persona.setHorarioAtencionDesde(rs.getString("HorarioAtencionDesde"));
				//persona.setHorarioAtencionHasta(rs.getString("HorarioAtencionHasta"));
				
				
				//st2 = conexion.createStatement();
				//ResultSet rs2 = st2.executeQuery(listarEspecialidades);
				
				//while(rs2.next()) {
					//Especialidad especialidad = new Especialidad();
					
					//especialidad.setIdEspecialidad(Integer.parseInt(rs.getString("IdEspecialidad")));
					//especialidad.setDescripcion(rs.getString("Especialidad"));
					
					//ArrayList<Especialidad> ListaEspecialidades = new ArrayList<Especialidad>();
					//ListaEspecialidades.add(especialidad);
					
					//persona.setEspecialidades(ListaEspecialidades);					
				//}
				
				
				listaMedicos.add(persona);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaMedicos;
	}

	@Override
	public Medico obtenerMedico(String dniMedico) {
		// TODO Auto-generated method stub
		return null;
	}

}
