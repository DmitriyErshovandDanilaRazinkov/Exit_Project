<#import "../parts/admincommon.ftl" as common>
<#import "listForAudio.ftl" as listAudio>

<@common.page>

    <@listAudio.listAudio listAudios=audios/>

</@common.page>