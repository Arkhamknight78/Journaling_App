package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/journal/**", "/user/**")
                .permitAll()
                // allow all requests to /journal
                .anyRequest().authenticated()
                // all other requests require authentication
                .and()
                .httpBasic();
        // enable basic authentication


        //http.csrf().disable();
        //it is a stateless protocol
        // its normal sever to server call with no memory of previous calls


        // disable CSRF protection
        // for simplicity, not recommended for production
        // http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // enable CSRF protection with cookie-based token repository
        // http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
        // disable session management and CSRF protection
        // for stateless APIs, not recommended for production
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // disable session management for stateless APIs

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        // set the user details service for authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

// give summary of all the classes in this file
// 1. SpringSecurity: This class configures the security settings for the application, including authentication and authorization.
// 2. configure(HttpSecurity http): This method configures the HTTP security settings, allowing all requests to the /journal endpoint and requiring authentication for all other requests.
// 3. configure(AuthenticationManagerBuilder auth): This method sets the user details service for authentication.
// 4. passwordEncoder(): This method returns a PasswordEncoder bean that uses BCrypt for password hashing.
// 5. UserDetailsServiceImpl: This class implements the UserDetailsService interface to load user-specific data during authentication.
// 6. UserEntry: This class represents a user entity with properties like username, password, and roles.
// 7. JournalEntry: This class represents a journal entry entity with properties like date, createdBy, and content.
// 8. JournalEntryService: This class provides methods to save, update, and retrieve journal entries.
// 9. UserEntryService: This class provides methods to save, update, and retrieve user entries.
// 10. JournalEntryControllerV2: This class handles HTTP requests related to journal entries, including creating and retrieving entries.
// 11. UserController: This class handles HTTP requests related to user entries, including creating and updating users.
// 12. JournalEntryRepository: This interface extends the MongoRepository to provide CRUD operations for JournalEntry entities.
// 13. UserRepository: This interface extends the MongoRepository to provide CRUD operations for UserEntry entities.
// 14. ObjectId: This class represents a unique identifier for MongoDB documents.
// 15. LocalDateTime: This class represents a date-time without a time zone in the ISO-8601 calendar system.
