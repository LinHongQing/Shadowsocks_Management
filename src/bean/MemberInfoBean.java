package bean;

import utils.ClassReflectUtil;

public class MemberInfoBean {
	private String username = null;
	private String email = null;
	private String pass = null;
	private String passwd = null;
	private int id = ClassReflectUtil._INVALID_NUM_;
	private int port = ClassReflectUtil._INVALID_NUM_;
	private int t_registry = ClassReflectUtil._INVALID_NUM_;
	private long transfer_enable = ClassReflectUtil._INVALID_NUM_;
	private int is_enabled = ClassReflectUtil._INVALID_NUM_;
	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
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
	 * @return the pass
	 */
	public final String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public final void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the passwd
	 */
	public final String getPasswd() {
		return passwd;
	}
	/**
	 * @param passwd the passwd to set
	 */
	public final void setPasswd(String passwd) {
		this.passwd = passwd;
	}
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
	/**
	 * @return the t_registry
	 */
	public final int getT_registry() {
		return t_registry;
	}
	/**
	 * @param t_registry the t_registry to set
	 */
	public final void setT_registry(int t_registry) {
		this.t_registry = t_registry;
	}
	/**
	 * @return the transfer_enable
	 */
	public final long getTransfer_enable() {
		return transfer_enable;
	}
	/**
	 * @param transfer_enable the transfer_enable to set
	 */
	public final void setTransfer_enable(long transfer_enable) {
		this.transfer_enable = transfer_enable;
	}
	/**
	 * @return the is_enabled
	 */
	public final int getIs_enabled() {
		return is_enabled;
	}
	/**
	 * @param is_enabled the is_enabled to set
	 */
	public final void setIs_enabled(int is_enabled) {
		this.is_enabled = is_enabled;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MemberInfoBean [username=" + username + ", email=" + email + ", pass=" + pass + ", passwd=" + passwd
				+ ", id=" + id + ", port=" + port + ", t_registry=" + t_registry + ", transfer_enable="
				+ transfer_enable + ", is_enabled=" + is_enabled + "]";
	}
}
