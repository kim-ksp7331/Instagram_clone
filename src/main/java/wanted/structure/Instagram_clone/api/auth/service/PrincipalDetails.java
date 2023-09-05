package wanted.structure.Instagram_clone.api.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wanted.structure.Instagram_clone.api.user.entity.User;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user; // 컴포지션

    // 해당 User의 권한을 리턴하는곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        });
        return null;
    }


    // User 의 password 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId() + "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 사이트 내에서 1년동안 로그인을 안하면 휴먼계정을 전환을 하도록 하겠다.
        // -> loginDate 타입을 모아놨다가 이 값을 false로 return 해버리면 된다.
        return true;
    }
}