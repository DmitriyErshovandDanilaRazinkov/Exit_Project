<#macro page>

    <!DOCTYPE html>
    <head>
        <meta charset="UTF-8">
        <title>DmusicD-Admin</title>
    </head>
    <body>
    <nav>
        <a href="/admin/users">Users</a>
        <a href="/admin/audios">Audios</a>
        <a href="/admin/files">Files</a>
        <a href="/admin/tags">Tags</a>
        <a href="/admin/playLists">PlayLists</a>
        <a href="/upload/audio">Upload Audio</a>
        <a href="/upload/file">Upload File</a>
        <a href="/admin">Main-admin</a>
        <a href="/">Main</a>
    </nav>

    <#nested>

    </body>
    </html>
</#macro>