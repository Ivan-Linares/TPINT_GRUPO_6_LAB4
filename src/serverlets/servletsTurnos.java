package serverlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.MedicoDAOImpl;
import dao.impl.PacienteDAOImpl;
import dao.impl.TurnosDAOImpl;
import dominio.Especialidad;
import dominio.Medico;
import dominio.Paciente;
import dominio.Persona;
import dominio.Turnos;
import dominio.Usuario;

/**
 * Servlet implementation class servletsTurnos
 */
@WebServlet("/servletsTurnos")
public class servletsTurnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletsTurnos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			System.out.println("entro en usuario");
			rd.forward(request, response);
		}
		else {
			
			if(request.getParameter("btn-nuevo-turno") != null) {
				listarEspecialidades(request);
				RequestDispatcher rd = request.getRequestDispatcher("InsertarTurno.jsp");
				rd.forward(request, response);
			}
			else {
				listarTurnos(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
				rd.forward(request, response);				
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idEsp = "";
		String descEsp = "";
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		
		int idTurno = 0;
		if(request.getParameter("idTurno") != null) idTurno = Integer.parseInt(request.getParameter("idTurno"));
		
		if(request.getParameter("btn-buscar-medicos") != null) {
			idEsp = request.getParameter("especialidadSelect").toString();

			EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
			ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
			
			for (Especialidad esp : listaEsp) {
				if(esp.getIdEspecialidad() == Integer.parseInt(idEsp)) {
					descEsp = esp.getDescripcion();
				}
			}
			
			MedicoDAOImpl mDao = new MedicoDAOImpl();
			ArrayList<Medico> listaMedico = mDao.listarMedicosXespecialidad(idEsp);
			
			request.setAttribute("listaMed", listaMedico);
			request.setAttribute("espSelect", idEsp);
			request.setAttribute("descripcionEsp", descEsp);
			
			listarEspecialidades(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("InsertarTurno.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-agregar-turno") != null) {
			
			try {
				if(agregarTurno(request)) {
					request.setAttribute("mensajeExito", "Turno dado de alta con exito ");
					listarTurnos(request);
					
				}
				else {
					request.setAttribute("mensajeError", "No se pudo dar de alta el turno.");
					listarTurnos(request);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-editar-turno") != null) {
			agregarDetallesVerTurno(request, idTurno);
			request.setAttribute("editar-turno", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditarTurno.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-ver-turno") != null) {

			agregarDetallesVerTurno(request, idTurno);
			
			RequestDispatcher rd = request.getRequestDispatcher("EditarTurno.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-guardar-turno") != null) {
			try {
				if(ModificarTurno(tDao, request))request.setAttribute("mensajeExito", "El turno fue modificado Correctamente!");
				else request.setAttribute("mensajeError", "Ocurrió un error al intentar modificar al turno");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			agregarDetallesVerTurno(request, idTurno);
			request.setAttribute("editar-turno", true);
			listarTurnos(request);
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-eliminar-turno") != null) {
			
			EliminarTurno(tDao, idTurno);
			listarTurnos(request);
			request.setAttribute("mensajeExito", "El Turno fue eliminado correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		
	}

	private void listarTurnos(HttpServletRequest request) {
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		ArrayList<Turnos> listaTurnos  = tDao.listarTurnos();
		
		request.setAttribute("listaTurnos", listaTurnos);
	}
	
	protected void agregarDetallesVerTurno(HttpServletRequest request, int idTurno) {
		
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		Turnos turno = tDao.listarTurnoxId(idTurno);
		request.setAttribute("turno", turno);
		listarEspecialidades(request);
	}
	
	protected void listarEspecialidades(HttpServletRequest request) {
		EspecialidadesDAOImpl espDao = new EspecialidadesDAOImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>)espDao.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
	
	protected boolean ModificarTurno(TurnosDAOImpl tDao, HttpServletRequest request) throws ParseException {
		Turnos turno = setearDatosTurno(request);
		return tDao.modificarTurno(turno);
	}
	
	protected Turnos setearDatosTurno(HttpServletRequest request) {
		Turnos nuevoTurno = new Turnos();
		
		try {
			
			nuevoTurno.setIdTurno(Integer.parseInt(request.getParameter("idTurno").toString()));
			
			nuevoTurno.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString()));
			nuevoTurno.setHora(Integer.parseInt(request.getParameter("hora")));
			nuevoTurno.setObservacion(request.getParameter("observaciones"));
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return nuevoTurno;
	}
	
	protected boolean agregarTurno(HttpServletRequest request) {
		
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		Turnos nuevoTurno = new Turnos();
		String dni = "";
		boolean agregoTurno = false;
		
		try {
			
			nuevoTurno.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString()));
			Especialidad esp = new Especialidad();
			esp.setIdEspecialidad(Integer.parseInt(request.getParameter("espSelect")));
			esp.setDescripcion(request.getParameter("descripcionEsp"));
			nuevoTurno.setEspecialidad(esp);
			
			nuevoTurno.setHora(Integer.parseInt(request.getParameter("hora")));
			
			Paciente pac = new Paciente();
			PacienteDAOImpl pDao = new PacienteDAOImpl();
			ArrayList<Paciente> listaPaciente = pDao.listarPacientes();
			
			dni = request.getParameter("dniPaciente");
			
			for (Paciente paciente : listaPaciente) {
				if(paciente.getDni().equals(dni)) {
					pac.setIdPaciente(paciente.getIdPaciente());
				}
			}
			nuevoTurno.setPaciente(pac);
			
			nuevoTurno.setObservacion(request.getParameter("observaciones"));
			
			Medico med = new Medico();
			med.setIdMedico(Integer.parseInt(request.getParameter("medico")));
			nuevoTurno.setMedico(med);
			
			agregoTurno = tDao.agregarTurno(nuevoTurno);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return agregoTurno;
	}
	
	protected void EliminarTurno(TurnosDAOImpl tDao, int idTurno) {		
		tDao.eliminarTurno(idTurno);
	}
	
}
