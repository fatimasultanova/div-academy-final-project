package az.baku.divfinalproject.security;

import az.baku.divfinalproject.security.jwt.AuthEntryPointJwt;
import az.baku.divfinalproject.security.jwt.AuthTokenFilter;
import az.baku.divfinalproject.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    final
    UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/admin-panel/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/adverts/**").authenticated()
                                .requestMatchers("/api/adverts/user").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/advert-details/**").authenticated()
                                .requestMatchers("/api/advert-details/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/advert-type/**").authenticated()
                                .requestMatchers("/api/advert-type/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/building-type/**").authenticated()
                                .requestMatchers("/api/building-type/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/payment/**").authenticated()
                                .requestMatchers("/api/users/**").authenticated()
                                .requestMatchers(HttpMethod.GET,"/api/property-details/**").authenticated()
                                .requestMatchers("/api/property-details/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/property-type/**").authenticated()
                                .requestMatchers("/api/property-type/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/role/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/subscription").authenticated()
                                .requestMatchers("/api/subscription").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers("/api/verification/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
