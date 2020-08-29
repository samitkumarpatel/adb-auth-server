package net.adb.authserver;

import net.adb.authserver.model.ApplicationUser;
import net.adb.authserver.repositry.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
    ApplicationUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		ApplicationUser u = userRepository.findByUsername(s);
		if(u == null) {
			throw new UsernameNotFoundException(s);
		}
		return new User(u.getUsername(),u.getPassword(), Collections.emptyList());
	}
}
