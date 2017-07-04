<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://neo4j-contrib.github.io/developer-resources/language-guides/assets/css/main.css">
    <title>Neo4j Movies</title>
</head>

<body>
<div id="graph">
    <defs>
        <marker id="markerArrow" markerWidth="13" markerHeight="13" refx="2" refy="6" orient="auto">
            <path d="M2,2 L2,11 L10,6 L2,2" style="fill: #000000;" />
        </marker>
    </defs>
</div>
<div role="navigation" class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-md-6">
                <ul class="nav navbar-nav">
                    <li>
                        <form role="search" class="navbar-form" id="search">
                            <div class="form-group">
                                <input type="text" value="" placeholder="Search Friends" class="form-control" name="search">
                            </div>
                            <button class="btn btn-default" type="submit">Search</button>
                        </form>
                    </li>
                </ul>
            </div>
            <div class="navbar-header col-sm-6 col-md-6">
                <div class="logo-well">
                    <a href="http://neo4j.com/developer-resources">
                        <img src="http://neo4j-contrib.github.io/developer-resources/language-guides/assets/img/logo-white.svg" alt="Neo4j World's Leading Graph Database" id="logo">
                    </a>
                </div>
                <div class="navbar-brand">
                    <div class="brand">Neo4j Movies</div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-5">
        <div class="panel panel-default">
            <div class="panel-heading">Search Results</div>
            <table id="results" class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Society</th>
                    <th>Released</th>
                    <th>Tagline</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-7">
        <div class="panel panel-default">
            <div class="panel-heading" id="title">Details</div>
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <img src="" class="well" id="poster"/>
                </div>
                <div class="col-md-8 col-sm-8">
                    <h4>Crew</h4>
                    <ul id="crew">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<style type="text/css">
    .node { stroke: #222; stroke-width: 1.5px; }
    .node.actor { fill: #888; }
    .node.person { fill: #BBB; }
    .link { stroke: #999; stroke-opacity: .6; stroke-width: 1px; }
</style>

<script type="text/javascript" src="/resources/js/jquery-1.10.2.js"></script>
<script src="/resources/js/d3.js" charset="utf-8"></script>
<script type="text/javascript">
    $(function () {
        function showMovie(title) {
            $.get("/person/findByTitle?title=" + encodeURIComponent(title), // todo fix paramter in SDN
                    function (data) {
                        if (!data ) return; //  || !data["_embedded"].movies) return;
                        var movie = data; // ["_embedded"].movies[0];
                        $("#title").text(movie.title);
                        $("#poster").attr("src","http://neo4j-contrib.github.io/developer-resources/language-guides/assets/posters/"+encodeURIComponent(movie.title)+".jpg");
                        var $list = $("#crew").empty();
                        movie.roles.forEach(function (cast) {
                            $.get(cast._links.person.href, function(personData) {
                                var person = personData.name;
                                var job = cast.job || "acted";
                                $list.append($("<li>" + person + " " +job + (job == "acted"?" as " + cast.roles.join(", ") : "") + "</li>"));
                            });
                        });
                    }, "json");
            return false;
        }
        function search() {
            var query=$("#search").find("input[name=search]").val();
            $.get(/*"/user/searchFriendsByTitle?userName="*/ + encodeURIComponent(query),
                    function (data) {
                        var t = $("table#results tbody").empty();
                        if (!data) return;
                        data = data["_embedded"].movies;
                        data.forEach(function (movie) {
                            $("<tr><td class='movie'>" + movie.title + "</td><td>" + movie.released + "</td><td>" + movie.tagline + "</td></tr>").appendTo(t)
                                    .click(function() { showMovie($(this).find("td.movie").text());})
                        });
                        showMovie(data[0].title);
                    }, "json");
            return false;
        }

        $("#search").submit(search);
        search();
    })
</script>

<script type="text/javascript">
    var width = 800, height = 800;
/*d3.layout.force 基于物理模拟的位置连接，force.charge 获取或设置节点电荷数（表示吸引或排斥），
linkDistance 获取或设置节点间连接线的距离， size获取宽和高*/
    var force = d3.layout.force()
            .charge(-200).linkDistance(100).size([width, height]);
/**/
    var svg = d3.select("#graph").append("svg")
            .attr("width", "100%").attr("height", "100%")
            .attr("pointer-events", "all");
    d3.json("/user/graph", function(error, graph) {
        if (error) return;
/*force.node 获得或设置布局中的节点阵列组，links获得或设置布局中节点间得连接阵列组，start开启或恢复节点间得位置影响*/
        force.nodes(graph.nodes).links(graph.links).start();

        var link = svg.selectAll(".link")
                .data(graph.links).enter()
                .append("line").attr("class", "link");

        var gNode = svg.selectAll(".node")
                .data(graph.nodes).enter()
                .append("g")
                .attr("class","layer nodes");
        var node = gNode.append("circle")
                .attr("class", function (d) { return "node "+d.label })
                .attr("r", 25)
                .call(force.drag);
        var text = gNode.append("text").text(function (d) {
            return d.title;
        }).attr("font-size",15)
            .attr("text-anchor","middle")
            /*.attr("transform","translate("+d.x+" "+(d.y+5)
                +")")*/;

        // html title attribute
        node.append("title")
                .text(function (d) { return d.title; });

        // force feed algo ticks
        force.on("tick", function(d) {
            var dx = function(d){
                return d.x;
            };
            var dy = function(d) {
                return d.y;
            };

            link.attr("x1", function(d) { return d.source.x; })
                    .attr("y1", function(d) { return d.source.y; })
                    .attr("x2", function(d) { return d.target.x; })
                    .attr("y2", function(d) { return d.target.y; })
                .attr("marker-end","url(#markerArrow)");
                      node.attr("cx", dx)
                .attr("cy", dy);
//            var translate = "translate("+dx+" "+dy+")";
            text.attr("transform",function(d) { return "translate(" + d.x + "," + (d.y+5) + ")"; });
        });
    });
</script>

</body>
</html>
