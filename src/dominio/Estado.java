package dominio;

public class Estado {
	
	private int idEstado;
	private String descripcion;
	
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Estado(int idEstado, String descripcion) {
		super();
		
		this.idEstado = idEstado;
		this.descripcion = descripcion;
	}
	
	public Estado() {}
}
