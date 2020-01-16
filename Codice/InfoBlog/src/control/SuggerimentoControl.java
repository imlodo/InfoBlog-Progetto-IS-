package control;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.bean.Articolo;
import model.bean.Autore;
import model.manager.ArticoloManagement;
import model.manager.AutoreManagement;
import storage.DriverManagerConnectionPool;

/**
 * Servlet implementation class SuggerimentoControl
 */
@WebServlet("/SuggerimentoControl")
public class SuggerimentoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuggerimentoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String type = request.getParameter("type");
		if(type == null)
		{
			response.sendRedirect("notfound.jsp");
			return;
		}
        ArrayList<String> sugg = new ArrayList<String>();
        DriverManagerConnectionPool pool = new DriverManagerConnectionPool();
		switch(type)
		{
			case "articolo": 
			{
				ArticoloManagement articoloDM = new ArticoloManagement(pool);
				try
				{
					ArrayList<Articolo> articoli = (ArrayList<Articolo>) articoloDM.doRetrieveAll("u:");
					for(Articolo a : articoli)
						sugg.add(a.getTitolo());
				}
				catch (SQLException e)
				{
					
					e.printStackTrace();
				}
			}break;
			case "autore":
			{
				AutoreManagement autoreDM = new AutoreManagement(pool);
				try
				{
					ArrayList<Autore> autori = (ArrayList<Autore>) autoreDM.doRetrieveAll("nome");
					for(Autore a: autori)
					{
						sugg.add(a.getNome());
						sugg.add(a.getCognome());
					}
						
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}break;
		}
		response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        String jsonData = gson.toJson(sugg);
        PrintWriter out = response.getWriter();
        try 
        {
            out.println(jsonData);
        } 
        finally 
        {
            out.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
