package pojo;

import utils.ClassReflectUtil;

public class User {

	private int id = ClassReflectUtil._INVALID_NUM_;
	private String username = null;
	private String email = null;
	private String pass = null;
	private String passwd = null;
	private int t_registry = ClassReflectUtil._INVALID_NUM_;
	private int t = ClassReflectUtil._INVALID_NUM_;
	private long u = ClassReflectUtil._INVALID_NUM_;
	private long d = ClassReflectUtil._INVALID_NUM_;
	private long transfer_enable = ClassReflectUtil._INVALID_NUM_;
	private int port = ClassReflectUtil._INVALID_NUM_;
	private int is_switched_on = ClassReflectUtil._INVALID_NUM_;
	private int is_enabled = ClassReflectUtil._INVALID_NUM_;
	
	public User() {
		super();
	}

	public User(String username, String email, String pass, String passwd, int t_registry, long transfer_enable, int port, int is_enabled) {
		super();
		this.id = ClassReflectUtil._INVALID_NUM_;

		this.t = ClassReflectUtil._INVALID_NUM_;
		this.u = ClassReflectUtil._INVALID_NUM_;
		this.d = ClassReflectUtil._INVALID_NUM_;
		this.is_switched_on = ClassReflectUtil._INVALID_NUM_;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.passwd = passwd;
		this.t_registry = t_registry;
		this.transfer_enable = transfer_enable;
		this.port = port;
		this.is_enabled = is_enabled;
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
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
	 * @param email
	 *            the email to set
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
	 * @param pass
	 *            the pass to set
	 */
	public final void setPass(String pass) {
		this.pass = pass;
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
	 * @return the passwd
	 */
	public final String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public final void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the t
	 */
	public final int getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public final void setT(int t) {
		this.t = t;
	}

	/**
	 * @return the u
	 */
	public final long getU() {
		return u;
	}

	/**
	 * @param u
	 *            the u to set
	 */
	public final void setU(long u) {
		this.u = u;
	}

	/**
	 * @return the d
	 */
	public final long getD() {
		return d;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public final void setD(long d) {
		this.d = d;
	}

	/**
	 * @return the transfer_enable
	 */
	public final long getTransfer_enable() {
		return transfer_enable;
	}

	/**
	 * @param transfer_enable
	 *            the transfer_enable to set
	 */
	public final void setTransfer_enable(long transfer_enable) {
		this.transfer_enable = transfer_enable;
	}

	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public final void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the is_switched_on
	 */
	public final int getIs_switched_on() {
		return is_switched_on;
	}

	/**
	 * @param is_switched_on
	 *            the is_switched_on to set
	 */
	public final void setIs_switched_on(int is_switched_on) {
		this.is_switched_on = is_switched_on;
	}

	/**
	 * @return the is_enabled
	 */
	public final int getIs_enabled() {
		return is_enabled;
	}

	/**
	 * @param is_enabled
	 *            the is_enabled to set
	 */
	public final void setIs_enabled(int is_enabled) {
		this.is_enabled = is_enabled;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", pass=" + pass + ", passwd="
				+ passwd + ", t_registry=" + t_registry + ", t=" + t + ", u=" + u + ", d=" + d + ", transfer_enable="
				+ transfer_enable + ", port=" + port + ", is_switched_on=" + is_switched_on + ", is_enabled="
				+ is_enabled + "]";
	}

}