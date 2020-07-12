package com.jboss.blog.services;

import com.jboss.blog.models.User;
import com.jboss.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService{
	String ROLE_PREFIX = "ROLE_";
	 @Autowired
	 private UserRepository userRepository;

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     Optional<User> user = userRepository.findByUsername(username);
		 return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				 mapRolesToAuthorities(user.get().getRole().getId()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Long role) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX +role));
		return list;
	}
}
