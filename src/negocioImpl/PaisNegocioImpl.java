package negocioImpl;

import java.util.List;

import dao.impl.MedicoDAOImpl;
import dao.impl.PaisDAOImpl;
import dominio.Pais;
import negocio.PaisNegocio;

public class PaisNegocioImpl implements PaisNegocio {
	PaisDAOImpl pDao = new PaisDAOImpl();
	@Override
	public List<Pais> listarPaises() {
		// TODO Auto-generated method stub
		return pDao.listarPaises();
	}

}
