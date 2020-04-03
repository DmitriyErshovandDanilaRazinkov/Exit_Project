<#import "../parts/admincommon.ftl" as common>

<@common.page>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
        </tr>
        </thead>
        <#list tags as tag>
            <tr>
                <td><b>${tag.id}</b></td>
                <td><span>${tag.name}</span></td>
                <td>
                    <form action="/admin/tags" method="post">
                        <input type="hidden" name="tagId" value="${tag.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

    <div>
        <h2>
            Создать новый тег
        </h2>
        <form action="/admin/addTag" method="post">
            <input type="text" name="name">
            <button type="submit">Add</button>
        </form>
    </div>

</@common.page>