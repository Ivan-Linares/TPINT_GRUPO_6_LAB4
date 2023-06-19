package dao;

import java.util.List;
import dominio.Medico;
import dominio.Paciente;

public interface IMedicoDAO {
	public boolean agregar(Medico medico);
	public boolean eliminar(String dniMedico);
	public boolean modificar (Medico medico);
	public List<Medico> listarMedicos();
	public Medico obtenerMedico(String dniMedico);
}
