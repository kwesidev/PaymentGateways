<%@ page import="tk.xdevcloud.paygate.PayGateWeb,tk.xdevcloud.paygate.PayGateWebResult,tk.xdevcloud.paygate.Config" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Payment Gate Web Example</title>
</head>
<body>
 <% 
     // init values
     String returnURL = "http://v.xdevcloud.tk/PayGate_Java/PayGateReturn"; // if hosting this app set it to https://yourserver/PayGate_Java/PayGateReturn
     String notifyURL = "http://v.xdevcloud.tk/PayGate_Java/PayGateNotify"; // ... https://yourserver/PayGate_Java/PayGateNotify 
     String payGateId = (String)Config.getValue("paygate_id"); // PayGate ID  provided by PayGate
     String payGateSecret = (String)Config.getValue("paygate_secret"); // PayGate secret for calculating checksums

     Double amount = 400.5; // price of the item 
     String emailAddress = "willzako@aol.com"; // email address of the payer
     String reference = "ML90"; // such as order id, invoice id etc  
     PayGateWebResult paygateWebResult = new PayGateWeb(payGateId,payGateSecret,reference,amount,emailAddress,notifyURL,returnURL).doRequest();
 %>
  <div align="center">
     <button onclick="document.form1.submit();">Pay With PayGate</button>
    
  </div>
  
  <form action="https://secure.paygate.co.za/payweb3/process.trans" method="POST"  name="form1">
    <input type="hidden" name="PAY_REQUEST_ID" value="<%= paygateWebResult.getPayRequestId() %>">
    <input type="hidden" name="CHECKSUM" value="<%= paygateWebResult.getChecksum()%>">
    
  </form>
</body>

</html>