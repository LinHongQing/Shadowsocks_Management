package pojo;

public class Login {
	private int id;
	private int userid;
	private String loginipaddress;
	private int logintime;
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
	 * @return the userid
	 */
	public final int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public final void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the loginipaddress
	 */
	public final String getLoginipaddress() {
		return loginipaddress;
	}
	/**
	 * @param loginipaddress the loginipaddress to set
	 */
	public final void setLoginipaddress(String loginipaddress) {
		this.loginipaddress = loginipaddress;
	}
	/**
	 * @return the logintime
	 */
	public final int getLogintime() {
		return logintime;
	}
	/**
	 * @param logintime the logintime to set
	 */
	public final void setLogintime(int logintime) {
		this.logintime = logintime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Login [id=" + id + ", userid=" + userid + ", loginipaddress=" + loginipaddress + ", logintime="
				+ logintime + "]";
	}
}
