package com.yabuhoo.server.model;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class User {

	private String username;
	private String password;

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

	public boolean equals(Object other) { // 重写equals方法，后面最好重写hashCode方法
		if (this == other) // 先检查是否其自反性，后比较other是否为空。这样效率高
			return true;
		if (other == null)
			return false;
		if (!(other instanceof User))
			return false;

		final User other_user = (User) other;
		if (!getUsername().equals(other_user.getUsername()))
			return false;
		if (!getPassword().equals(other_user.getPassword()))
			return false;
		return true;
	}

	public int hashCode() {
		// hashCode主要是用来提高hash系统的查询效率。当hashCode中不进行任何操作时，可以直接让其返回
		// 一常数，或者不进行重写。
		int result = getUsername().hashCode();
		result = 29 * result + getPassword().hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "user--->" + getUsername() + "/" + getPassword()
				;
	}
}
