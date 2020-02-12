package tk.xdevcloud.paygate;

import java.net.URL;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PayGate Web Implementation
 * 
 * @author kwesidev
 */
public class PayGateWeb {
	// merchant Details
	private String payGateID;
	private String payGateSecret;
	private String reference;
	private int amount;
	private String emailAddress;
	private String returnUrl;
	private String notifyUrl;

	private static final String PAYGATE_WEB_URL = "https://secure.paygate.co.za/payweb3/initiate.trans";

	private enum PaymentMethod {
		CC("cc"), EFT("eft");

		private String code;

		PaymentMethod(String code) {
			this.code = code;
		}

		public String getCode() {

			return code;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param payGateId     assigned by PayGate
	 * @param payGateSecret
	 * @param reference     This is your reference number for use by your internal
	 *                      systems
	 * @param amount        Transaction amount in cents. e.g. 32.99 is specified as
	 *                      3299
	 * @param emailAddres   customer email
	 * @param notifyUrl     if payment is completed result will be posted to this
	 *                      url
	 */
	public PayGateWeb(String payGateId, String payGateSecret, String reference, Double amount, String emailAddress,
			String notifyUrl, String returnUrl) {

		this.payGateID = payGateId;
		this.payGateSecret = payGateSecret;
		this.reference = reference;
		this.amount = (int) (amount * 100);
		this.emailAddress = emailAddress;
		this.notifyUrl = notifyUrl;
		this.returnUrl = returnUrl;

	}

	public PayGateWeb() {

		this.payGateID = "10011072130";
		this.payGateSecret = "secret";
		this.reference = "testing";
		this.amount = 900;
		this.emailAddress = "willzako@aol.com";
		this.notifyUrl = "https://apps.xdevcloud.tk/gateway/notify_payment";
		this.returnUrl = "http://xdevcloud.tk";
	}

	// getters and settters left out

	/**
	 * Makes request to PayPagte Engine EndPoint so that the return values can be
	 * use to redirect customer to PayGate web to make payment return
	 * PayGateWebResult
	 */

	public PayGateWebResult doRequest() throws PayGateException {

		URL url = null;
		HttpURLConnection conn = null;
		OutputStreamWriter streamWriter = null;
		BufferedReader buffReader = null;
		PayGateWebResult payGateResult = new PayGateWebResult();

		try {
			// Init the request
			url = new URL(PAYGATE_WEB_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// We making a form post
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			String requestData = "";
			// Request Values without spaces
			String requestValues = "";

			// Date format for transaction date
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss");

			// Request data to be posted
			Map<String, String> requestMap = new LinkedHashMap<String, String>();
			requestMap.put("PAYGATE_ID", this.payGateID.trim());
			requestMap.put("REFERENCE", this.reference.trim());
			requestMap.put("AMOUNT", String.valueOf(this.amount));
			requestMap.put("CURRENCY", "ZAR");
			requestMap.put("RETURN_URL", this.returnUrl.trim());
			requestMap.put("TRANSACTION_DATE", localDate.format(dateFormat));
			requestMap.put("LOCALE", "en-ZA");
			requestMap.put("COUNTRY", "ZAF");
			requestMap.put("EMAIL", this.emailAddress.trim());
			requestMap.put("NOTIFY_URL", this.notifyUrl.trim());

			// Build a form string and string to calculate checksum
			for (Map.Entry<String, String> reqValue : requestMap.entrySet()) {

				requestData += (String.format("%s=%s&", reqValue.getKey(),
						URLEncoder.encode(reqValue.getValue(), "UTF-8")));
				requestValues += reqValue.getValue();
			}
			// Add secret key to requestValues
			requestValues += this.payGateSecret.trim();
			MessageDigest checksum = MessageDigest.getInstance("MD5");

			checksum.update(requestValues.toString().getBytes("UTF-8"));
			// Convert the calculate data to hex
			String checkSumHash = DatatypeConverter.printHexBinary(checksum.digest()).toString();

			// Add it to the requestData
			requestData += "CHECKSUM=" + checkSumHash;
			System.out.println(requestData);

			// Sets request content length
			conn.setRequestProperty("Content-Length", String.valueOf(requestData.length()));
			conn.setRequestMethod("POST");
			HttpURLConnection.setFollowRedirects(true);

			// Writes request to connection
			streamWriter = new OutputStreamWriter(conn.getOutputStream());
			streamWriter.write(requestData.toString());
			streamWriter.flush();

			// Display response from server
			// Headers and actual response from PayGate
			// For debugging purposes can be commented out
			for (Map.Entry<String, List<String>> headerFields : conn.getHeaderFields().entrySet()) {

				System.out.println(headerFields.getKey() + " " + headerFields.getValue());
			}

			buffReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer responseData = new StringBuffer();
			String oneLine = "";
			while ((oneLine = buffReader.readLine()) != null) {

				responseData.append(oneLine);
			}
			if (responseData.toString().startsWith("ERROR")) {

				throw new PayGateException(responseData.toString());
			}
			// Paygate is returned in this format so it needs to be split
			// PAYGATE_ID=10011072130&PAY_REQUEST_ID=23B785AE-C96C-32AF-4879-D2C9363DB6E8&REFERENCE=pgtest_123456789&CHECKSUM=b41a77f83a275a849f23e30b4666e837
			String[] payGateResultValues = new String[4];
			int count = 0;
			System.out.println(responseData.toString());
			for (String res : responseData.toString().split("&")) {
				// Get equal sign position and copy the values
				int equalPos = res.indexOf("=");
				payGateResultValues[count] = res.substring(equalPos + 1, res.length());
				count++;
			}
			// Save the result into the PayGateWebResult Object
			payGateResult.setPayGateId(payGateResultValues[0]).setPayRequestId(payGateResultValues[1])
					.setReference(payGateResultValues[2]).setChecksum(payGateResultValues[3]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}

		catch (IOException e) {

			e.printStackTrace();
		}
		// Free resource
		finally {
			if (streamWriter != null) {
				try {
					streamWriter.close();
				}

				catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (buffReader != null) {
				try {

					buffReader.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}
		return payGateResult;
	}

}
