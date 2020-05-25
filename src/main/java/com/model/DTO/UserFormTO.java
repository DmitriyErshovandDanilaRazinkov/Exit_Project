package com.model.DTO;

import com.model.User;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserFormTO {

    @Size(min = 3)
    private String userName = "";
    @Size(min = 5, message = "Слишком короткий пароль (минимум 5 символов)")
    private String password = "";
    @Size(min = 5)
    private String confirmPassword = "";
}
