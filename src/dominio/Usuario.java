package dominio;

public class Usuario {
	private int id;
	private boolean esAdministrador;
	
	public Usuario() {
		
	}
	
	public Usuario(int id, boolean esAdministrador) {
		super();
		this.id = id;
		this.esAdministrador = esAdministrador;
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
	
}
