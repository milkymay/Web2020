<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="notice" type="ru.itmo.wp.domain.Notice" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="creation form">
        <form method="post" action="">
            <input type="hidden" name="action" value="create"/>
            <div class="field">
                <div class="name">
                    <label for="content">Type notice text here:</label>
                </div>
                <div class="value">
                    <textarea id="content" name="content"></textarea>
                </div>
                <div class="error">
                    <@c.error "noticeForm.content"/>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Add">
            </div>
        </form>
    </div>
</@c.page>