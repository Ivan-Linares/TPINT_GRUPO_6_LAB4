package negocio;

import java.util.ArrayList;
import java.util.List;

import dominio.Especialidad;

public interface EspecialidadNegocio {
	public boolean agregarEspecialidadMedico(int idEspecialidad,  int idMedico);
	public List<Especialidad> listarEspecialidades();
	public ArrayList<Especialidad> listarEspecialidadesPorMedico(int idMedico);
	public boolean eliminarEspecialidadMedico(int idEspecialidad, int idMedico);
	public boolean reactivarEspecialidadMedico(int idEspecialidad, int idMedico);
}
