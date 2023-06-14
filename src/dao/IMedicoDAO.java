package dao;

import java.util.List;
import dominio.Medico;

public interface IMedicoDAO {
	public boolean agregar(Medico medico);
	public boolean eliminar(Medico medico);
	public boolean modificar (int IdMedico);
	public List<Medico> listarMedicos();
}
