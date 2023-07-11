package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import dao.ITurnosDAO;
import dominio.Especialidad;
import dominio.Estado;
import dominio.Medico;
import dominio.Paciente;
import dominio.Pais;
import dominio.Turnos;

public class TurnosDAOImpl implements ITurnosDAO{

	private static final String insertTurno = "Insert into Turnos (Fecha, Hora, IdEspecialidad, IdPaciente, IdMedico, IdEstado, Observacion, Activo) values (?, ?, ?, ?, ?, ?, ?, true)";
	private static final String updateTurno = "Update Turnos set Fecha=?, Hora=?, IdEstado=?, Observacion=? where IdTurno =";
	private static final String deleteTurno = "Update Turnos set Activo = 0 where IdTurno = ?";
	private static final String listarTurnos = "select t.IdTurno as IdTurno, t.Fecha as Fecha, t.Hora as Hora, m.IdMedico, t.IdEspecialidad as IdEspecialidad, pac.IdPaciente as IdPaciente, t.IdEstado as IdEstado, est.Descripcion as Estado, t.Observacion as Observacion, t.Activo as Activo from turnos t inner join medicos m on m.IdMedico = t.IdMedico inner join pacientes pac on pac.IdPaciente = t.IdPaciente inner join estados est on est.IdEstado = t.IdEstado";
	private static final String listarTurnosXid = "select t.IdTurno as IdTurno, t.Fecha as Fecha, t.Hora as Hora, m.IdMedico, t.IdEspecialidad as IdEspecialidad, pac.IdPaciente as IdPaciente, t.IdEstado as IdEstado, est.Descripcion as Estado, t.Observacion as Observacion, t.Activo as Activo from turnos t inner join medicos m on m.IdMedico = t.IdMedico inner join pacientes pac on pac.IdPaciente = t.IdPaciente inner join estados est on est.IdEstado = t.IdEstado where t.IdTurno =";
	private static final String listarTurnosPaciente = "select t.IdTurno as IdTurno, t.Fecha as Fecha, t.Hora as Hora, m.IdMedico, t.IdEspecialidad as IdEspecialidad, pac.IdPaciente as IdPaciente, t.IdEstado as IdEstado, est.Descripcion as Estado, t.Observacion as Observacion, t.Activo as Activo from turnos t inner join medicos m on m.IdMedico = t.IdMedico inner join pacientes pac on pac.IdPaciente = t.IdPaciente inner join estados est on est.IdEstado = t.IdEstado where pac.DNI =";
	private static final String listarTurnosMedico = "select t.IdTurno as IdTurno, t.Fecha as Fecha, t.Hora as Hora, m.IdMedico, t.IdEspecialidad as IdEspecialidad, pac.IdPaciente as IdPaciente, t.IdEstado as IdEstado, est.Descripcion as Estado, t.Observacion as Observacion, t.Activo as Activo from turnos t inner join medicos m on m.IdMedico = t.IdMedico inner join pacientes pac on pac.IdPaciente = t.IdPaciente inner join estados est on est.IdEstado = t.IdEstado where t.IdMedico =";
	
	@Override
	public boolean agregarTurno(Turnos turno) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean agregoTurno = false;
		
		try {
			//Timestamp timestamp = Timestamp.valueOf(turno.getFechaHora()); /// por lo q lei, no se puede mandar un localdatetime de una, asi q lo paso a timestamp
			
			statement = conexion.prepareStatement(insertTurno);
			
			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTurno = print.format(turno.getFecha());
			Date sqlDate = Date.valueOf(fechaTurno);
			statement.setDate(1, sqlDate);
			
			statement.setInt(2, turno.getHora());
			statement.setInt(3, turno.getEspecialidad().getIdEspecialidad());
			statement.setInt(4, turno.getPaciente().getIdPaciente());
			statement.setInt(5, turno.getMedico().getIdMedico());
			statement.setInt(6, 1);
			statement.setString(7, turno.getObservacion());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				agregoTurno = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agregoTurno;
	}
	
	@Override
	public boolean eliminarTurno(int idTurno) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		boolean eliminoTurno = false;
		
		try
		{
			statement = conexion.prepareStatement(deleteTurno);
			statement.setInt(1, idTurno);
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				eliminoTurno = true;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return eliminoTurno;
	}

	@Override
	public boolean modificarTurno(Turnos turno) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean modificoTurno = false;
		
		System.out.println("tDao: "+turno.toString());
		
		try {
			//Timestamp timestamp = Timestamp.valueOf(turno.getFechaHora()); /// por lo q lei, no se puede mandar un localdatetime de una, asi q lo paso a timestamp
			
			statement = conexion.prepareStatement(updateTurno+turno.getIdTurno());
			
			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTurno = print.format(turno.getFecha());
			Date sqlDate = Date.valueOf(fechaTurno);
			statement.setDate(1, sqlDate);
			
			statement.setInt(2, turno.getHora());
			statement.setInt(3, 1);
			statement.setString(4, turno.getObservacion());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				modificoTurno = true;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modificoTurno;
	}

	@Override
	public ArrayList<Turnos> listarTurnos() {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarTurnos);
			
			while(rs.next()) {
				Turnos turno = new Turnos();
				turno.setIdTurno(rs.getInt("IdTurno"));
				turno.setFecha(rs.getDate("Fecha"));
				turno.setHora(rs.getInt("Hora"));
				//turno.setFechaHora(Date.parse((CharSequence)rs.getDate("FechaHora")));
				
				Medico medico = new Medico();
				int idMed = rs.getInt("IdMedico");
				
				MedicoDAOImpl mDao = new MedicoDAOImpl();
				ArrayList<Medico> listaMedicos = mDao.listarMedicos();
				
				for (Medico med : listaMedicos) {
					if(med.getIdMedico() == idMed) {
						medico = med;
					}
				}
				turno.setMedico(medico);
				
				Especialidad esp = new Especialidad();
				int idEsp = rs.getInt("IdEspecialidad");
				
				EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
				ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
				
				for (Especialidad especialidad : listaEsp) {
					if(especialidad.getIdEspecialidad() == idEsp) {
						esp = especialidad;
					}
				}
				turno.setEspecialidad(esp);
				
				Paciente paciente = new Paciente();
				int idPac = rs.getInt("IdPaciente");
				
				PacienteDAOImpl pDao = new PacienteDAOImpl();
				ArrayList<Paciente> listaPacientes = pDao.listarPacientes();
				
				for (Paciente pac : listaPacientes) {
					if(pac.getIdPaciente() == idPac) {
						paciente = pac;
					}
				}
				turno.setPaciente(paciente);
				
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("IdEstado"));
				estado.setDescripcion(rs.getString("Estado"));
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				turno.setActivo(rs.getBoolean("Activo"));
				
				listaTurnos.add(turno);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaTurnos;
	}
	
	public Turnos listarTurnoxId(int idTurno) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Turnos turno = new Turnos();
		
		try 
		{
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarTurnosXid+idTurno);
			while(rs.next()) {
				
			
			turno.setIdTurno(rs.getInt("IdTurno"));
			turno.setFecha(rs.getDate("Fecha"));
			turno.setHora(rs.getInt("Hora"));
			//turno.setFechaHora(Date.parse((CharSequence)rs.getDate("FechaHora")));
			
			Medico medico = new Medico();
			int idMed = rs.getInt("IdMedico");
			
			MedicoDAOImpl mDao = new MedicoDAOImpl();
			ArrayList<Medico> listaMedicos = mDao.listarMedicos();
			
			for (Medico med : listaMedicos) {
				if(med.getIdMedico() == idMed) {
					medico = med;
				}
			}
			turno.setMedico(medico);
			
			Especialidad esp = new Especialidad();
			int idEsp = rs.getInt("IdEspecialidad");
			
			EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
			ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
			
			for (Especialidad especialidad : listaEsp) {
				if(especialidad.getIdEspecialidad() == idEsp) {
					esp = especialidad;
				}
			}
			turno.setEspecialidad(esp);
			
			Paciente paciente = new Paciente();
			int idPac = rs.getInt("IdPaciente");
			
			PacienteDAOImpl pDao = new PacienteDAOImpl();
			ArrayList<Paciente> listaPacientes = pDao.listarPacientes();
			
			for (Paciente pac : listaPacientes) {
				if(pac.getIdPaciente() == idPac) {
					paciente = pac;
				}
			}
			turno.setPaciente(paciente);
			
			Estado estado = new Estado();
			estado.setIdEstado(rs.getInt("IdEstado"));
			estado.setDescripcion(rs.getString("Estado"));
			turno.setEstado(estado);
			
			String obs = null;
			obs = rs.getString("Observacion");
			if(obs == null) {
				turno.setObservacion("");
			}else {				
				turno.setObservacion(obs);
			}
			
			turno.setActivo(rs.getBoolean("Activo"));
			
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return turno;
	}

	@Override
	public ArrayList<Turnos> obtenerTurno_Paciente(String dniPaciente) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
		
		try {
			
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarTurnosPaciente+dniPaciente);
			
			while(rs.next()) {
				Turnos turno = new Turnos();
				turno.setIdTurno(rs.getInt("IdTurno"));
				turno.setFecha(rs.getDate("Fecha"));
				turno.setHora(rs.getInt("Hora"));
				
				Medico medico = new Medico();
				int idMed = rs.getInt("IdMedico");
				
				MedicoDAOImpl mDao = new MedicoDAOImpl();
				ArrayList<Medico> listaMedicos = mDao.listarMedicos();
				
				for (Medico med : listaMedicos) {
					if(med.getIdMedico() == idMed) {
						medico = med;
					}
				}
				turno.setMedico(medico);
				
				Especialidad esp = new Especialidad();
				int idEsp = rs.getInt("IdEspecialidad");
				
				EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
				ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
				
				for (Especialidad especialidad : listaEsp) {
					if(especialidad.getIdEspecialidad() == idEsp) {
						esp = especialidad;
					}
				}
				turno.setEspecialidad(esp);
				
				Paciente paciente = new Paciente();
				int idPac = rs.getInt("IdPaciente");
				
				PacienteDAOImpl pDao = new PacienteDAOImpl();
				ArrayList<Paciente> listaPacientes = pDao.listarPacientes();
				
				for (Paciente pac : listaPacientes) {
					if(pac.getIdPaciente() == idPac) {
						paciente = pac;
					}
				}
				turno.setPaciente(paciente);
				
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("IdEstado"));
				estado.setDescripcion(rs.getString("Estado"));
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				turno.setActivo(rs.getBoolean("Activo"));
				
				listaTurnos.add(turno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaTurnos;
	}

	@Override
	public ArrayList<Turnos> obtenerTurno_Medico(int idMedico) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
		
		try {
			
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarTurnosMedico+idMedico);
			
			while(rs.next()) {
				Turnos turno = new Turnos();
				turno.setIdTurno(rs.getInt("IdTurno"));
				turno.setFecha(rs.getDate("Fecha"));
				turno.setHora(rs.getInt("Hora"));
				
				Medico medico = new Medico();
				int idMed = rs.getInt("IdMedico");
				
				MedicoDAOImpl mDao = new MedicoDAOImpl();
				ArrayList<Medico> listaMedicos = mDao.listarMedicos();
				
				for (Medico med : listaMedicos) {
					if(med.getIdMedico() == idMed) {
						medico = med;
					}
				}
				turno.setMedico(medico);
				
				Especialidad esp = new Especialidad();
				int idEsp = rs.getInt("IdEspecialidad");
				
				EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
				ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
				
				for (Especialidad especialidad : listaEsp) {
					if(especialidad.getIdEspecialidad() == idEsp) {
						esp = especialidad;
					}
				}
				turno.setEspecialidad(esp);
				
				Paciente paciente = new Paciente();
				int idPac = rs.getInt("IdPaciente");
				
				PacienteDAOImpl pDao = new PacienteDAOImpl();
				ArrayList<Paciente> listaPacientes = pDao.listarPacientes();
				
				for (Paciente pac : listaPacientes) {
					if(pac.getIdPaciente() == idPac) {
						paciente = pac;
					}
				}
				turno.setPaciente(paciente);
				
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("IdEstado"));
				estado.setDescripcion(rs.getString("Estado"));
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				turno.setActivo(rs.getBoolean("Activo"));
				
				listaTurnos.add(turno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaTurnos;
	}

}
