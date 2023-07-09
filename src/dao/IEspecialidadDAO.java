package dao;

import java.util.List;

import dominio.Especialidad;
import dominio.Medico;

public interface IEspecialidadDAO {
	public boolean agregarEspecialidadMedico(int idEspecialidad,  int idMedico);
	public List<Especialidad> listarEspecialidades();
}
