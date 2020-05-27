<#import "../parts/common.ftl" as common>

<@common.page>
    <div class="container">
        <div class="my-3">

            <form action="/audioWithId" method="post">
                <div class="form-group col-md-4">
                    <label for="inputState">Теги</label>
                    <select id="inputState" class="form-control" name="tagId">
                        <#list pageTo.tags as tag>
                            <option value="${tag.id}">${tag.name}</option>
                        </#list>
                    </select>
                    <button class="btn btn-outline-primary flex my-2">Найти</button>
                </div>
            </form>

            <h3>Найти аудио по названию</h3>
            <form class="mb-5" action="/audioByName" method="post">
                <div class="input-group input-group-prepend">
                    <input class="form-control w-25" type="text" name="audioName" placeholder="Название аудио">
                    <button class="btn btn-outline-primary mx-3">Найти</button>
                </div>
            </form>

            <table class="table table-borderless">
                <thead>
                <th scope="col">Name</th>
                <th scope="col">Tags</th>
                <th scope="col">Прослушали</th>
                <th scope="col">Добавить в плейлист</th>
                </thead>

                <#list pageTo.listAudio as audio>
                    <tr>
                        <td><a href="/user/audioPage/${audio.id}"><span>${audio.name}</span></a></td>
                        <td><#list audio.tags as tag>${tag.name};</#list></td>
                        <td>${audio.countListen}</td>
                        <td>
                            <a href="/audio/addInPlayList/${audio.id}">Add in PlayList</a>
                        </td>
                    </tr>
                <#else>
                    <div>
                        Такой аудиозаписи не нашлось
                        <div>
                            <a href="/audio">Вернутья к списку</a>
                        </div>
                    </div>
                </#list>
            </table>
        </div>
    </div>
</@common.page>