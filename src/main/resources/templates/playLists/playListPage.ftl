<#import "../parts/common.ftl" as common>
<#import "../audios/listForAudio.ftl" as listAudio>

<@common.page>

    <div>
        ${playList.name}
    </div>

    <@listAudio.listAudio listAudios=playList.listAudio/>

    <form method="post" action="/user/playList/${playList.id}/add" enctype="multipart/form-data">
        <div>
            Название музыки
            <input type="text" name="name">
        </div>
        <div>
            <button type="submit"> Добавить</button>
        </div>
    </form>
</@common.page>