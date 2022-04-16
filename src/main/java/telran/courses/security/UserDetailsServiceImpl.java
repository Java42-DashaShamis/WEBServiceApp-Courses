package telran.courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
	@Autowired
	AccountingManagement accounting;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accounting.getAccount(username);
		if(account==null) {
			throw new UsernameNotFoundException("");
		}
		
		return new User(username, account.getPasswordHash(), AuthorityUtils.createAuthorityList("ROLE_" + account.getRole()));
	}

}
