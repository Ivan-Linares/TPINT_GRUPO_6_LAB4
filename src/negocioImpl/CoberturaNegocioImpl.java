package negocioImpl;

import java.util.List;

import dao.impl.CoberturaDAOImpl;
import dominio.Cobertura;
import negocio.CoberturaNegocio;

public class CoberturaNegocioImpl implements CoberturaNegocio {
	CoberturaDAOImpl coberturaDao = new CoberturaDAOImpl();
	@Override
	public List<Cobertura> listarCoberturas() {
		return coberturaDao.listarCoberturas();
	}

}
