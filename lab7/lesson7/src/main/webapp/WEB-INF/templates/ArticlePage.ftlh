<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="creation form">
        <form method="post" action="">
            <input type="hidden" name="action" value="create"/>
            <div class="field">
                <div class="name">
                    <label for="title">Choose the title</label>
                </div>
                <div class="value">
                    <input id="title" name="title" value="${title!}"/>
                </div>
                <div class="name">
                    <label for="text">Type article text here:</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text">${text!}</textarea>
                </div>
                <div class="error">
                    ${error!}
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>
    <script>
        $(function () {
            $(".creation form").submit(function () {
                const text = $(this).find("textarea[name='text']").val();
                const title = $(this).find("input[name='title']").val();
                const $error = $(this).find(".error");

                ajax({
                    action: "create",
                    text,
                    title
                }, function (response) {
                    if (response["error"]) {
                        $error.text(response["error"]);
                    }
                }, "/article");

                return false;
            });
        })
    </script>
</@c.page>