package com.social.network.rest.api;

import java.security.Principal;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.domain.model.Account;
import com.social.network.domain.model.User;
import com.social.network.rest.dto.AccountDto;

/**
 * Created by Yadykin Andrii Aug 4, 2016
 *
 */

@RestController
@RequestMapping(value = "/signin")
public class LoginController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public AccountDto user(Principal user) {
        AccountDto accountDto = null;
        if (Objects.nonNull(user)) {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User loggedUser = account.getUser();
            accountDto = new AccountDto(user.getName(), loggedUser.getFirstName(), loggedUser.getLastName(), loggedUser.getLocale());
        }
        return accountDto;
    }

}
