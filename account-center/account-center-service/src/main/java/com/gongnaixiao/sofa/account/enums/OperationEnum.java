package com.gongnaixiao.sofa.account.enums;

public enum OperationEnum {
    
    CREDIT("TransferIn"),

	DEBIT("TransferOut");

	private String code;

	OperationEnum(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}