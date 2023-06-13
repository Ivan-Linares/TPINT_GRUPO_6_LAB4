package dominio;

public class Domicilio {
	private int idDomicilio;
	private Persona persona;
	private String direccion;
	private String localidad;
	private String provincia;
	private boolean activo;
	
	public Domicilio() {		
	}
	
	public Domicilio(int idDomicilio, Persona persona, String direccion, String localidad, String provincia,
			boolean activo) {		
		this.idDomicilio = idDomicilio;
		this.persona = persona;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.activo = activo;
	}
	
	public int getIdDomicilio() {
		return idDomicilio;
	}
	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio = idDomicilio;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
		
}
