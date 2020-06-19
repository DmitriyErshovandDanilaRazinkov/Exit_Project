<#import "../parts/common.ftl" as common>

<@common.page>

    <div class = "mx-5 my-5" >
        <h3> ${videoDetails.getTitle()!""} ${videoDetails.getLengthMinutesAndSeconds()!""}</h3>

        <form method="post" action="/addaudiofrom" enctype="multipart/form-data" class = "my-3">
            <br><button type="submit" class="btn btn-outline-primary my-2">Добавить</button>
        </form>
    </div>

</@common.page>