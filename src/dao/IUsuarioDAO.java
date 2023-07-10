package dao;

import java.util.ArrayList;

import dominio.Usuario;

public interface IUsuarioDAO {
	public boolean agregar(Usuario nuevoUsuario);
	public boolean eliminar(String dniUsuario);
	public boolean modificar(Usuario usuario);
	public ArrayList<Usuario> listar();
	public Usuario obtener(String dni);
}
