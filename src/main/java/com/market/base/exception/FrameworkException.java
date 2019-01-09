package com.market.base.exception;

/**
 * <p>框架异常</p>
 */
public class FrameworkException extends BaseException {

    private static final long serialVersionUID = -815529553760287172L;

    public FrameworkException() {
		super();
	}

	public FrameworkException(String message, Throwable cause) {
		super("[FrameworkException]" + message, cause);
	}

	public FrameworkException(String message) {
		super("[FrameworkException]" + message);
	}

	public FrameworkException(Throwable cause) {
		super("[FrameworkException]", cause);
	}

}
