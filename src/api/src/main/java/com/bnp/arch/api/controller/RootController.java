package com.bnp.arch.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.bnp.arch.api.config.security.JwtTokenUtil;
import com.bnp.arch.api.model.security.JwtRequest;
import com.bnp.arch.api.model.security.JwtResponse;
import com.bnp.arch.api.model.user.User;
import com.bnp.arch.api.model.user.UserDetail;
import com.bnp.arch.api.repository.user.UserRepository;
import com.bnp.arch.api.service.JwtUserDetailsService;
import com.bnp.arch.api.util.UtilFunctions;

import lombok.extern.slf4j.Slf4j;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("/api")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    @Value("${app.url}")
    String appUrl;

    @Value("${email.debug.name}")
    private String debugName;

    // curl -d '{"username":"sparate@bnp.com", "password":"password"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/authenticate
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info("authenticating the user  " + authenticationRequest.getUsername() + " "
                + authenticationRequest.getUsername());
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        log.info("user is authenticated");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public UserDetail getInstrospect(Authentication authentication) throws Exception {
        User _user = userRepository.getUserRecordByEmail(authentication.getName());

        UserDetail user = new UserDetail();
        user.setUser(_user);
        user.setAuths(userRepository.getUserAuthorizations(_user.getId()));

        user.getUser().setPassword("");
        user.getUser().setMfaSecret("");
        return user;
    }
    @RequestMapping(path = "/health-check", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String healthCheck( Authentication authentication) {
        if (null != authentication && null != authentication.getName()) {
                return authentication.getName();
        }
        return "NOT.LOGGEDIN";
    }

    @RequestMapping(path = "/pulse", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String basePulse( Authentication authentication) {
        if (null != authentication && null != authentication.getName()) {
            return authentication.getName();
        }
        return "NOT.LOGGEDIN";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public Map<String, String> forgotPassword(@RequestBody User payload)
            throws UnsupportedEncodingException, MessagingException {
        User user = userRepository.getUserRecordByEmail(payload.getEmail());
        HashMap<String, String> map = new HashMap<>();
        if (user != null && user.getStatusId() == 2) {
            String password = UtilFunctions.getRandomPassword(15);
            String ePassword = passwordEncoder.encode(password);
            userRepository.setNewPassword(ePassword, 1/* status > New */, user.getEmail());
            map.put("status", "SUCCESS");
            map.put("msg", "Reset password email sent.");
        } else {
            map.put("status", "WARNING");
            map.put("msg", "You are not authorized, Please contact to the system administrator.");
        }

        return map;
    }



}
