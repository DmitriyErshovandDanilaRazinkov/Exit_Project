<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>UserName</td>
            <td>Password</td>
            <td>Roles</td>
        </tr>
        </thead>
        <#list allUsers as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>
                    <#list user.roles as role> ${role.name}; </#list>
                </td>
                <td>
                    <form action="/admin/users" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>