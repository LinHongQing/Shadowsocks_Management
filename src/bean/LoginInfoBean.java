package bean;

import utils.ClassReflectUtil;

public class LoginInfoBean {
	private int userid = ClassReflectUtil._INVALID_NUM_;
	private String username;
	private String loginipaddress;
	private int logintime = ClassReflectUtil._INVALID_NUM_;
	private boolean isadmin = false;
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
	/**
	 * @return the isadmin
	 */
	public final boolean isIsadmin() {
		return isadmin;
	}
	/**
	 * @param isadmin the isadmin to set
	 */
	public final void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginInfoBean [userid=" + userid + ", username=" + username + ", loginipaddress="
				+ loginipaddress + ", logintime=" + logintime + ", isadmin=" + isadmin + "]";
	}

}
