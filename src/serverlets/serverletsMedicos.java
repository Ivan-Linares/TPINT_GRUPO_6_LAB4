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
import dao.impl.HorariosTrabajoDAOImpl;
import dao.impl.MedicoDAOImpl;
import dao.impl.PaisDAOImpl;
import dominio.Especialidad;
import dominio.HorariosTrabajo;
import dominio.Medico;
import dominio.Pais;
import dominio.Usuario;

@WebServlet("/serverletsMedicos")
public class serverletsMedicos extends HttpServlet   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		   
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			listarMedicos(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
			rd.forward(request, response);
		}
	}
	
	protected void listarMedicos(HttpServletRequest request) {
		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		ArrayList<Medico> listaMedicos  = medicoDao.listarMedicos();
		request.setAttribute("listaMedicos", listaMedicos);
		System.out.println(listaMedicos.size());
		listarHorariosTrabajoMedico(request);
	}
	
	protected void listarHorariosTrabajoMedico(HttpServletRequest request) {
		HorariosTrabajoDAOImpl htDao = new HorariosTrabajoDAOImpl();
		ArrayList<HorariosTrabajo> listaHT = htDao.listar();
		request.setAttribute("listaHT", listaHT);
	}
	
	protected void listarEspecialidadesMedico(HttpServletRequest request) {
		MedicoDAOImpl espMedDao = new MedicoDAOImpl();
		ArrayList<Medico> listaEspecialidadesMedico = (ArrayList<Medico>) espMedDao.listarEspecialidadesMedico();
		request.setAttribute("listaEspMedico", listaEspecialidadesMedico);
	}
	
	protected void agregarListaPaises(HttpServletRequest request) {
		PaisDAOImpl paisDao = new PaisDAOImpl();
		ArrayList<Pais> listaPaises = (ArrayList<Pais>) paisDao.listarPaises();
		request.setAttribute("listaPaises", listaPaises);
	}
	
	protected void agregarListaEspecialidades(HttpServletRequest request) {
		EspecialidadesDAOImpl espDao = new EspecialidadesDAOImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>) espDao.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
}
