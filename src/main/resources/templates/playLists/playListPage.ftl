<#import "../parts/common.ftl" as common>

<@common.page>
    <div class = "container">
        <div class = "my-2">
            <div>
                <h3>${playList.name}</h3>
            </div>

            <div>
                <a href="/playLists/${playList.id}/listUsers">Пользователи</a>
            </div>

            <table class = "my-2">
                <thead>
                <tr>
                    <td>Name</td>
                    <td>Tags</td>
                    <td></td>
                </tr>
                </thead>
                <#list playList.listAudio as audio>
                    <tr>
                        <td><span>${audio.name}</span></td>
                        <td><#list audio.tags as tag>${tag.name};</#list></td>
                        <td>
                            <audio controls preload="metadata">
                                <source src="/download/${audio.file.id}">
                            </audio>
                        </td>
                        <td>
                            <#if (userRole.playListRole.id >= 2)>
                                <form enctype="multipart/form-data" method="post">
                                    <input type="hidden" name="audioId" value="${audio.id}"/>
                                    <input type="hidden" name="action" value="delete"/>
                                    <button type="submit" class="btn btn-outline-danger">Delete</button>
                                </form>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</@common.page>