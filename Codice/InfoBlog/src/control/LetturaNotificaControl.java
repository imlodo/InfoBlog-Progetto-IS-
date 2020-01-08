package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class LetturaNotificaControl
 */
@WebServlet("/LetturaNotificaControl")
public class LetturaNotificaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LetturaNotificaControl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		NotificaManagement notMan=new NotificaManagement(dm);
	
		try {
			notMan.leggiNotifica(Integer.valueOf(request.getParameter("idNotifica")));
			
		    response.sendRedirect(request.getContextPath()+"/notifica.html");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
