<#import "parts/common.ftl" as common>

<@common.page>

    <div class="container">
        <div class = "my-5">
            <div class = "row">
                <div class="col-sm">
                    <h2>Самые популярные аудиозаписи: </h2>
                    <#list morePopular as audio>
                        <div>
                            <a href="/user/audioPage/${audio.id}">${audio.name}</a>
                        </div>
                    </#list>
                </div>
                <div class = "col-sm">
                    <div class = "mx-5">
                        <h2 >Узнайте о преимуществах подписки на DmusicD:</h2>
                        <button type="button" class="btn btn-dark my-1">Узнать</button>
                    </div>
                </div>
            </div>
            <div class = "row">
                <div class="col-sm">
                    <a href="/downloadingpage"><h3>ютуб хрень</h3></a>
                </div>

            </div>
        </div>
    </div>
</@common.page>