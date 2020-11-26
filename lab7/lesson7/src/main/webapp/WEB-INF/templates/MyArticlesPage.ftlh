<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Visible</th>
            </tr>
            </thead>
            <tbody>
            <#if articles?size == 0>
                <tr class="noData">
                    <td colspan="3">
                        No Articles
                    </td>
                </tr>
            </#if>
            <#list articles as article>
                <tr>
                    <td class="articleId">${article.id}</td>
                    <td class="articleTitle">${article.title}</td>
                    <td class="articleStatus" id="${article.id}">
                        <a href="#" id="${article.hidden?string("Hide", "Show")}">
                            ${article.hidden?string("Show", "Hide")}
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
        $(".articleStatus a").click(function() {
            $(this).text("Changing..")
            ajax({
                action: "changeStatus",
                id: $(this).parent().attr("id"),
                newStatus: $(this).attr("id")
            }, function (response) {
                let newValue = response["article"]["hidden"] ? "Show" : "Hide";
                $("#" + response["article"]["id"] + " a").text(newValue);
            }, "/article")
        });
    </script>
</@c.page>