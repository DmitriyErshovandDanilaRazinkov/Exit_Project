<#import "../parts/common.ftl" as common>

<@common.page>
    <div>
        <h2>Регистрация</h2>
        <form method="post" action="/registration">
            <div><input type="text" name="userName" placeholder="НикНейм" value="${(userForm.userName)!""}">
                <span>${(validation.userName)!""}</span></div>
            <div><input type="password" name="password" placeholder="Пароль" value="${(userForm.password)!""}">
                <span>${(validation.password)!""}</span></div>
            <div><input type="password" name="confirmPassword" placeholder="Подтвердите Пароль"
                        value="${(userForm.confirmPassword)!""}">
                <span>${(validation.confirmPassword)!""}</span>
                <span>${(passwordsNotEquals)!""}</span></div>
            <button type="submit" class="btn-success">Регистрация</button>
        </form>
        <a href="/">Главная</a>
    </div>

</@common.page>