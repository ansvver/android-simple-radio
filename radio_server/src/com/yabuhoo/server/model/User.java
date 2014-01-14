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

	public boolean equals(Object other) { // ��дequals���������������дhashCode����
		if (this == other) // �ȼ���Ƿ����Է��ԣ���Ƚ�other�Ƿ�Ϊ�ա�����Ч�ʸ�
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
		// hashCode��Ҫ���������hashϵͳ�Ĳ�ѯЧ�ʡ���hashCode�в������κβ���ʱ������ֱ�����䷵��
		// һ���������߲�������д��
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
