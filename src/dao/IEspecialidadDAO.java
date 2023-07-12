package dao;

import java.util.ArrayList;
import java.util.List;

import dominio.Especialidad;
import dominio.Medico;

public interface IEspecialidadDAO {
	public boolean agregarEspecialidadMedico(int idEspecialidad,  int idMedico);
	public List<Especialidad> listarEspecialidades();
	public ArrayList<Especialidad> listarEspecialidadesPorMedico(int idMedico);
	public boolean eliminarEspecialidadMedico(int idEspecialidad, int idMedico);
	public boolean reactivarEspecialidadMedico(int idEspecialidad, int idMedico);
}
