<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <table>
        <thead>
        <tr>
            <td></td>
            <td>Name</td>
            <td>Tags</td>
            <td></td>
        </tr>
        </thead>
        <#list audios as audio>
            <tr>
                <td><b><a href="/admin/audio/${audio.id}">${audio.id}</a></b></td>
                <td><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <td><span><a href="/admin/files/${audio.getFileId()}">${audio.getFileId()}</a></span></td>
                <td>
                    <form enctype="multipart/form-data" method="post">
                        <input type="hidden" name="audioId" value="${audio.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>

</@common.page>