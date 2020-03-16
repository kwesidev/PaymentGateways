<%@ page
	import="tk.xdevcloud.paygate.PayGateWeb,
tk.xdevcloud.paygate.PayGateWebResult,tk.xdevcloud.paygate.Config,
java.security.SecureRandom"
	contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>PayGate Web Example</title>
</head>

<%
	// Init values

	// Generate random values
	SecureRandom random = new SecureRandom();
	random.setSeed(random.generateSeed(20));

	String returnURL = (String) Config.getValue("return_url"); // Load return url from config
	String notifyURL = (String) Config.getValue("notify_url"); // Lad notify url from config
	String payGateId = (String) Config.getValue("paygate_id"); // PayGate ID  provided by PayGate
	String payGateSecret = (String) Config.getValue("paygate_secret"); // PayGate secret for calculating checksums

	double amount = 400.5 + random.nextDouble(); // Price of the item 
	String emailAddress = "willzako@aol.com"; // Email address of the payer
	String reference = "ORD_" + random.nextInt(5000); // such as order id, invoice id etc  
	PayGateWeb payweb = new PayGateWeb.PayGateWebBuilder().reference(reference).amount(amount)
			.emailAddress(emailAddress).payGateId(payGateId).payGateSecret(payGateSecret).notifyUrl(notifyURL)
			.returnUrl(returnURL).build();
	PayGateWebResult paygateWebResult = payweb.doRequest();
%>

<div align="center">
	<p>
		Order Number :
		<%=reference%></p>
	<p>
		Amount Due:
		<%=String.format("%.2f", amount)%>
	</p>
	<button onclick="document.form1.submit();">Pay With PayGate</button>
</div>


<form action="https://secure.paygate.co.za/payweb3/process.trans"
	method="POST" name="form1">
	<input type="hidden" name="PAY_REQUEST_ID"
		value="<%=paygateWebResult.getPayRequestId()%>"> <input
		type="hidden" name="CHECKSUM"
		value="<%=paygateWebResult.getChecksum()%>">

</form>
</body>

</html>