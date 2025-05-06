package org.sid.renaultvisiteursbackend.Security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PersonRepository personRepository;

    private final String secretKey = "MIICXgIBAAKBgQDNPVn9YUqJ/C9vDkwMP0xCVK218XCZeaPgvL3unAXSHcaWMUOP\n" +
            "yb89nEfem3YHRwHJUpQkWIa7iFyMDZDxcg3TYl/Tx3vl6fm7Scj6emDwtb5iQIGF\n" +
            "S7/LgSipW081HYcAzHvTpoA4wv+0wqD52VE2S0FVXL3K+FT7YzGFGv2jUwIDAQAB\n" +
            "AoGBAKRFccyrXYTZ84FZGSdIVppUuoEBEZXV1YQgrYjZGpOVv4ghQClLWiVO+/tB\n" +
            "xROIEvb3gJkivhxFxYVXqmaGWmcheKLNI+UXg10hJ+e08NMn1Gxgmj+IAXLEKG4A\n" +
            "rPg0QQzwWU8KiwLL9vUr/lIqp5+L1O/xfWrSpFz4Us4nx6wxAkEA+Yxca9J4PKZl\n" +
            "GgJvDqkmDXRgWu73RdBzFOU0IjWV3MhqGv7CSZhFhPspn05nSNwS5/RT16f7WzuF\n" +
            "DYjXKXwauwJBANKLu7GzDSfp1aFSebDtxeGU9EzHkdLhpvW1YTLHtyGo/zbeMfMs\n" +
            "KVdgmzCUgq3FJqaQi+kY0NaeXxEG/YeXzEkCQCGiQl6h6mS6RIwh4dgHAkLz+Xyo\n" +
            "EpnNQ4WAcutdb4pnVK24wnTq2gvXUj/PcGpIhx/ONXKuiFk+h2tQkzdbK7sCQQCq\n" +
            "PlKGXUFGBM24o/fCGIDo5oijjLtcyRk3lHIDnXl2vi+fLgs1lX/YJ0VVAsCnwcJ+\n" +
            "7GI1GNvErkowenaGLTgBAkEApNjd2a4onsY4wunNREvmrOSKdIScOrq89ywx5qwG\n" +
            "VXv3jbIKvsLYhWepWZmRvlgyFOFwMef04a77LPW3ZtHQEw==";

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Person person = personRepository.findByEmail(username);
            if (person == null) {
                throw new UsernameNotFoundException("Utilisateur introuvable avec l'email : " + username);
            }

            if (!person.isActif()) {
                throw new UsernameNotFoundException("⚠️ Ce compte est désactivé.");
            }

            return User.withUsername(person.getEmail())
                    .password(person.getPassword())
                    .authorities(person.getRole())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .authorizeHttpRequests(ar -> ar.requestMatchers("/auth/**", "/content/**").permitAll())
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}