package com.market.base.exception;

/**
 * <p>业务异常</p>
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 7496989777267878889L;

    public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
