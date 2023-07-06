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
				if(!agregarTurno(request)) {
					request.setAttribute("mensajeError", "No se pudo dar de alta el turno.");					
					
				}
				else {
					request.setAttribute("mensajeExito", "Turno dado de alta con exito ");
					
					RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
					rd.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private void listarTurnos(HttpServletRequest request) {
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		ArrayList<Turnos> listaTurnos  = tDao.listarTurnos();
		
		request.setAttribute("listaTurnos", listaTurnos);
	}
	
	private boolean buscarDNI(HttpServletRequest request, String dniPaciente) {
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
		
		boolean encontroDNI = false;
		
		for (Paciente paciente : listaPacientes) {
			if(paciente.getDni().equals(dniPaciente)) {
				encontroDNI = true;
			}
		}
		
		return encontroDNI;
	}
	
	private Paciente buscarPaciente(HttpServletRequest request, String dniPaciente) {
		Paciente paciente = new Paciente();
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
		
		for (Paciente pac : listaPacientes) {
			if(pac.getDni().equals(dniPaciente)) {
				paciente = pac;
			}
		}
		
		return paciente;
	}
	
	protected void listarEspecialidades(HttpServletRequest request) {
		EspecialidadesDAOImpl espDao = new EspecialidadesDAOImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>)espDao.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
	
	protected boolean agregarTurno(HttpServletRequest request) {
		
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		Turnos nuevoTurno = new Turnos();
		String dni = "";
		boolean agregoTurno = false;
		boolean agregoTurnoxMedico = false;
		
		try {
			
			nuevoTurno.setFechaHora(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString()));
			Especialidad esp = new Especialidad();
			esp.setIdEspecialidad(Integer.parseInt(request.getParameter("espSelect")));
			esp.setDescripcion(request.getParameter("descripcionEsp"));
			nuevoTurno.setEspecialidad(esp);
			
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
		
		if(agregoTurno == true && agregoTurnoxMedico == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
