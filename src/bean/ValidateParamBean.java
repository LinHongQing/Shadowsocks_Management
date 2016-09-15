package bean;

import utils.ClassReflectUtil;

public class ValidateParamBean {
	private int id = ClassReflectUtil._INVALID_NUM_;
	private String email = null;
	private int port = ClassReflectUtil._INVALID_NUM_;
	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public final void setPort(int port) {
		this.port = port;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidateParamBean [id=" + id + ", email=" + email + ", port=" + port + "]";
	}


}
