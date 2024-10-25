package proyecto_pd_dh.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest registerAdmin){
        return ResponseEntity.ok(authenticationService.registerAdmin(registerAdmin));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody LoginRequest loginRequest) throws Exception {
        try{
            System.out.println("Login Request: " + loginRequest);
            return ResponseEntity.ok(authenticationService.login(loginRequest));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
