package negocio;

import java.util.List;

import dominio.Turnos;

public interface TurnosNegocio {
	public boolean agregarTurno(Turnos turno);
	public boolean eliminarTurno(int idTurno);
	public boolean modificarTurno(Turnos turno);
	public List<Turnos> listarTurnos();
	public List<Turnos> obtenerTurno_Paciente(String dniPaciente);
	public List<Turnos> obtenerTurno_Medico(int idMedico);
}
