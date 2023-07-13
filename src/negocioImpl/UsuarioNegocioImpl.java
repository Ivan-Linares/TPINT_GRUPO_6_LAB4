package negocioImpl;

import java.util.ArrayList;

import dao.impl.PaisDAOImpl;
import dao.impl.UsuarioDAOImpl;
import dominio.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio{
	UsuarioDAOImpl uDao = new UsuarioDAOImpl();
	@Override
	public boolean agregar(Usuario nuevoUsuario) {
		// TODO Auto-generated method stub
		return uDao.agregar(nuevoUsuario);
	}

	@Override
	public boolean eliminar(String dniUsuario) {
		// TODO Auto-generated method stub
		return uDao.eliminar(dniUsuario);
	}

	@Override
	public boolean reactivar(String dniUsuario) {
		// TODO Auto-generated method stub
		return uDao.reactivar(dniUsuario);
	}

	@Override
	public boolean modificar(Usuario usuario) {
		// TODO Auto-generated method stub
		return uDao.modificar(usuario);
	}

	@Override
	public ArrayList<Usuario> listar() {
		// TODO Auto-generated method stub
		return uDao.listar();
	}

	@Override
	public Usuario obtener(String dni) {
		// TODO Auto-generated method stub
		return uDao.obtener(dni);
	}

}
