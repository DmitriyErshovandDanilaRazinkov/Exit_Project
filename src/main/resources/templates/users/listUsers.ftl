<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>UserName</td>
            <td>Roles</td>
        </tr>
        </thead>
        <#list allUsers as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.role}</td>
                <td>
                    <form action="/admin/deleteUser" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
                <td>
                    <form action="/admin/addToAdmin" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit">AppToAdmin</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>