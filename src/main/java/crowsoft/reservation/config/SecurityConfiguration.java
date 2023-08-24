package crowsoft.reservation.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static crowsoft.reservation.core.entities.Permission.ADMIN_CREATE;
import static crowsoft.reservation.core.entities.Permission.ADMIN_DELETE;
import static crowsoft.reservation.core.entities.Permission.ADMIN_READ;
import static crowsoft.reservation.core.entities.Permission.ADMIN_UPDATE;
import static crowsoft.reservation.core.entities.Permission.MANAGER_CREATE;
import static crowsoft.reservation.core.entities.Permission.MANAGER_DELETE;
import static crowsoft.reservation.core.entities.Permission.MANAGER_READ;
import static crowsoft.reservation.core.entities.Permission.MANAGER_UPDATE;
import static crowsoft.reservation.core.entities.Role.ADMIN;
import static crowsoft.reservation.core.entities.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
            "/api/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html")
        .permitAll()


        // .requestMatchers(GET,
        // "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(),
        // MANAGER_READ.name())
        // .requestMatchers(POST,
        // "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(),
        // MANAGER_CREATE.name())
        // .requestMatchers(PUT,
        // "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(),
        // MANAGER_UPDATE.name())
        // .requestMatchers(DELETE,
        // "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(),
        // MANAGER_DELETE.name())

        /*
         * .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
         * 
         * .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
         * .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
         * .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
         * .requestMatchers(DELETE,
         * "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
         */

        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())

    ;
    return http.build();
  }
}