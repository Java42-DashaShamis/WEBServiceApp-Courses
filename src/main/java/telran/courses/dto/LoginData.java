package telran.courses.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginData {
	@Email
	public String email;
	@Size(min=7)
	public String password;
}
