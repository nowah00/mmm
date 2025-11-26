package com.ssg.mmm.account.helper;

import com.ssg.mmm.account.dto.AccountJoinRequests;
import com.ssg.mmm.account.dto.AccountLoginRequests;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountHelper {
     void join (AccountJoinRequests accountJoinRequests);

     String login (AccountLoginRequests accountLoginRequests, HttpServletRequest request, HttpServletResponse response);

     Integer getMemberId (HttpServletRequest request);

     boolean isLoggedIn (HttpServletRequest request);

     void logout(HttpServletRequest request, HttpServletResponse response);
}
