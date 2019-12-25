package tk.xdevcloud.paygate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class PayGateReturnServlet
 */
@WebServlet(name = "PayGateReturn", urlPatterns = "PayGateReturn", loadOnStartup = 1)
public class PayGateReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayGateReturnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			//get transaction status and determine the description
			int transactionStatusCode = Integer.parseInt(request.getParameter("TRANSACTION_STATUS"));
			response.getWriter().println(Helper.convertTransactionCodeToDescription(transactionStatusCode));	
			
		}
		catch(Exception e ) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Internal Server Error");
		}
		
	}
}
