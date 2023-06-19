package dominio;

public class Telefono {
	private String dni;
	private String telefono;
	private boolean activo;
	
	public Telefono() {		
	}
	
	public Telefono(String dni, String telefono, boolean activo) {		
		this.dni = dni;
		this.telefono = telefono;
		this.activo = activo;
	}	
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
