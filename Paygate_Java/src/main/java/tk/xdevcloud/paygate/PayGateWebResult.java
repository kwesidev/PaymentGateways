package tk.xdevcloud.paygate;
/**
 * 
 * @author kwesidev
 *
 */
public class PayGateWebResult {
	// Details
	private String payGateId;
	private String checksum;
	private String payRequestId;
	private String reference;
	
	public PayGateWebResult() {
		this.payGateId = "";
		this.checksum = "";
		this.payRequestId = "";
		this.reference = "";
	}
	
	/**
	 * @param payGateId id that was sent along the request
	 * @param checksum  hash posted back from paygate 
	 * @param payRequestId payment request id 
	 * @param reference payment reference such as invoince id etc 
	 */
	public PayGateWebResult(String payGateId,String checksum,String payRequestId,String reference) {
		this.payGateId = payGateId;
		this.checksum = checksum;
		this.payRequestId = payRequestId;
		this.reference = reference;
	}
	/**
	 * @return the payGateId
	 */
	public String getPayGateId() {
		return payGateId;
	}
	/**
	 * @param payGateId the payGateId to set
	 * @return this
	 */
	public PayGateWebResult setPayGateId(String payGateId) {
		this.payGateId = payGateId;
		return this;
	}
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 * @return this
	 */
	public PayGateWebResult setChecksum(String checksum) {
		this.checksum = checksum;
		return this;
	}
	/**
	 * @return the payRequestId
	 */
	public String getPayRequestId() {
		return payRequestId;
	}
	/**
	 * @param payRequestId the payRequestId to set
	 * @return this
	 */
	public PayGateWebResult setPayRequestId(String payRequestId) {
		this.payRequestId = payRequestId;
		return this;
	}
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 * @return this
	 */
	public PayGateWebResult setReference(String reference) {
		this.reference = reference;
		return this;
	}

}
