<#import "../parts/common.ftl" as common>

<@common.page>

    <div class = "mx-5 my-5" >
        <h3 class="my-3">Введите ссылку на видео</h3>
        <form method="post" action="/downloadaudiofrom" enctype="multipart/form-data" class = "my-3">
            <input name="link" type="text" value="${(link)!""}">
            <br><button type="submit" class="btn btn-outline-primary my-2">Найти</button>
        </form>
    </div>
</@common.page>