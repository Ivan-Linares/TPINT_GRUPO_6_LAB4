package serverlets;

import java.io.IOException;
import java.util.ArrayList;

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
			
			listarTurnos(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String dniPaciente ="";
		boolean encontro = false;
		Paciente paciente = null;
		
		if(request.getParameter("btn-buscar-dni") != null) {
			dniPaciente = request.getParameter("dniPaciente").toString();
			encontro = buscarDNI(request, dniPaciente);
			paciente = buscarPaciente(request, dniPaciente);
			
			request.setAttribute("encontroDNI", encontro);
			request.setAttribute("paciente", paciente);
			request.setAttribute("dni", dniPaciente);
			
			RequestDispatcher rd = request.getRequestDispatcher("InsertarTurno.jsp");
			rd.forward(request, response);
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
	
}
