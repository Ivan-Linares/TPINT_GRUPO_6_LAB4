package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import dao.ITurnosDAO;
import dominio.Turnos;

public class TurnosDAOImpl implements ITurnosDAO{

	private static final String insertTurno = "Insert into Turnos (FechaHora, IdEspecialidad, IdPaciente, IdEstado, Observacion, Activo) values (?, ?, ?, ?, ?, true)";
	private static final String updateTurno = "Update Turnos set FechaHora=?, IdEspecialidad=?, IdPaciente=?, IdEstado=?, Observacion=? where IdTurno = ?";
	private static final String deleteTurno = "Update Turnos set Activo = 0 where IdTurno = ?";
	private static final String listarTurnos = "select IdTurno, FechaHora, IdEspecialidad, IdPaciente, IdEstado, Observacion from Turnos where Activo = true";
	private static final String listarTurnosXid = "select FechaHora, IdEspecialidad, IdPaciente, IdEstado, Observacion from Turnos where Activo = true and IdTurno = ?";
	private static final String listarTurnosPaciente = "select IdTurno, FechaHora, IdEspecialidad, IdEstado, Observacion from Turnos where Activo = true and IdPaciente = ?";
	private static final String listarTurnosMedico = "select T.IdTurno, T.FechaHora, T.IdEspecialidad, T.IdPaciente, T.IdEstado, T.Observacion from Turnos T inner join TurnosXMedico TxM on TxM.IdTurno = T.IdTurno where T.Activo = true and TxM.IdMedico = ?";
	
	
	@Override
	public boolean agregarTurno(Turnos turno) {
		PreparedStatement statement;
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
	public List<Turnos> listarTurnos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Turnos> obtenerTurno_Paciente(int idPaciente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Turnos> obtenerTurno_Medico(int idMedico) {
		// TODO Auto-generated method stub
		return null;
	}

}
