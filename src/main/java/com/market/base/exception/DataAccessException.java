package com.market.base.exception;

/**
 * <p>数据访问异常</p>
 */
public class DataAccessException extends BaseException {


    private static final long serialVersionUID = -782310039120173223L;

    public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super("[DataAccessException]" + message, cause);
	}

	public DataAccessException(String message) {
		super("[DataAccessException]" + message);
	}

	public DataAccessException(Throwable cause) {
		super("[DataAccessException]", cause);
	}

}
