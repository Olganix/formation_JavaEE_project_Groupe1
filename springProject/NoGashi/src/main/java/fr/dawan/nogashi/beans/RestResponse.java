package fr.dawan.nogashi.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.RestResponseStatus;





@Component
public class RestResponse<T> implements Serializable								//Class for Rest communication, for response specially.
{
	private static final long serialVersionUID = 1L;
	
	private RestResponseStatus status;
	private T data = null;
	private int errorCode = -1;													//for warning and error
	private String errormessage = "";											//for specific informations.
	
	
	public RestResponse() {
		super();
	}

	public RestResponse(RestResponseStatus status, T data) {
		super();
		this.status = status;
		this.data = data;
	}

	public RestResponse(RestResponseStatus status, T data, int errorCode, String errormessage) {
		this(status, data);
		this.errorCode = errorCode;
		this.errormessage = errormessage;
	}

	public RestResponseStatus getStatus() {
		return status;
	}

	public void setStatus(RestResponseStatus status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}	
}
