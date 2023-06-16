package dominio;

public class Usuario {
	private int id;
	private boolean esAdministrador;
	private String correo;
	
	public Usuario() {
		
	}
	
	public Usuario(int id, boolean esAdministrador, String correo) {
		super();
		this.id = id;
		this.esAdministrador = esAdministrador;
		this.correo = correo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}	
	
}
