<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <table class="table table-borderless">
        <thead>
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Tags</td>
            <td>CountList</td>
            <td>File_Id</td>
        </tr>
        </thead>
        <#list audios as audio>audio.
            <tr>
                <td><b><a href="/admin/audio/${audio.id}">${audio.id}</a></b></td>
                <td><span>${audio.name}</span></td>
                <td><#list audio.tags as tag>${tag.name};</#list></td>
                <td>${audio.countListen}</td>
                <td><span><a href="/admin/getFile/${audio.file.id}">${audio.file.id}</a></span></td>
                <td>
                    <form action="/admin/deleteAudio" enctype="multipart/form-data" method="post">
                        <input type="hidden" name="audioId" value="${audio.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        <#else>
                <div>
                    У вас пока нет аудиозаписей, добавьте их
                </div>
        </#list>
    </table>

</@common.page>