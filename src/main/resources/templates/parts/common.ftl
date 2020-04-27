<#macro page>
    <#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />

    <!DOCTYPE html>
    <head>
        <meta charset="UTF-8">
        <title>DmusicD</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
              crossorigin="anonymous">
    </head>
    <body>

    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">DmusicD</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
                    aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">My page</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/audio">Audio</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Upload
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="/upload/audio">Audio</a>
                            <a class="dropdown-item" href="/upload/file">File</a>
                        </div>
                    </li>
                </ul>
            </div>
            <@sec.authorize access="!isAuthenticated()">
                <div>
                    <a class="btn btn-success mr-sm-2" href="/login">Войти</a>
                    <a class="btn btn-success mr-sm-2" href="/registration">Зарегистрироваться</a>
                </div>
            </@sec.authorize>
            <@sec.authorize access="isAuthenticated()">
                <a class="btn btn-danger mr-sm-2" href="/logout">Выйти</a>
            </@sec.authorize>
            <@sec.authorize access="hasRole('ROLE_ADMIN')">
                <div>
                    <a class="btn btn-blue mr-sm-2" href="/admin">Admin</a>
                </div>
            </@sec.authorize>
        </nav>

    </header>


    <#nested>

    <footer>

    </footer>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
</#macro>