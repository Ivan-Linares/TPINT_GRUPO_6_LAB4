package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import dao.ITurnosDAO;
import dominio.Especialidad;
import dominio.Estado;
import dominio.Medico;
import dominio.Paciente;
import dominio.Pais;
import dominio.Turnos;

public class TurnosDAOImpl implements ITurnosDAO{

	private static final String insertTurno = "Insert into Turnos (FechaHora, IdEspecialidad, IdPaciente, IdEstado, Observacion, Activo) values (?, ?, ?, ?, ?, true)";
	private static final String insertTurnoXMedico = "Insert into turnosxmedico (IdMedico, IdTurno, Activo) values (?, ?, true)";
	private static final String updateTurno = "Update Turnos set FechaHora=?, IdEspecialidad=?, IdPaciente=?, IdEstado=?, Observacion=? where IdTurno = ?";
	private static final String deleteTurno = "Update Turnos set Activo = 0 where IdTurno = ?";
	private static final String listarTurnos = "select t.IdTurno as IdTurno, t.FechaHora as FechaHora, m.IdMedico as IdMedico, t.IdEspecialidad as IdEspecialidad, e.Descripcion as Especialidad, t.IdPaciente as IdPaciente, t.IdEstado as IdEstado, t.Observacion as Observacion from turnos t inner join turnosxmedico txm on txm.IdTurno = t.IdTurno inner join medicos m on m.IdMedico = txm.IdMedico inner join especialidades e on e.IdEspecialidad = t.IdEspecialidad where t.Activo = true";
	private static final String listarTurnosXid = "select t.IdTurno as IdTurno, t.FechaHora as FechaHora, m.IdMedico as IdMedico, t.IdEspecialidad as IdEspecialidad, e.Descripcion as Especialidad, t.IdPaciente as IdPaciente, t.IdEstado as IdEstado, t.Observacion as Observacion from turnos t inner join turnosxmedico txm on txm.IdTurno = t.IdTurno inner join medicos m on m.IdMedico = txm.IdMedico inner join especialidades e on e.IdEspecialidad = t.IdEspecialidad where t.Activo = true and t.IdTurno = ?";
	private static final String listarTurnosPaciente = "select t.IdTurno as IdTurno, t.FechaHora as FechaHora, m.IdMedico as IdMedico, t.IdEspecialidad as IdEspecialidad, e.Descripcion as Especialidad, t.IdPaciente as IdPaciente, t.IdEstado as IdEstado, t.Observacion as Observacion from turnos t inner join turnosxmedico txm on txm.IdTurno = t.IdTurno inner join medicos m on m.IdMedico = txm.IdMedico inner join especialidades e on e.IdEspecialidad = t.IdEspecialidad where t.Activo = true and t.IdPaciente =";
	private static final String listarTurnosMedico = "select t.IdTurno as IdTurno, t.FechaHora as FechaHora, m.IdMedico as IdMedico, t.IdEspecialidad as IdEspecialidad, e.Descripcion as Especialidad, t.IdPaciente as IdPaciente, t.IdEstado as IdEstado, t.Observacion as Observacion from turnos t inner join turnosxmedico txm on txm.IdTurno = t.IdTurno inner join medicos m on m.IdMedico = txm.IdMedico inner join especialidades e on e.IdEspecialidad = t.IdEspecialidad where t.Activo = true and txm.IdMedico =";
	
	@Override
	public boolean agregarTurno(Turnos turno) {
		PreparedStatement statement;
		PreparedStatement st2;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean agregoTurno = false;
		
		try {
			Timestamp timestamp = Timestamp.valueOf(turno.getFechaHora()); /// por lo q lei, no se puede mandar un localdatetime de una, asi q lo paso a timestamp
			
			statement = conexion.prepareStatement(insertTurno);
			
			statement.setTimestamp(1, timestamp); //aca se supone q se inserta el timestamp, oremos
			statement.setInt(2, turno.getEspecialidad().getIdEspecialidad());
			statement.setInt(3, turno.getPaciente().getIdPaciente());
			statement.setInt(4, turno.getEstado().getIdEstado());
			statement.setString(5, turno.getObservacion());
			
			if(statement.executeUpdate() > 0) {
				conexion.commit();
				agregoTurno = true;
			}
			
			st2 = conexion.prepareStatement(insertTurnoXMedico);
			
			st2.setInt(1, turno.getMedico().getIdMedico());
			st2.setInt(2, turno.getIdTurno());
			
			if(st2.executeUpdate() > 0) {
				conexion.commit();
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
		
		try {
			Timestamp timestamp = Timestamp.valueOf(turno.getFechaHora()); /// por lo q lei, no se puede mandar un localdatetime de una, asi q lo paso a timestamp
			
			statement = conexion.prepareStatement(updateTurno);
			
			statement.setTimestamp(1, timestamp); //aca se supone q se inserta el timestamp, oremos
			statement.setInt(2, turno.getEspecialidad().getIdEspecialidad());
			statement.setInt(3, turno.getPaciente().getIdPaciente());
			statement.setInt(4, turno.getEstado().getIdEstado());
			statement.setString(5, turno.getObservacion());
			statement.setInt(6, turno.getIdTurno());
			
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
				turno.setFechaHora(LocalDateTime.parse((CharSequence)rs.getDate("FechaHora")));
				
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
				esp.setIdEspecialidad(rs.getInt("IdEspecialidad"));
				esp.setDescripcion(rs.getString("Especialidad"));
				
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
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				
				listaTurnos.add(turno);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaTurnos;
	}

	@Override
	public ArrayList<Turnos> obtenerTurno_Paciente(int idPaciente) {
		Statement st;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
		
		try {
			
			st = conexion.createStatement();
			ResultSet rs = st.executeQuery(listarTurnosPaciente+idPaciente);
			
			while(rs.next()) {
				Turnos turno = new Turnos();
				turno.setIdTurno(rs.getInt("IdTurno"));
				turno.setFechaHora(LocalDateTime.parse((CharSequence)rs.getDate("FechaHora")));
				
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
				esp.setIdEspecialidad(rs.getInt("IdEspecialidad"));
				esp.setDescripcion(rs.getString("Especialidad"));
				
				turno.setEspecialidad(esp);
				
				Paciente paciente = new Paciente();
				PacienteDAOImpl pDao = new PacienteDAOImpl();
				ArrayList<Paciente> listaPacientes = pDao.listarPacientes();
				
				for (Paciente pac : listaPacientes) {
					if(pac.getIdPaciente() == idPaciente) {
						paciente = pac;
					}
				}
				turno.setPaciente(paciente);
				
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("IdEstado"));
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				
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
				turno.setFechaHora(LocalDateTime.parse((CharSequence)rs.getDate("FechaHora")));
				
				Medico medico = new Medico();
				MedicoDAOImpl mDao = new MedicoDAOImpl();
				ArrayList<Medico> listaMedicos = mDao.listarMedicos();
				
				for (Medico med : listaMedicos) {
					if(med.getIdMedico() == idMedico) {
						medico = med;
					}
				}
				turno.setMedico(medico);
				
				Especialidad esp = new Especialidad();
				esp.setIdEspecialidad(rs.getInt("IdEspecialidad"));
				esp.setDescripcion(rs.getString("Especialidad"));
				
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
				
				turno.setEstado(estado);
				turno.setObservacion(rs.getString("Observacion"));
				
				listaTurnos.add(turno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaTurnos;
	}

}
