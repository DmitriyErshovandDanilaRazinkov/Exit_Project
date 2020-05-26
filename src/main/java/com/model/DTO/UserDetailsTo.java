package com.model.DTO;

import lombok.Data;

import java.util.*;

@Data
public class UserDetailsTo {

    private Long id;

    private String username;

    private String role;

    private double cash = 0;

    private boolean premium = false;

    private Date endPremium;

}
