<#import "../parts/common.ftl" as common>

<@common.page>
    <div class = "my-5 mx-5">
        <table>
            <#list userPageTo.playLists as playList>
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
            ${userPageTo.result}
        </div>
    </div>
</@common.page>