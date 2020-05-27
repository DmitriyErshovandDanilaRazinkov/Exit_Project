<#import "../parts/common.ftl" as common>

<@common.page>

    <div class = "container my-3">
        <div>
            <a href="/playLists/${pageTo.playListId}"><-Назад</a>
        </div>

        <br><b>Ссылка для приглашения :</b>
        <div id="text1">
            ${pageTo.joinCode}
        </div>

       <script type = "text/javascript">
           function copytext(el) {
               var $tmp = $("<input>");
               $("body").append($tmp);
               $tmp.val($(el).text()).select();
               document.execCommand("copy");
               $tmp.remove();
           }
       </script>

        <button class="btn btn-outline-primary" onclick="copytext('#text1')" > Скопировать адрес</button>

        <br><br>
        <#if (pageTo.nowUserRole >=3) >
            <#if (pageTo.nowUserRole == 4) >
                <div><a href="/playLists/${pageTo.playListId}/changeOwner">Поменять владельца</a></div>
            </#if>
            <div><a href="/playLists/${pageTo.playListId}/admins">Aдминистраторы</a></div>
            <div><a href="/playLists/${pageTo.playListId}/moderators">Модераторы</a></div>
        </#if>
        <br>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">UserName</th>
                            <th scope="col ">Role</th>
                        </tr>
                        </thead>
                        <#list pageTo.roleInPlayListList as userWithRole>
                            <tr>
                                <td>${userWithRole.user.username}</td>
                                <td>${userWithRole.playListRole.getName()}</td>
                                <td>
                                    <#if (pageTo.nowUserRole >=3)>
                                        <form action="/playLists/${pageTo.playListId}/deleteUser" method="post"
                                              enctype="multipart/form-data">
                                            <input type="hidden" name="userId" value="${userWithRole.user.id}">
                                            <button type="submit" class="btn btn-outline-danger mx-3">Удалить</button>
                                        </form>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                    </table>
    </div>

</@common.page>