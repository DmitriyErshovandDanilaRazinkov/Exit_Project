<#import "parts/common.ftl" as common>

<@common.page>

    <div class="container">
        <div class="my-5">
            <div class="row">
                <div class="col-sm">
                    <h2>Самая популярная аудиозаписи: </h2>
                    <#list morePopular as audio>
                        <div>
                            <a href="/user/audioPage/${audio.id}">${audio.name}</a>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</@common.page>