package com.github.kwesidev.paygate;

public enum PaymentMethod {
	CREDIT_CARD("CC"), BANK_TRANSFER("BT"), DEBIT_CARD("DC"), PREPAID_CARD("PC"), CASH_VOUCHER("CV");
	private String code;
	PaymentMethod(String code) {
		this.code = code;
	}
	public String toString() {
		return code;
	}
}