package dao;

import dominio.HorariosTrabajo;

public interface IHorariosTrabajo {
	public boolean agregar(HorariosTrabajo horarioTrabajo);
	public boolean eliminar();
	public boolean modificar();
	public boolean listar();
}
