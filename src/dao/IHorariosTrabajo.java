package dao;

import java.util.ArrayList;

import dominio.HorariosTrabajo;

public interface IHorariosTrabajo {
	public boolean agregar(HorariosTrabajo horarioTrabajo);
	public boolean eliminar(int idMedico, String dia);	
	public boolean modificar(HorariosTrabajo horarioTrabajo);
	public ArrayList<HorariosTrabajo> listar();
	public ArrayList<HorariosTrabajo> listarPorMedico(int IdMedico);
}
