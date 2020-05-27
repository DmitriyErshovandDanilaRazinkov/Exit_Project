<#import "../parts/common.ftl" as common>

<@common.page>
    <div class = "container">
        <div class = "my-5">
            <div class="row">
                <div class="col-sm">
                    <div>
                        <form action="/store/addCash" method="post" enctype="multipart/form-data">
                            <button class="btn btn-info btn-lg" type="submit">Пополнить счет</button>
                        </form>
                    </div>
                    <div class="green">
                        ${message}
                    </div>
                </div>
                <div class="col-sm">
                    <div>
                        <form action="/store/premium" method="post" enctype="multipart/form-data">
                            <button class="btn btn-info btn-lg" type="submit">Bye premium</button>
                        </form>
                    </div>
                    <div class="red">
                        ${exception}
                    </div>
                </div>
            </div>
        </div>
    </div>
</@common.page>