package tk.xdevcloud.paygate;

import java.util.Arrays;
import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Helper class
 * @author kwesidev
 */
public class Helper {

	/**
	 * Methods saves PayGate transaction to database
	 * 
	 * @param payGateResult object
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void saveTransactions(PayGateResult payGateResult) throws SQLException, IOException {


		StringBuffer queryString = new StringBuffer();
		// build a query string
		queryString.append("INSERT INTO paygate_transactions(pay_request_id,reference,transaction_status,");
		queryString.append("result_code,auth_code,currency,amount,result_description,transaction_id,");
		queryString.append("risk_indicator,pay_method,pay_method_detail,date_time) ");
		queryString.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW());");
		// Init connection
		try (Connection pgSQLConn = PostgreSQLConnection.getConnection()) {
			// Prepare statement to be executed
			try (PreparedStatement paymentInfoStatement = pgSQLConn.prepareStatement(queryString.toString())) {

				paymentInfoStatement.setString(1, payGateResult.getPayRequestId()); // PAY_REQUEST_ID
				paymentInfoStatement.setString(2, payGateResult.getReference()); // REFERENCE
				paymentInfoStatement.setInt(3, payGateResult.getTransactionStatus()); // TRANSACTION_STATUS
				paymentInfoStatement.setInt(4, payGateResult.getResultCode()); // RESULT_CODE
				paymentInfoStatement.setString(5, payGateResult.getAuthCode()); // AUTH_CODE
				paymentInfoStatement.setString(6, payGateResult.getCurrency()); // CURRENCY
				paymentInfoStatement.setInt(7,payGateResult.getAmount()); // AMOUNT
				paymentInfoStatement.setString(8, payGateResult.getResultDescription()); // RESULT_DESC
				paymentInfoStatement.setInt(9, payGateResult.getTransactionId()); // TRANSACTION ID
				paymentInfoStatement.setString(10, payGateResult.getRiskIndicator()); // RISK INDICATOR
				paymentInfoStatement.setString(11, payGateResult.getPayMethod()); // PAYMENT METHOD
				paymentInfoStatement.setString(12, payGateResult.getPayMethodDetail()); // PAYMENT_METHOD_DETAIL
				// If executed without any errors
				if (paymentInfoStatement.execute()) {
					System.out.println("Saved");

				}

			}
		}

	}

	/**
	 * Method to Determine the type of Transaction status received from Paygate
	 * 
	 * @param code use to determine the type of Description
	 * @return
	 */
	public static String convertTransactionCodeToDescription(int code) {

		String description = "";

		switch (code) {

		case 0:
			description = "Not Done";
			break;
		case 1:
			description = "Approved";
			break;
		case 2:
			description = "Declined";
			break;
		case 3:
			description = "Cancelled";
			break;
		case 4:
			description = "User Cancelled";
			break;

		case 5:
			description = "Received By Paygate";
			break;
		case 7:
			description = "Settlement Voided";
			break;
		default:
			description = "Uknown";
		}

		return description;
	}

	/**
	 * split request params 
	 * @param requestData
	 * @return
	 */
	public static String[] splitResult(String responseData) {

		return Arrays.stream(responseData.split("&")).map(e -> e.substring(e.indexOf("=") + 1, e.length()))
				.toArray(String[]::new);

	}

}
