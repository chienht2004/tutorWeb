package com.asm.tutorCompany.Controller.LogOut;


import org.springframework.web.bind.annotation.GetMapping;

public class BlockController {
    @GetMapping("/account/block")
    public String block(){
        return "logOut/account-blocked";
    }
}
