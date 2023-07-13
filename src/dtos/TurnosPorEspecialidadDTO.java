package dtos;


public class TurnosPorEspecialidadDTO {
	private String nombreEspecialidad;
	private int idEspecialidad;
	private int cantidadTurnos;
	
	public TurnosPorEspecialidadDTO(){
		
	}
	
	public TurnosPorEspecialidadDTO(String nombreEspecialidad, int cantidadTurnos, int idEspecialidad) {		
		this.nombreEspecialidad = nombreEspecialidad;
		this.cantidadTurnos = cantidadTurnos;
		this.idEspecialidad = idEspecialidad;
	}
	public String getNombreEspecialidad() {
		return nombreEspecialidad;
	}
	public void setNombreEspecialidad(String nombreEspecialidad) {
		this.nombreEspecialidad = nombreEspecialidad;
	}
	public int getCantidadTurnos() {
		return cantidadTurnos;
	}
	public void setCantidadTurnos(int cantidadTurnos) {
		this.cantidadTurnos = cantidadTurnos;
	}
	
	
}
