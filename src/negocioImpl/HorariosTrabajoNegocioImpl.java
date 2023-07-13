package negocioImpl;

import java.util.ArrayList;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.HorariosTrabajoDAOImpl;
import dominio.HorariosTrabajo;
import negocio.HorariosTrabajoNegocio;

public class HorariosTrabajoNegocioImpl implements  HorariosTrabajoNegocio{
	HorariosTrabajoDAOImpl htDao = new HorariosTrabajoDAOImpl();
	@Override
	public boolean agregar(HorariosTrabajo horarioTrabajo) {
		// TODO Auto-generated method stub
		return htDao.agregar(horarioTrabajo);
	}

	@Override
	public boolean eliminar(int idMedico, String dia, String horaEntrada) {
		// TODO Auto-generated method stub
		return htDao.eliminar(idMedico, dia, horaEntrada);
	}

	@Override
	public boolean reactivar(int idMedico, String dia, String horaEntrada) {
		// TODO Auto-generated method stub
		return htDao.reactivar(idMedico, dia, horaEntrada);
	}

	@Override
	public boolean eliminarTodos(int idMedico) {
		// TODO Auto-generated method stub
		return htDao.eliminarTodos(idMedico);
	}

	@Override
	public boolean reactivarTodos(int idMedico) {
		// TODO Auto-generated method stub
		return htDao.reactivarTodos(idMedico);
	}

	@Override
	public boolean modificar(HorariosTrabajo horarioTrabajo) {
		// TODO Auto-generated method stub
		return htDao.modificar(horarioTrabajo);
	}

	@Override
	public ArrayList<HorariosTrabajo> listar() {
		// TODO Auto-generated method stub
		return htDao.listar();
	}

	@Override
	public ArrayList<HorariosTrabajo> listarPorMedico(int IdMedico) {
		// TODO Auto-generated method stub
		return htDao.listarPorMedico(IdMedico);
	}

}
