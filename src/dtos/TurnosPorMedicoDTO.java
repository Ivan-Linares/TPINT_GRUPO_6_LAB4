package dtos;

public class TurnosPorMedicoDTO {
	private String nombreMedico;
	private String apellidoMedico;
	private int cantidadTurnos;
	
	public TurnosPorMedicoDTO() {		
	}
	
	public TurnosPorMedicoDTO(String nombreMedico, String apellidoMedico, int cantidadTurnos) {		
		this.nombreMedico = nombreMedico;
		this.apellidoMedico = apellidoMedico;
		this.cantidadTurnos = cantidadTurnos;
	}
	public String getNombreMedico() {
		return nombreMedico;
	}
	public void setNombreMedico(String nombreMedico) {
		this.nombreMedico = nombreMedico;
	}
	public String getApellidoMedico() {
		return apellidoMedico;
	}
	public void setApellidoMedico(String apellidoMedico) {
		this.apellidoMedico = apellidoMedico;
	}
	public int getCantidadTurnos() {
		return cantidadTurnos;
	}
	public void setCantidadTurnos(int cantidadTurnos) {
		this.cantidadTurnos = cantidadTurnos;
	}
	
	
}
