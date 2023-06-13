package dominio;

public class Telefono {
	private Persona persona;
	private String telefono;
	private boolean activo;
	
	public Telefono() {		
	}
	
	public Telefono(dominio.Persona persona, String telefono, boolean activo) {		
		this.persona = persona;
		this.telefono = telefono;
		this.activo = activo;
	}	
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
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
