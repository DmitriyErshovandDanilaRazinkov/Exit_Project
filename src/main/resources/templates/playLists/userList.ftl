<#import "../parts/common.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td>UserName</td>
        </tr>
        </thead>
        <#list listUser as user>
            <tr>
                <td>${user.username}</td>
                <td>
                    <#if (nowUserRole.id >=3)>
                        <form action="/playLists/${playListId}/deleteUser" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="userId" value="${user.id}">
                            <button type="submit">Удалить</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>

    <#if (nowUserRole.id >=3) >
        <form action="/playLists/${playListId}/addUser" method="post" enctype="multipart/form-data">
            <input type="number" name="userId">
            <button type="submit">Добавить</button>
        </form>
    </#if>

</@common.page>