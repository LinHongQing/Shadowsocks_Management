package bean;

public class LoginPageBean {
	private String email = null;
	private String pass = null;
	private String verifycode = null;
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
	 * @return the password
	 */
	public final String getPass() {
		return pass;
	}
	/**
	 * @param password the password to set
	 */
	public final void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the checkcode
	 */
	public final String getVerifycode() {
		return verifycode;
	}
	/**
	 * @param checkcode the checkcode to set
	 */
	public final void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginPageBean [email=" + email + ", pass=" + pass + ", verifycode=" + verifycode + "]";
	}
}
