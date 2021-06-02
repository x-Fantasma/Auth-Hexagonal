package co.pets.auth.infrastructure.in_ports.api.adapter;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.pets.auth.domain.ports.primary.AuthService;
import co.pets.auth.infrastructure.in_ports.api.model.JwtDto;
import co.pets.auth.infrastructure.in_ports.api.model.LoginUser;
import co.pets.auth.infrastructure.in_ports.api.model.NewUser;
import co.pets.core.exceptions.ResException;
import co.pets.domain.exceptions.config.BadRequestException;


@RestController
@RequestMapping("/auth")
@CrossOrigin("localhost:4200")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser) {
		try {
			
			authService.register(newUser);
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			
			BadRequestException exception = new BadRequestException(e.getMessage());
			ResException res = new ResException(e.getMessage(), exception);
				
			return new ResponseEntity<ResException>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
		try {
			
			return new ResponseEntity<>(authService.login(loginUser), HttpStatus.OK);
		
		} catch (Exception e) {
			
			BadRequestException exception = new BadRequestException(e.getMessage());
			ResException res = new ResException(e.getMessage(), exception);
				
			return new ResponseEntity<ResException>(res, HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@RequestBody JwtDto jwtDto) {
		try {
			
			return new ResponseEntity<>(authService.refreshToken(jwtDto), HttpStatus.OK);
		
		} catch (Exception e) {
			
			BadRequestException exception = new BadRequestException(e.getMessage());
			ResException res = new ResException(e.getMessage(), exception);
				
			return new ResponseEntity<ResException>(res, HttpStatus.BAD_REQUEST);
		}
	}
}
