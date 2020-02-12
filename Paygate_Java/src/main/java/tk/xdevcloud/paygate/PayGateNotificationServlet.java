package tk.xdevcloud.paygate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/**
 * Servlet implementation class PayGateNotify
 *
 * This Servlet class process the request received from PayGate Web Return
 */
@WebServlet(name = "PayGateNotify", urlPatterns = "PayGateNotify", loadOnStartup = 1)
public class PayGateNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayGateNotificationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			response.setContentType("text/plain");
			// get the request values from PayGate
			String payGateId = request.getParameter("PAYGATE_ID");
			String payRequestId = request.getParameter("PAY_REQUEST_ID");
			String reference = request.getParameter("REFERENCE");
			Integer transactionStatus = Integer.valueOf(request.getParameter("TRANSACTION_STATUS"));
			Integer resultCode = Integer.parseInt(request.getParameter("RESULT_CODE"));
			String currency = request.getParameter("CURRENCY");
			String authCode = request.getParameter("AUTH_CODE");
			Integer amount = Integer.parseInt(request.getParameter("AMOUNT"));
			String resultDescription = request.getParameter("RESULT_DESC");
			Integer transactionId = Integer.parseInt(request.getParameter("TRANSACTION_ID"));
			String riskIndicator = request.getParameter("RISK_INDICATOR");
			String payMethod = request.getParameter("PAY_METHOD");
			String payMethodDetail = request.getParameter("PAY_METHOD_DETAIL");
			String paygateChecksum = request.getParameter("CHECKSUM");

			// Get the request values concatenated string and must be in order according to
			// PayGate docs

			StringBuffer requestConcatValues = new StringBuffer();
			requestConcatValues.append(payGateId).append(payRequestId).append(reference)
					.append(String.valueOf(transactionStatus)).append(String.valueOf(resultCode)).append(authCode)
					.append(currency).append(String.valueOf(amount)).append(resultDescription)
					.append(String.valueOf(transactionId)).append(riskIndicator).append(payMethod)
					.append(payMethodDetail);

			// Calculate the check sum
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// Gets PayGate secret
			String payGateSecret = (String) Config.getValue("paygate_secret");
			requestConcatValues.append(payGateSecret);
			digest.update(requestConcatValues.toString().getBytes());
			String checkSum = DatatypeConverter.printHexBinary(digest.digest()).toString().toLowerCase();
			// Security check
			// Verify that request actually came from PayGate
			if ((!payGateId.equals((String) Config.getValue("paygate_id")) || (!checkSum.equals(paygateChecksum)))) {

				throw new ForbiddenException("Access Denied");
			}
			// store the request values
			PayGateResult payGateResult = new PayGateResult();
			payGateResult.setPayRequestId(payRequestId);
			payGateResult.setReference(reference);
			payGateResult.setTransactionStatus(transactionStatus);
			payGateResult.setResultCode(resultCode);
			payGateResult.setCurrency(currency);
			payGateResult.setAuthCode(authCode);
			payGateResult.setAmount(amount);
			payGateResult.setResultDescription(resultDescription);
			payGateResult.setTransactionId(transactionId);
			payGateResult.setRiskIndicator(riskIndicator);
			payGateResult.setPayMethod(payMethod);
			payGateResult.setPayMethodDetail(payMethodDetail);

			Helper.saveTransactions(payGateResult);
			// Respond back to PayGate server that everything went well
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("OKAY");
		}

		catch (ForbiddenException exception) {
			// Throw 403 when request not coming from PayGate
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().println(exception.getMessage());

		}

		catch (Exception exception) {
			// Throw 500 for other errors
			exception.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Internal Server Error");
		}

	}

}
