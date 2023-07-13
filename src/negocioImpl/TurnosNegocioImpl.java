package negocioImpl;

import java.util.List;

import dao.impl.PacienteDAOImpl;
import dao.impl.TurnosDAOImpl;
import dominio.Turnos;
import negocio.TurnosNegocio;

public class TurnosNegocioImpl implements TurnosNegocio{
	TurnosDAOImpl tDao = new TurnosDAOImpl();
	@Override
	public boolean agregarTurno(Turnos turno) {
		// TODO Auto-generated method stub
		return tDao.agregarTurno(turno);
	}

	@Override
	public boolean eliminarTurno(int idTurno) {
		// TODO Auto-generated method stub
		return tDao.eliminarTurno(idTurno);
	}

	@Override
	public boolean modificarTurno(Turnos turno) {
		// TODO Auto-generated method stub
		return tDao.modificarTurno(turno);
	}

	@Override
	public List<Turnos> listarTurnos() {
		// TODO Auto-generated method stub
		return tDao.listarTurnos();
	}

	@Override
	public List<Turnos> obtenerTurno_Paciente(String dniPaciente) {
		// TODO Auto-generated method stub
		return tDao.obtenerTurno_Paciente(dniPaciente);
	}

	@Override
	public List<Turnos> obtenerTurno_Medico(int idMedico) {
		// TODO Auto-generated method stub
		return tDao.obtenerTurno_Medico(idMedico);
	}
	
	public Turnos listarTurnoxId(int idTurno) {
		return tDao.listarTurnoxId(idTurno);
	}

}
