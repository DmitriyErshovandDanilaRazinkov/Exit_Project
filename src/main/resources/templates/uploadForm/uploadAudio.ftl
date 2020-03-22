<#import "../parts/common.ftl" as common>

<@common.page>

    <form method="post" enctype="multipart/form-data">
        <div>
            <input type="text" name="name">
        </div>
        <div>
            <input type="text" name="tag">
        </div>
        <div>
            <input type="file" name="file">
        </div>
        <div>
            <button type="submit"> Upload </button>
        </div>
    </form>

</@common.page>