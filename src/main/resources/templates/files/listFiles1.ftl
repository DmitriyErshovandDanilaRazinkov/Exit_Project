<#import "../parts/admincommon.ftl" as common>

<@common.page>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>FileName</td>
            <td>OriginalName</td>
        </tr>
        </thead>
        <#list files as file>
            <tr>
                <td><b>${file.id}</b></td>
                <td><span>${file.name}</span></td>
                <td><span>${file.originalName}</span></td>

                <td>
                    <form action="/admin/files" method="post">
                        <input type="hidden" name="fileId" value="${file.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>


</@common.page>