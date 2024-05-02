package az.baku.divfinalproject.security.services;

import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  final
  UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }



  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByPhoneNumberOrEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found : " + username));

    return UserDetailsImpl.build(user);
  }

}