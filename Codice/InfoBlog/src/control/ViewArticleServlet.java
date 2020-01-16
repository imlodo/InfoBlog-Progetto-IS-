package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Articolo;
import model.manager.ArticoloManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class ViewArticleServlet
 */
@WebServlet("/ViewArticleServlet")
public class ViewArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		ArticoloManagement DAOArticolo=new ArticoloManagement(new DriverManagerConnectionPool());
		Articolo articolo=null;
		String url="SingleArticle.jsp";
		
		try
		{
			articolo=(Articolo)DAOArticolo.doRetrieveByKey(id);
			request.setAttribute("articolo",articolo);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		RequestDispatcher requestDispatcher=request.getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

}
