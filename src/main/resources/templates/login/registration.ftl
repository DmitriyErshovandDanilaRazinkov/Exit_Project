<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
</head>

<body>
<div>
    <form method="post" enctype="multipart/form-data">
        <h2>Регистрация</h2>
        <div>
            <@spring.formInput path="userForm.username" fieldType="text" attributes='placeholder="Username" autofocus="true"'></@spring.formInput>
            <@spring.showErrors ' ', 'errors'>
                ${usernameError}
            </@spring.showErrors>
        </div>
        <div>
            <@spring.formInput path="userForm.password" fieldType="password" attributes='placeholder="Password"'></@spring.formInput>
        </div>
        <div>
            <@spring.formInput path="userForm.passwordConfirm" fieldType="password"
            attributes='placeholder="Confirm your password"'></@spring.formInput>
            <@spring.showErrors ' ', 'errors'>
                ${passwordError}
            </@spring.showErrors>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>
    <@spring.showErrors ' ', 'errors'/>

    <a href="/">Главная</a>
</div>
</body>
</html>