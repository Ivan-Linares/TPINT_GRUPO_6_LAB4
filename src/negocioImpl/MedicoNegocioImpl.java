package negocioImpl;

import java.util.ArrayList;

import dao.impl.HorariosTrabajoDAOImpl;
import dao.impl.MedicoDAOImpl;
import dominio.Medico;
import negocio.MedicoNegocio;

public class MedicoNegocioImpl implements  MedicoNegocio{
	MedicoDAOImpl mDao = new MedicoDAOImpl();
	@Override
	public boolean agregar(Medico medico) {
		// TODO Auto-generated method stub
		return mDao.agregar(medico);
	}

	@Override
	public boolean eliminar(String dniMedico, int idMedico) {
		// TODO Auto-generated method stub
		return mDao.eliminar(dniMedico, idMedico);
	}

	@Override
	public boolean reactivar(String dniMedico, int idMedico) {
		// TODO Auto-generated method stub
		return mDao.reactivar(dniMedico, idMedico);
	}

	@Override
	public boolean modificar(Medico medico) {
		// TODO Auto-generated method stub
		return mDao.modificar(medico);
	}

	@Override
	public ArrayList<Medico> listarMedicos() {
		// TODO Auto-generated method stub
		return mDao.listarMedicos();
	}

	@Override
	public Medico obtenerMedico(String dniMedico) {
		// TODO Auto-generated method stub
		return mDao.obtenerMedico(dniMedico);
	}

	@Override
	public ArrayList<Medico> listarEspecialidadesMedico() {
		// TODO Auto-generated method stub
		return mDao.listarEspecialidadesMedico();
	}
	
	public ArrayList<Medico> filtrarMedicos(String campo, String valor){
		return mDao.filtrarMedicos(campo, valor);
	}
	
	public ArrayList<Medico> filtrarMedicosPorDia(String valor){
		return mDao.filtrarMedicosPorDia(valor);
	}

	public ArrayList<Medico> filtrarMedicosPorEspecialidad(String valor){
		return mDao.filtrarMedicosPorEspecialidad(valor);
	}
	
	public ArrayList<Medico> listarMedicosXespecialidad(String idEsp){
		return mDao.listarMedicosXespecialidad(idEsp);
	}
}
