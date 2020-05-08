<#import "../parts/common.ftl" as common>

<@common.page>

    <div>
        <form action="/store/addCash" method="post" enctype="multipart/form-data">
            <button type="submit">Пополнить счет</button>
        </form>
    </div>
    <div>
        <form action="/store/premium" method="post" enctype="multipart/form-data">
            <button type="submit">Bye premium</button>
        </form>
    </div>
    <div class="green">
        ${message}
    </div>
    <div class="red">
        ${exception}
    </div>

</@common.page>