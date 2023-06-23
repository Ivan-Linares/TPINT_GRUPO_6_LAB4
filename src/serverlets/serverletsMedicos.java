package serverlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import dao.impl.PacienteDAOImpl;
import dao.impl.PaisDAOImpl;
import dominio.Cobertura;
import dominio.Domicilio;
import dominio.Especialidad;
import dominio.HorariosTrabajo;
import dominio.Medico;
import dominio.Paciente;
import dominio.Pais;
import dominio.Telefono;
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
			
			if(request.getParameter("btn-nuevo-medico") != null) {
				agregarListaPaises(request);
				agregarListaEspecialidades(request);
				RequestDispatcher rd = request.getRequestDispatcher("InsertarMedico.jsp");
				rd.forward(request, response);
			}

			else {
				listarMedicos(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
				rd.forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String dniMedico ="";
		 System.out.println("entro");
		if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico").toString();

		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		if(request.getParameter("btn-agregar-medico") != null) {
			 System.out.println("entro2");
			try {
				if(AgregarMedico(medicoDao, request)) {
					listarMedicos(request);
					
					RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
					rd.forward(request, response);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(request.getParameter("btn-ver-medico") != null) {
			Medico medico = medicoDao.obtenerMedico(dniMedico);
			request.setAttribute("medico", medico);
			agregarListaPaises(request);
			agregarListaEspecialidades(request);
			RequestDispatcher rd = request.getRequestDispatcher("EditarMedico.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-editar-medico") != null) {
			
		}
		else if(request.getParameter("btn-eliminar-medico") != null) {
			EliminarMedico(medicoDao, dniMedico);
			
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
	
	protected boolean AgregarMedico(MedicoDAOImpl mDao, HttpServletRequest request) throws ParseException {
		System.out.println("entro agregar medico");
		Medico nuevoMedico = new Medico();
		nuevoMedico.setDni(request.getParameter("dni").toString());
		nuevoMedico.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fechaNacimiento").toString()));
		nuevoMedico.setSexo(request.getParameter("sexoSelect"));
		nuevoMedico.setNombre(request.getParameter("nombre"));
		nuevoMedico.setApellido(request.getParameter("apellido"));
		
		Pais nacionalidad = new Pais();
		nacionalidad.setIdPais(Integer.parseInt(request.getParameter("nacionalidadSelect").toString()));
		nuevoMedico.setNacionalidad(nacionalidad);
		nuevoMedico.setCorreo(request.getParameter("correo"));
		
		Domicilio domicilioMedico = new Domicilio();
		domicilioMedico.setDireccion(request.getParameter("direccion"));
		domicilioMedico.setLocalidad(request.getParameter("localidad"));
		domicilioMedico.setProvincia(request.getParameter("provincia"));
		
		nuevoMedico.setDomicilio(domicilioMedico);
		System.out.println("salio agregar medico");
		boolean agregado = mDao.agregar(nuevoMedico);
		if(agregado) request.setAttribute("idMedico", 1); //aca tiene que retornar el id Medico y se lo asignamos
		return agregado;
	}
	
	protected boolean EliminarMedico(MedicoDAOImpl mDao, String dniMedico) {		
		return mDao.eliminar(dniMedico);	
	}
}
