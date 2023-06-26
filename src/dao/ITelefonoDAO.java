package dao;

import java.util.ArrayList;

import dominio.Telefono;

public interface ITelefonoDAO {
	public boolean agregar(Telefono nuevoTelefono);
	public boolean eliminar(String dni);	
	public boolean modificar(Telefono telefono);
	public ArrayList<Telefono> listar();
	public ArrayList<Telefono> listarPorPersona(String dni);
}
