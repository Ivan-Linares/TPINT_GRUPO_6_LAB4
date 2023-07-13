package negocioImpl;

import java.util.ArrayList;

import dao.impl.PacienteDAOImpl;
import dao.impl.TelefonoDAOImpl;
import dominio.Telefono;
import negocio.TelefonoNegocio;

public class TelefonoNegocioImpl implements TelefonoNegocio{
	TelefonoDAOImpl tDao = new TelefonoDAOImpl();
	@Override
	public boolean agregar(Telefono nuevoTelefono) {
		return tDao.agregar(nuevoTelefono);
	}

	@Override
	public boolean eliminar(String dni, String telefono) {		
		return tDao.eliminar(dni, telefono);
	}

	@Override
	public boolean reactivar(String dni, String telefono) {
		// TODO Auto-generated method stub
		return tDao.reactivar(dni, telefono);
	}

	@Override
	public boolean modificar(Telefono telefono) {
		// TODO Auto-generated method stub
		return tDao.modificar(telefono);
	}

	@Override
	public ArrayList<Telefono> listar() {
		// TODO Auto-generated method stub
		return tDao.listar();
	}

	@Override
	public ArrayList<Telefono> listarPorPersona(String dni) {
		// TODO Auto-generated method stub
		return tDao.listarPorPersona(dni);
	}

}
