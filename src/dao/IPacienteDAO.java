package dao;

import java.util.List;
import dominio.Paciente;

public interface IPacienteDAO {
	public boolean agregar(Paciente paciente);
	public boolean eliminar(String dniPaciente);
	public boolean modificar (int ID);
	public List<Paciente> listarPacientes();
}
