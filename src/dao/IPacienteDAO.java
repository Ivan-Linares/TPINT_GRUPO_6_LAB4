package dao;

import java.util.List;
import dominio.Paciente;

public interface IPacienteDAO {
	public boolean Insert(Paciente paciente);
	public boolean Delete(Paciente paciente_a_eliminar);
	public List<Paciente> ListarPacientes();
}
