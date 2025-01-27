package org.abonehatirlatici.neoduyorum.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı."));
    }
}
