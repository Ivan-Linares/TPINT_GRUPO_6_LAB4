package dominio;

public class Especialidad {
	private int idEspecialidad;
	private String descripcion;
	private boolean activo;
	public int getIdEspecialidad() {
		return idEspecialidad;
	}
	public void setIdEspecialidad(int idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
