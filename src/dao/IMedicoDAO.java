package dao;

import java.util.ArrayList;
import java.util.List;
import dominio.Medico;
import dominio.Paciente;

public interface IMedicoDAO {
	public boolean agregar(Medico medico);
	public boolean eliminar(String dniMedico, int idMedico);
	public boolean modificar (Medico medico);
	public ArrayList<Medico> listarMedicos();
	public Medico obtenerMedico(String dniMedico);
	public ArrayList<Medico> listarEspecialidadesMedico();
}
