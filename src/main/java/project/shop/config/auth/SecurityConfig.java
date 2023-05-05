package project.shop.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import project.shop.entity.Role;

@RequiredArgsConstructor
/**Spring Security 설정 활성화*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**h2-console 화면 사용하기 위해 해당 옵션들 disable()*/
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                .authorizeRequests()    //antMatchers 사용
                .antMatchers("/", "/home", "/member/login", "/member/registration",
                        "/item/list/**", "/css/**", "/images/**", "/js/**", "/h2-console/**","/static/**").permitAll()
                //("/static/**" 추가해줘야 로그아웃했을 때, css경로 리다이렉트 되었을때도 css받아올 수 있음)
//                .antMatchers("/home/login").hasRole(Role.MEMBER.name())     //MEMBER권한 가져야 진입가능
                .anyRequest().authenticated()   //설정된 값 이외에 나머지 URL 로그인된 사용자만 허용

                /**로그아웃 성공시 redirect*/
                .and()
                .logout()
                .logoutSuccessUrl("/home")

                .and()
                .oauth2Login()  //OAuth 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                .userService(customOAuth2UserService)
                /** 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
                    리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시가능*/

                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/*")
                .and()
                .defaultSuccessUrl("/home/login");

    }
}
