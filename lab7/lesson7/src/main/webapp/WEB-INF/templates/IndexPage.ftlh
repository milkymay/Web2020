<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <div class="noData">
            No data
        </div>
        <template>
            <article>
                <div class="title">Loading..</div>
                <div class="information">Loading..</div>
                <div class="body">
                    Loading..
                </div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        Loading..
                    </div>
                </div>
            </article>
        </template>
    </div>

    <script>
        $(function () {
            $("aside section").click(function () {
                $(this).css("background-color", "red");
            });
        });

        $(function () {
            const $noData = $(".articles .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    response["articles"].forEach(function (article) {
                        $noData.hide();
                        const $tr = $($(".articles template").html()).clone().attr("id", "article" + article["id"]);
                        $tr.find(".title").text(article["title"]);
                        $tr.find(".body").text(article["text"]);
                        $tr.find(".footer .right").text(article["creationTime"]);
                        $(".articles").append($tr);
                        ajax({
                            action: "findLoginByUserId",
                            userId: article["userId"]
                        }, function (resp) {
                            $("#article" + article["id"] + " .information").text("by " + resp["author"]["login"]);
                        },
                        "/users")
                    });
                }, "/article"
            );

        })
    </script>
</@c.page>
