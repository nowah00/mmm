package com.ssg.mmm.account.controller;

import com.ssg.mmm.account.dto.AccountJoinRequests;
import com.ssg.mmm.account.dto.AccountLoginRequests;
import com.ssg.mmm.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountHelper accountHelper;

    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        if (joinReq.getName() == null || joinReq.getLoginId() == null || joinReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequests loginReq) { // ⑥
        if (!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String output = accountHelper.login(loginReq, req, res);
        if (output == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest request) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(request), HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        accountHelper.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
