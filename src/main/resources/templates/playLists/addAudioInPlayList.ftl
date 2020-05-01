<#import "../parts/common.ftl" as common>

<@common.page>

    <table>
        <#list playLists as playList>
            <tr>
                <td>${playList.name}</td>
                <td>
                    <form method="post" enctype="multipart/form-data">
                        <input type="hidden" name="playListId" value="${playList.id}"/>
                        <button type="submit">Add</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

    <div>
        ${result}
    </div>

</@common.page>