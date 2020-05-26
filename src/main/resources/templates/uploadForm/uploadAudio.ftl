<#import "../parts/admincommon.ftl" as common>

<@common.page>

    <form method="post" enctype="multipart/form-data">
        <div>
            <input type="text" name="name">
            <input name="isPremium" type="checkbox">
        </div>
        <div>
            <input type="file" name="file">
        </div>
        <div>
            <button type="submit"> Upload</button>
        </div>
    </form>

</@common.page>