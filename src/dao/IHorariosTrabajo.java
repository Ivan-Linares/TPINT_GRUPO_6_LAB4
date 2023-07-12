package dao;

import java.util.ArrayList;

import dominio.HorariosTrabajo;

public interface IHorariosTrabajo {
	public boolean agregar(HorariosTrabajo horarioTrabajo);
	public boolean eliminar(int idMedico, String dia, String horaEntrada);	
	public boolean reactivar(int idMedico, String dia, String horaEntrada);	
	public boolean eliminarTodos(int idMedico);	
	public boolean modificar(HorariosTrabajo horarioTrabajo);
	public ArrayList<HorariosTrabajo> listar();
	public ArrayList<HorariosTrabajo> listarPorMedico(int IdMedico);
}
