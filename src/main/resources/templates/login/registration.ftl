<#import "../parts/common.ftl" as common>

<@common.page>
    <div class="w-25 mx-auto mb-3">
        <h2>Регистрация</h2>
        <form method="post" action="/registration">
            <div class="input-group input-group-prepend pt-1">
                <input class="form-control" type="text" name="userName" placeholder="НикНейм"
                       value="${(userForm.userName)!""}">
                <span class="alert-danger">${(validation_userName)!""}</span></div>
            <div class="input-group input-group-prepend pt-1">
                <input class="form-control" type="password" name="password" placeholder="Пароль"
                       value="${(userForm.password)!""}">
                <span class="alert-danger">${(validation_password)!""}</span></div>
            <div class="input-group input-group-prepend pt-1 pb-1">
                <input class="form-control" type="password" name="confirmPassword" placeholder="Подтвердите Пароль"
                       value="${(userForm.confirmPassword)!""}">
                <span class="alert-danger">${(validation_confirmPassword)!""}</span>
                <span class="alert-danger">${(passwordsNotEquals)!""}</span></div>
            <button type="submit" class="btn btn-success pt-1">Регистрация</button>
        </form>
        <a href="/">Главная</a>
    </div>

</@common.page>