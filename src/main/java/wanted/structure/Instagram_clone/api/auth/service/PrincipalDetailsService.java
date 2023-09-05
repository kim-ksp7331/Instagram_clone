package wanted.structure.Instagram_clone.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wanted.structure.Instagram_clone.api.user.entity.User;
import wanted.structure.Instagram_clone.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byUsername = userRepository.findByEmail(username).orElseThrow();
        if (byUsername != null) {
            return new PrincipalDetails(byUsername);
        }
        return null;
    }
}