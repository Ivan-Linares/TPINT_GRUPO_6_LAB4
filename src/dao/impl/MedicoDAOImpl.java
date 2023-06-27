package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dao.IMedicoDAO;
import dominio.Domicilio;
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
	private static final String listarMedicos = "select med.idMedico as idMedico, per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.Activo as activo from personas per inner join medicos med on per.dni = med.dni";
	private static final String listarEspecialidades = "select med.IdMedico as idMedico, esp.IdEspecialidad as idEspecialidad, esp.Descripcion as Especialidad from medicos med inner join especialidadxmedico exm on exm.IdMedico = med.IdMedico inner join especialidades esp on esp.IdEspecialidad = exm.IdEspecialidad";
	private static final String updatePersona = "update personas set dni=?, nombre=?, apellido=?, sexo=?, nacionalidad=?, fechaNac=?, correo=?, idDomicilio=?, activo=? where idPaciente=?";
	private static final String listarIDMedico = "select IdMedico from medicos";
	private static final String listarMedico = "select med.idmedico as idMedico, per.dni as dni, per.nombre as nombre, per.apellido as apellido, per.sexo as sexo, per.FechaNacimiento as fechaNacimiento, per.Correo as correo, per.Activo as activo, pais.idPais as idNacionalidad, pais.descripcion AS nacionalidad,domicilio.Direccion AS direccion, domicilio.Localidad as localidad, domicilio.Provincia as provincia, telefono.telefono as telefono from personas per inner join medicos med on per.dni = med.dni left join Paises pais on per.idNacionalidad = pais.IdPais left join Domicilio domicilio on per.idDomicilio = domicilio.idDomicilio left join TelefonosXPersonas telefono on per.dni = telefono.dni where per.dni = ";
	private static final String listarMedicoXEspecialidad = "select m.IdMedico as IdMedico, m.DNI as DNI, p.Nombre as Nombre, p.Apellido as Apellido from especialidades e inner join especialidadxmedico exm on exm.IdEspecialidad = e.IdEspecialidad inner join medicos m on m.IdMedico = exm.IdMedico inner join personas p on p.DNI = m.DNI where e.IdEspecialidad =";
	
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
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean modificado = false;
				
		try {
			statement = conexion.prepareStatement(updatePersona);
				
			statement.setString(1, medico.getDni());
			statement.setString(2, medico.getNombre());
			statement.setString(3, medico.getApellido());
			statement.setString(4, medico.getSexo());
			statement.setInt(5, medico.getNacionalidad().getIdPais());
			statement.setDate(6, (Date) medico.getFechaNacimiento());
			statement.setString(7, medico.getCorreo());
			statement.setInt(8, medico.getDomicilio().getIdDomicilio());
			statement.setBoolean(9, medico.isActivo());
					
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
				persona.setIdMedico(rs.getInt("idMedico"));
				persona.setDni(rs.getString("dni"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				persona.setActivo(rs.getBoolean("activo"));
				//persona.setDiaAtencion(rs.getString("DiaAtencion"));
				//persona.setHorarioAtencionDesde(rs.getString("HorarioAtencionDesde"));
				//persona.setHorarioAtencionHasta(rs.getString("HorarioAtencionHasta"));
				
				
				//st2 = conexion.createStatement();
				//ResultSet rs2 = st2.executeQuery(
;
				
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
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Medico medico = new Medico();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarMedico + "'"+ dniMedico +"'");
			
			rs.next();
			medico.setIdMedico(rs.getInt("idMedico"));
			medico.setDni(rs.getString("dni"));
			medico.setNombre(rs.getString("nombre"));
				
				System.out.println("entro al impl medico" + medico.getDni());
				medico.setApellido(rs.getString("apellido"));
				medico.setSexo(rs.getString("sexo"));
				medico.setFechaNacimiento(rs.getDate("fechaNacimiento"));
				medico.setCorreo(rs.getString("correo"));
				medico.setActivo(rs.getBoolean("activo"));
				
				Pais nacionalidad = new Pais();
				nacionalidad.setDescripcion(rs.getString("nacionalidad"));
				
				medico.setNacionalidad(nacionalidad);		
				
				Domicilio domicilio = new Domicilio();
				domicilio.setDireccion(rs.getString("direccion"));
				domicilio.setLocalidad(rs.getString("localidad"));
				domicilio.setProvincia(rs.getString("provincia"));
				
				medico.setDomicilio(domicilio);		
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return medico;
	}

	@Override
	public ArrayList<Medico> listarEspecialidadesMedico() {
		Statement st;
		Statement st2;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Medico> listaEspMedicos = new ArrayList<Medico>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarIDMedico);
			
			while(rs.next()) {
				Medico medico = new Medico();
				medico.setIdMedico(rs.getInt("IdMedico"));
				
				st2 = conexion.createStatement();
				ResultSet rs2 = st2.executeQuery(listarEspecialidades);
				
				ArrayList<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
				
				while(rs2.next()) {
					
					int id = rs2.getInt("idMedico");
					
					if(id == rs.getInt("IdMedico")) {
						Especialidad esp = new Especialidad();
						esp.setIdEspecialidad(rs.getInt("idEspecialidad"));
						esp.setDescripcion(rs.getString("Especialidad"));
						
						listaEspecialidades.add(esp);
					}
					
				}
				
				medico.setEspecialidades(listaEspecialidades);
				
				listaEspMedicos.add(medico);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaEspMedicos;
	}
	
	public ArrayList<Medico> listarMedicosXespecialidad(String idEsp){
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Medico> listaMedicos = new ArrayList<Medico>();
		
		try {
			
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarMedicoXEspecialidad+idEsp);
			
			while(rs.next()) {
				Medico medico = new Medico();
				medico.setIdMedico(rs.getInt("IdMedico"));
				medico.setDni(rs.getString("DNI"));
				medico.setNombre(rs.getString("Nombre"));
				medico.setApellido(rs.getString("Apellido"));
				
				listaMedicos.add(medico);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaMedicos;
	}

}
