package dao;

import java.util.List;
import dominio.Paciente;

public interface IPacienteDAO {
	public boolean agregar(Paciente paciente);
	public boolean eliminar(String dniPaciente);
	public boolean modificar (Paciente paciente);
	public List<Paciente> listarPacientes();
	public Paciente obtenerPaciente(String dniPaciente);
}
