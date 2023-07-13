package negocio;

import java.util.ArrayList;
import java.util.List;

import dominio.Pais;
import dominio.Telefono;

public interface TelefonoNegocio {
	public boolean agregar(Telefono nuevoTelefono);
	public boolean eliminar(String dni, String telefono);	
	public boolean reactivar(String dni, String telefono);	
	public boolean modificar(Telefono telefono);
	public ArrayList<Telefono> listar();
	public ArrayList<Telefono> listarPorPersona(String dni);
}
