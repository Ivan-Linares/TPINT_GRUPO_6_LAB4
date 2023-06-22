package dao;

import java.util.List;
import dominio.Turnos;

public interface ITurnosDAO {
	
	public boolean agregarTurno(Turnos turno);
	public boolean eliminarTurno(int idTurno);
	public boolean modificarTurno(Turnos Turno);
	public List<Turnos> listarTurnos();
	public List<Turnos> obtenerTurno_Paciente(int idPaciente);
	public List<Turnos> obtenerTurno_Medico(int idMedico);
}
