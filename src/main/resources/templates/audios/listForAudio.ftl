<#macro listAudio listAudios>

    <#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
    <table>
        <thead>
        <tr>
            <td></td>
            <td>Name</td>
            <td>Tags</td>
            <td></td>
        </tr>
        </thead>
        <#list listAudios as audio>
            <tr>
                <@sec.authorize access="hasRole('ROLE_ADMIN')">
                    <td><b><a href="/admin/audio/${audio.id}">${audio.id}</a></b></td>
                </@sec.authorize>
                <td><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <@sec.authorize access="hasRole('ROLE_ADMIN')">
                    <td><span><a href="/admin/files/${audio.getFileId()}">${audio.getFileId()}</a></span></td>
                </@sec.authorize>
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
</#macro>