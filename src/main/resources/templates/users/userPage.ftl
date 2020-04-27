<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        ${user.id}
    </div>
    <div>
        ${user.username}
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input name="name" type="text">
            <input name="isPrivate" type="checkbox">
            <button type="submit">Добавить</button>
        </form>
    </div>

    <#list user.getRoleInPlayLists() as role>
        <div>
            <tr>
                <td><a href="/user/playList/${role.getPlayList().id}">${role.getPlayList().id}</a></td>
                <td>${role.getPlayList().name}</td>
            </tr>
        </div>
    </#list>

</@common.page>