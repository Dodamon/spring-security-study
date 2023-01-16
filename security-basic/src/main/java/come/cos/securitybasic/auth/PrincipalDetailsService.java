package come.cos.securitybasic.auth;

import come.cos.securitybasic.model.User;
import come.cos.securitybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session <= Authentication <= UserDetail
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
