package dao;

import java.util.List;
import dominio.Paciente;

public interface IPacienteDAO {
	public boolean agregar(Paciente paciente);
	public boolean eliminar(Paciente paciente_a_eliminar);
	public List<Paciente> listarPacientes();
}
