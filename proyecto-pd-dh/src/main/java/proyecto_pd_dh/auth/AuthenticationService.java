package proyecto_pd_dh.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proyecto_pd_dh.configuration.JwtService;
import proyecto_pd_dh.entities.Role;
import proyecto_pd_dh.entities.Usuario;
import proyecto_pd_dh.repository.IUsuarioRepository;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService ;

    public AuthenticationResponse register(RegisterRequest registerRequest){
       var user = Usuario.builder()
               .name(registerRequest.getName())
               .apellido(registerRequest.getApellido())
               .email(registerRequest.getEmail())
               .password(passwordEncoder.encode(registerRequest.getPassword()))
               .role(Role.USER)
               .build();

       usuarioRepository.save(user);

        // Crea un UserDetails a partir del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());


       //devolver el token

        var jwt = jwtService.generateToken(new HashMap<>(), userDetails);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }


    public AuthenticationResponse registerAdmin(RegisterRequest registerRequest){
        var user = Usuario.builder()
                .name(registerRequest.getName())
                .apellido(registerRequest.getApellido())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ADMIN)
                .build();

        usuarioRepository.save(user);

        // Crea un UserDetails a partir del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());


        //devolver el token

        var jwt = jwtService.generateToken(new HashMap<>(), userDetails);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }


    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception {
        //Delegamos al authentication manager

            System.out.println(loginRequest);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )

            );
            var user = usuarioRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow();
            System.out.println("Usuario:" + user);

            Usuario userJWT = new Usuario(user.getEmail(), user.getPassword());

            var jwt = jwtService.generateToken(userJWT);
            System.out.println("jwt: " + jwt);

            return AuthenticationResponse.builder()
                    .token(jwt)
                    .build();


    }
}
