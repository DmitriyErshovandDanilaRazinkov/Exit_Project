<#import "../parts/common.ftl" as common>

<@common.page>
    <div class = "container">
        <div class = "my-3">

            <table>
                <thead>
                <tr>
                    <td>Name</td>
                    <td>Tags</td>
                </tr>
                </thead>

                <#list listAudio as audio>
                    <tr>
                        <td><a href="/user/audioPage/${audio.id}"><span>${audio.name}</span></a></td>
                        <td><#list audio.tags as tag>${tag.name};</#list></td>
                        <td>
                            <a href="/audio/addInPlayList/${audio.id}">Add in PlayList</a>
                        </td>
                    </tr>
                <#else>
                    <div>
                        У вас пока нет аудиозаписей, добавьте их
                    </div>
                </#list>
            </table>
        </div>
    </div>
</@common.page>