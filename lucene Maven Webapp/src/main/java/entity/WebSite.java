/**
 * 
 */
package entity;

import java.io.Serializable;

/**
 * <p><b>Description:</b> 网站信息的实体类</p>
 * <p><b>Date:</b> 2016年6月29日下午2:09:24</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class WebSite implements Serializable{
	private static final long serialVersionUID = -597092701875619860L;
	private String sname;
	private String fname;
	private String username;
	private String password;
	private String email;
	private String description;
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public WebSite(String sname, String fname, String username,
			String password, String email, String description) {
		super();
		this.sname = sname;
		this.fname = fname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.description = description;
	}
	public WebSite() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "WebSite [sname=" + sname + ", fname=" + fname + ", username="
				+ username + ", password=" + password + ", email=" + email
				+ ", description=" + description + "]";
	}
}
