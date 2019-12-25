package tk.xdevcloud.paygate;

/*
 * This class holds the response data sent by PayGate after completing the transactions
 * @author kwesidev
 */
public class PayGateResult {

	private String payRequestId;
	private String reference;
	private Integer transactionStatus;
	private Integer resultCode;
	private String authCode;
	private String currency;
	private Integer amount;
	private String resultDescription;
	private Integer transactionId;
	private String riskIndicator;
	private String payMethod;
	private String payMethodDetail;
	
    /*
	/**
	 * Constructor
	 * 
	 * 
	 * @param reference        This should be the same reference that was passed in the request;
	 * @param transactionStatus 
	 * @param resultCode        a Integer
	 * @param authCode          a String
	 * @param currency          a String
	 * @param amount            a Integer
	 * @param resultDescription a String
	 * @param transactionId     a Integer
	 * @param riskIndicator     a String
	 * @param payMethod         a String
	 * @param payMethodDetail   a String
	
	public PayGateResult() {

	}
	
	*/
	/**
	 * @return the payRequestId
	 */
	public String getPayRequestId() {
		return payRequestId;
	}

	/**
	 * @param payRequestId 
	 */
	public void setPayRequestId(String payRequestId) {
		this.payRequestId = payRequestId;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the transactionStatus
	 */
	public int getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param transactionStatus 
	 */
	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return the resultCode
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency Currency code of the currency the customer is paying in
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount 
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the resultDescription
	 */
	public String getResultDescription() {
		return resultDescription;
	}

	/**
	 * @param resultDescription the resultDescription to set
	 */
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 * @return the transactionId
	 */
	public Integer getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the riskIndicator
	 */
	public String getRiskIndicator() {
		return riskIndicator;
	}

	/**
	 * @param riskIndicator the riskIndicator to set
	 */
	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}

	/**
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return the payMethodDetail
	 */
	public String getPayMethodDetail() {
		return payMethodDetail;
	}

	/**
	 * @param payMethodDetail the payMethodDetail to set
	 */
	public void setPayMethodDetail(String payMethodDetail) {
		this.payMethodDetail = payMethodDetail;
	}

}
