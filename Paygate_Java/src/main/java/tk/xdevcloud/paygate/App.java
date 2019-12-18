package tk.xdevcloud.paygate;
public class App {
	
    public static void main(String[] args) throws PayGateException {
       
       PayGateWeb pweb = new PayGateWeb();
       PayGateWebResult pWebResult = pweb.doRequest();
       System.out.println("PayGateId:" + pWebResult.getPayGateId());
       System.out.println("PayRequestId:" + pWebResult.getPayRequestId());
       System.out.println("Reference:" + pWebResult.getReference());
       System.out.println("CheckSum:" + pWebResult.getChecksum());
    }
}
