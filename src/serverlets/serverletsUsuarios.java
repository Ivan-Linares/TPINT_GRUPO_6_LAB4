package serverlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.UsuarioDAOImpl;
import dominio.Usuario;

@WebServlet("/serverletsUsuarios")
public class serverletsUsuarios extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("btn-guardar-usuario-medico") != null) {
			int idUsuario = 0;
			if(request.getParameter("idUsuario") != null) idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
			
			Usuario usuario = new Usuario();
			UsuarioDAOImpl uDao = new UsuarioDAOImpl();
			usuario.setId(Integer.parseInt(request.getParameter("idUsuario").toString()));
			usuario.setDni(request.getParameter("dniMedico"));
			usuario.setCorreo(request.getParameter("correo"));
			usuario.setPassword(request.getParameter("password"));
			String estado = "";
			if(uDao.modificar(usuario)) {
				estado = "El Usuario del Medico fue modificado con exito!";
				request.setAttribute("estadoUsuarioMedico", estado);			
				String dniMedico = "";
				if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico");
				int idMedico = 0;
				if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));
				RequestDispatcher rd = request.getRequestDispatcher("serverletsMedicos?method=post&dniMedico="+ dniMedico+"&idMedico="+idMedico+"&btn-editar-medico=");
				rd.forward(request, response);
			}
		}
		
	}

}


