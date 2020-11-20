<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.creationTime}</td>
                    <#if user.email != "">
                        <td>${user.email}</td>
                    <#else>
                        <td>-</td>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".users tr").click(function () {
                        ajax({
                            action: "findUser",
                            userId: $(this).attr("data-userId")
                        }, function (response) {
                            notify(response["user"].creationTime);
                        })
                    })
                });
        })
    </script>
</@c.page>
