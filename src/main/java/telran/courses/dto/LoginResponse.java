package telran.courses.dto;

public class LoginResponse {
	public String accessToken;
	public String role;
	public LoginResponse(String accessToken, String role) {
		this.accessToken = accessToken;
		this.role = role;
	}
	public LoginResponse() {
	}
}
