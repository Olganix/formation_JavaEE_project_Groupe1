package fr.dawan.nogashi.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import fr.dawan.nogashi.enums.RestResponseStatus;





@Component
public class RestResponse implements Serializable								//Class for Rest communication, for response specially.
{
	private static final long serialVersionUID = 1L;
	
	private RestResponseStatus status;
	private Object data = null;
	private int errorCode = -1;													//for warning and error
	private String errormessage = "";											//for specific informations.
	
	
	public RestResponse() {
		super();
	}

	public RestResponse(RestResponseStatus status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public RestResponse(RestResponseStatus status, Object data, int errorCode, String errormessage) {
		super();
		this.status = status;
		this.data = data;
		this.errorCode = errorCode;
		this.errormessage = errormessage;
	}

	public RestResponseStatus getStatus() {
		return status;
	}

	public void setStatus(RestResponseStatus status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
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
