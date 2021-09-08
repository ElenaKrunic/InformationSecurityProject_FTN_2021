package uns.ac.rs.ib.security.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.Role;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		   User user = userRepository.findByEmail(email);
	        if (user == null) {
	            throw new UsernameNotFoundException(String.format("User with username does not exist '%s'.", email));
	        }
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	        for(Role role:user.getRoles()){
	            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
	        }
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), grantedAuthorities);
	    }
	}


