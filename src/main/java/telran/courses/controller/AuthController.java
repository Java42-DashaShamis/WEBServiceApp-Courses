package telran.courses.controller;

import java.util.Base64;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import telran.courses.dto.LoginData;
import telran.courses.dto.LoginResponse;
import telran.courses.exceptions.BadRequestException;
import telran.courses.security.Account;
import telran.courses.security.AccountingManagement;

@RestController
@RequestMapping("/login")
@CrossOrigin
@Validated
public class AuthController {
	
	static Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	AccountingManagement accounting;
	PasswordEncoder passwordEncoder;
	public AuthController(AccountingManagement accounting, PasswordEncoder passwordEncoder) {
		this.accounting = accounting;
		this.passwordEncoder = passwordEncoder;
	}
	@Value("${app.security.enable: true}")
	private boolean securityEnable;
	@PostMapping
	LoginResponse login(@RequestBody @Valid LoginData loginData) {
		LOG.debug("login data are email {}, password {}", loginData.email, loginData.password);
		Account account = accounting.getAccount(loginData.email);
		/* V.R. The following line has to be instead of previous one
		Account account = securityEnable ? accounting.getAccount(loginData.email) : new Account("admin@tel-ran.co.il",
				"$2a$10$rSdI0lSvHmwhzOxLQ1olOujYO4gIGgRhst03Si3vKxtpASI/4W3Ni", "ADMIN");
				*/
// V.R. Additional line
// V.R.		if(securityEnable)
		if(account == null || !passwordEncoder.matches(loginData.password, account.getPasswordHash())) {
			throw new BadRequestException("Wrong credentials");
		}
		LoginResponse response = new LoginResponse(getToken(loginData), securityEnable ? account.getRole() : "ADMIN");
/* V.R.	The following line has to be instead of previous one
 * LoginResponse response = new LoginResponse(securityEnable ? getToken(loginData) : "", account.getRole());
 */
		LOG.debug("accessToken: {}, role: {}", response.accessToken, response.role);
		return response;
	}

	private String getToken(LoginData loginData) {
		//"Basic <username:password> in Base64 code
		byte[] code = String.format("%s:%s", loginData.email, loginData.password).getBytes();
		return "Basic " + Base64.getEncoder().encodeToString(code);
	}
}
