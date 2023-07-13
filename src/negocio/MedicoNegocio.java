package negocio;

import java.util.ArrayList;

import dominio.Medico;

public interface MedicoNegocio {
	public boolean agregar(Medico medico);
	public boolean eliminar(String dniMedico, int idMedico);
	public boolean reactivar(String dniMedico, int idMedico);
	public boolean modificar (Medico medico);
	public ArrayList<Medico> listarMedicos();
	public Medico obtenerMedico(String dniMedico);
	public ArrayList<Medico> listarEspecialidadesMedico();
}
