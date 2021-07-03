package com.superops.movie.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(UserResource.class);

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) {

        log.debug("REST request to save User : {}", user);

        if (user.getId() != null)
            return new ResponseEntity<>("A new user cannot already have an ID", HttpStatus.BAD_REQUEST);

        if (StringUtils.isEmpty(user.getEmailId()))
            return new ResponseEntity<>("Empty emailId", HttpStatus.BAD_REQUEST);

        if (StringUtils.isEmpty(user.getUsername()))
            return new ResponseEntity<>("Empty username", HttpStatus.BAD_REQUEST);

        if (StringUtils.isEmpty(user.getName()))
            return new ResponseEntity<>("Empty name", HttpStatus.BAD_REQUEST);

        if (StringUtils.isEmpty(user.getPassword()))
            return new ResponseEntity<>("Empty password", HttpStatus.BAD_REQUEST);

        if (userService.existsByEmailId(user.getEmailId()))
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);

        if (userService.existsByUsername(user.getUsername()))
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
}
