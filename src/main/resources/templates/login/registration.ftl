<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
</head>

<body>
<div>
    <@spring.bind "userForm"/>
    <h2>Регистрация</h2>
    <div>
        <@input path="username" type="text" placeholder="Username" autofocus="true"/>
        <@spring.showErrors ' ', 'errors'>
            ${usernameError}
        </@spring.showErrors>
    </div>
    <div>
        <@spring.formInput 'settings.password','type="password"  placeholder="Password"'></@spring.formInput>
    </div>
    <div>
        <@spring.formInput 'settings.passwordConfirm','type="password"
                        placeholder="Confirm your password"'></@spring.formInput>
        <@spring.showErrors ' ', 'errors'>
            ${passwordError}
        </@spring.showErrors>
    </div>
    <button type="submit">Зарегистрироваться</button>
    <@spring.showErrors ' ', 'errors'/>

    <a href="/">Главная</a>
</div>
</body>
</html>