package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.bean.Notifica;
import model.manager.NotificaManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class PrelevaNotificheControl
 */
@WebServlet("/PrelevaNotificheControl")
public class PrelevaNotificheControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrelevaNotificheControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm=new DriverManagerConnectionPool();
		NotificaManagement notMan=new NotificaManagement(dm);
		
		try {
			ArrayList<Notifica> notificheList=(ArrayList<Notifica>)notMan.doRetrieveByEmail(request.getParameter("e_mail"));
			String jsonNotifiche=new Gson().toJson(notificheList);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(jsonNotifiche);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}