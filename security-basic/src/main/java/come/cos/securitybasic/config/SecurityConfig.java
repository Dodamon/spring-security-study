package come.cos.securitybasic.config;

import come.cos.securitybasic.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 구글 로그인이 완료된 후 처리
        // 1. 코드받기(인증), 2. 엑세스토크(권한),
        // 3. 사용자프로필 정보를 가져오고
        // 4-1. 그 정보를 토대로 회원가입을 자동으로 진행
        // 4-2. 그 정보가 부족할 경우 -> 추가로 입력을 받는다. ( 추가적인 회원가입 )
        // 구글 로그인이 완료되면 코드X (엑세스토큰 + 사용자프로필 정보를 한번에 받는다)
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
       .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다
                .defaultSuccessUrl("/")
       .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
