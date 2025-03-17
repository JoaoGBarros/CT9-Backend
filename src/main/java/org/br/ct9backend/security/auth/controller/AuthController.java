package org.br.ct9backend.security.auth.controller;


import org.br.ct9backend.security.auth.model.Cargo;
import org.br.ct9backend.security.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/verifyRole")
    public ResponseEntity<Map<String, String>> verifyRole(@RequestBody String uid) {
        Map<String, String> response = new HashMap<>();
        try{
            String cargo = authService.verifyRole(uid);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        response.put("role", Cargo.ADMIN.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
