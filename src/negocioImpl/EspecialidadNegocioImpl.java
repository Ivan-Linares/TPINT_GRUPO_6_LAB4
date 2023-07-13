package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.impl.CoberturaDAOImpl;
import dao.impl.EspecialidadesDAOImpl;
import dominio.Especialidad;
import negocio.EspecialidadNegocio;

public class EspecialidadNegocioImpl implements EspecialidadNegocio{
	EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
	@Override
	public boolean agregarEspecialidadMedico(int idEspecialidad, int idMedico) {
		// TODO Auto-generated method stub
		return eDao.agregarEspecialidadMedico(idEspecialidad, idMedico);
	}

	@Override
	public List<Especialidad> listarEspecialidades() {
		// TODO Auto-generated method stub
		return eDao.listarEspecialidades();
	}

	@Override
	public ArrayList<Especialidad> listarEspecialidadesPorMedico(int idMedico) {
		// TODO Auto-generated method stub
		return eDao.listarEspecialidadesPorMedico(idMedico);
	}

	@Override
	public boolean eliminarEspecialidadMedico(int idEspecialidad, int idMedico) {
		// TODO Auto-generated method stub
		return eDao.eliminarEspecialidadMedico(idEspecialidad, idMedico);
	}

	@Override
	public boolean reactivarEspecialidadMedico(int idEspecialidad, int idMedico) {
		// TODO Auto-generated method stub
		return eDao.reactivarEspecialidadMedico(idEspecialidad, idMedico);
	}

}
