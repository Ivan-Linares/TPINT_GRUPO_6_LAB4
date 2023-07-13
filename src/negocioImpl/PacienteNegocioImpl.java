package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.impl.MedicoDAOImpl;
import dao.impl.PacienteDAOImpl;
import dominio.Paciente;
import negocio.PacienteNegocio;

public class PacienteNegocioImpl implements PacienteNegocio {
	PacienteDAOImpl pDao = new PacienteDAOImpl();
	@Override
	public boolean agregar(Paciente paciente) {
		// TODO Auto-generated method stub
		return pDao.agregar(paciente);
	}

	@Override
	public boolean eliminar(String dniPaciente) {
		// TODO Auto-generated method stub
		return pDao.eliminar(dniPaciente);
	}

	@Override
	public boolean reactivar(String dniPaciente) {
		// TODO Auto-generated method stub
		return pDao.reactivar(dniPaciente);
	}

	@Override
	public int modificar(Paciente paciente) {
		// TODO Auto-generated method stub
		return pDao.modificar(paciente);
	}

	@Override
	public List<Paciente> listarPacientes() {
		// TODO Auto-generated method stub
		return pDao.listarPacientes();
	}

	@Override
	public Paciente obtenerPaciente(String dniPaciente) {
		// TODO Auto-generated method stub
		return pDao.obtenerPaciente(dniPaciente);
	}

	@Override
	public boolean existe(String dniPaciente) {
		// TODO Auto-generated method stub
		return pDao.existe(dniPaciente);
	}

	public ArrayList<Paciente> filtrarPacientes(String campo, String valor) {
		return pDao.filtrarPacientes(campo, valor);
	}
}
