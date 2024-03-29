(function() {
    Number.prototype.toPercent = function() { return (Math.round(this * 10000) / 100).toFixed(2) + "%" };
    var a = function(q) {
        if (!(this instanceof a)) { return new a(q) }
        var n = this;
        var h = {};
        var r = [];
        var g = {};
        var o;
        var e = [];
        var k = new Date().getTime();
        var j = { id: "", level: 3, levelDesc: ["省份", "城市", "区县", "乡镇", "社区"], index: "996", separator: " - ", isInitClick: true, isWithMouse: false, offsetX: 0, offsetY: 0, emptyText: "暂无数据", color: "#56b4f8", fontSize: "14px", isAsync: false, asyncUrl: "", isShowBtn: true, btnConfig: [], data: "" };
        var t = j;
        var l;
        var c = 20;
        var m = true;
        n._init = function() {
            if (!i()) { return }
            d();
            var x = '<div id="adp-wraper-' + k + '" class="adp-wraper" style="z-index: ' + t.index + '"><p>';
            var v = '<div class="ul-div" style="display: block"><ul></ul></div>';
            x += '<span class="adp-head-active">' + t.levelDesc[0] + "</span><span>" + t.levelDesc[0] + "</span>";
            for (var w = 1; w < t.level; w++) {
                x += "<span>" + t.levelDesc[w] + "</span>";
                v += '<div class="ul-div"><ul><span class="adp-empty-text">' + t.emptyText + "</span></ul></div>"
            }
            x += "</p>";
            var y = b();
            $("body").append(x + v + y + '</div><div class="adp-wraper-backshadow" style="z-index: ' + (t.index - 1) + '"></div>');
            if (t.isAsync) {
                o = f(null);
                n._initFirstLevelData()
            } else {
                if (typeof t.data == "string" && !p(t.data)) {
                    $.ajax({
                        url: t.data,
                        async: false,
                        success: function(z) {
                            o = z;
                            n._getEachLevelData(0, t.level, o);
                            n._initFirstLevelData()
                        }
                    })
                } else {
                    o = t.data;
                    if (typeof t.data == "string") { o = JSON.parse(o) }
                    n._getEachLevelData(0, t.level, o);
                    n._initFirstLevelData()
                }
            }
            n._bindEvent()
        };
        n._getEachLevelData = function(w, B, y) {
            var A = [];
            var z = [];
            if (w >= B) { return }
            for (var x = 0; x < y.length; x++) { A.push(y[x]); if (w == B - 1) { continue } if (y[x].children == undefined) { continue } for (var v = 0; v < y[x].children.length; v++) { z.push(y[x].children[v]) } }
            if (z.length == 0) { return }
            h["data_level_" + w] = A;
            n._getEachLevelData(w + 1, B, z)
        };
        n._initFirstLevelData = function() {
            for (var w = 0; w < t.level; w++) {
                $("#adp-wraper-" + k).children("div").eq(w).children("ul").empty();
                $("#adp-wraper-" + k).children("div").eq(w).children("ul").append('<span class="adp-empty-text">' + t.emptyText + "</span>")
            }
            if (o.length > 0) { $("#adp-wraper-" + k).children("div").eq(0).children("ul").empty() }
            for (var w = 0; w < o.length; w++) {
                var v = '<li data-code="' + o[w].code + '" title="' + o[w].name + '">' + o[w].name + "</li>";
                $("#adp-wraper-" + k).children("div").eq(0).children("ul").append(v)
            }
            var y = $("#adp-wraper-" + k).children("p").children("span").eq(1).text();
            var x = $("#adp-wraper-" + k).children("p").children("span.adp-head-active");
            x.css("left", "-1px");
            x.text(y);
            $("#adp-wraper-" + k).children("div.ul-div").eq(0).show().siblings("div.ul-div").hide();
            if (t.isShowBtn) { s(0) }
        };
        n._bindEvent = function() {
            $("#adp-wraper-" + k + " div ul").delegate("li", "click", function() {
                var z = $(this).parent().parent().index();
                g.code = $(this).data("code");
                g.text = $(this).text();
                g.level = z;
                r[z - 1] = $(this).data("code");
                e[z - 1] = $(this).text();
                r.splice(z, r.length - z);
                e.splice(z, e.length - z);
                var G = $("#adp-wraper-" + k).children("div.ul-div").length;
                if (z < G) {
                    for (var F = z; F < t.level; F++) { $("#adp-wraper-" + k).children("div.ul-div").eq(F).children("ul").empty().append('<span class="adp-empty-text">' + t.emptyText + "</span>"); if (t.isShowBtn) { s(F) } }
                    $("#adp-wraper-" + k).children("div.ul-div").eq(z).children("ul").empty();
                    var x = t.isAsync ? f(g, z) : o;
                    if (z > 1 && !t.isAsync) { x = h["data_level_" + (z - 1)] }
                    if (x != undefined) {
                        for (var C = 0; C < x.length; C++) {
                            var y = x[C];
                            if (t.isAsync) {
                                var E = '<li data-code="' + y.code + '" title="' + y.name + '">' + y.name + "</li>";
                                $("#adp-wraper-" + k).children("div").eq(z).children("ul").append(E)
                            } else {
                                if (y.name == $(this).text() && y.code == $(this).data("code")) {
                                    if (y.children != null && y.children.length > 0) {
                                        for (var B = 0; B < y.children.length; B++) {
                                            var A = y.children[B];
                                            var E = '<li data-code="' + A.code + '" title="' + A.name + '">' + A.name + "</li>";
                                            $("#adp-wraper-" + k).children("div.ul-div").eq(z).children("ul").append(E)
                                        }
                                    } else { $("#adp-wraper-" + k).children("div.ul-div").eq(z).children("ul").append('<span class="adp-empty-text">' + t.emptyText + "</span>") }
                                    break
                                }
                            }
                        }
                        if (t.isShowBtn) { s(z) }
                    } else { $("#adp-wraper-" + k).children("div.ul-div").eq(z).children("ul").append('<span class="adp-empty-text">' + t.emptyText + "</span>") }
                    $(this).addClass("adp-active").siblings().removeClass("adp-active");
                    var D = $("#adp-wraper-" + k).children("p").children("span").eq(z + 1).text();
                    var H = $("#adp-wraper-" + k).children("p").children("span.adp-head-active");
                    H.css("left", (0.2 * (z)).toPercent());
                    $(this).parent().parent().next().show().siblings("div.ul-div").hide();
                    setTimeout(function() { H.text(D) }, 200)
                } else {
                    $(this).addClass("adp-active").siblings().removeClass("adp-active");
                    $("#adp-wraper-" + k).fadeOut();
                    $("div.adp-wraper-backshadow").hide()
                }
            });
            $("#adp-wraper-" + k + " p span").click(function() {
                if ($(this).hasClass("adp-head-active")) { return }
                var x = $(this).index() - 1;
                var z = $(this).text();
                var y = $("#adp-wraper-" + k).children("p").children("span.adp-head-active");
                y.css("left", x == 0 ? -1 : (0.2 * x).toPercent());
                setTimeout(function() { y.text(z) }, 200);
                $("#adp-wraper-" + k).children("div").eq(x).show().siblings("div.ul-div").hide()
            });
            $("div.adp-wraper-backshadow").on("click", function() {
                $("div.adp-wraper-backshadow").hide();
                $("#adp-wraper-" + k).fadeOut()
            });
            if (t.isInitClick) {
                $("#" + t.id).on("click", function() {
                    var y = u(event);
                    var x = $("#adp-wraper-" + k + " div.ul-div").eq(0);
                    $("#adp-wraper-" + k).css("left", y.x);
                    $("#adp-wraper-" + k).css("top", y.y);
                    $("#adp-wraper-" + k).fadeIn();
                    if ((g.level == undefined || m) && t.isShowBtn) {
                        if (x.children("ul").children("li").height() != 0) { c = x.children("ul").children("li").height() }
                        s(0);
                        m = false
                    }
                    $(".adp-wraper-backshadow").show()
                })
            }
            if (t.isShowBtn) { var w = t.btnConfig; for (var v = 0; v < w.length; v++) { if (typeof(w[v].click) == "function") { $("#adp_btn_" + v).on("click", w[v].click) } } }
        };
        n._clearSelectedData = function() {
            r = [];
            e = [];
            g = {};
            var x = $("#adp-wraper-" + k).children("p").children("span.adp-head-active");
            x.css("left", "-1px");
            setTimeout(function() { x.text(t.levelDesc[0]) }, 200);
            for (var w = 1; w < t.level; w++) { $("#adp-wraper-" + k).children("div.ul-div").eq(w).children("ul").empty().append('<span class="adp-empty-text">' + t.emptyText + "</span>"); if (t.isShowBtn) { s(w) } }
            var v;
            if ((v = $("#adp-wraper-" + k).children("div.ul-div").eq(0).children("ul").children("li.adp-active")).length > 0) { v.removeClass("adp-active") }
            $("#adp-wraper-" + k).children("div").eq(0).show().siblings("div.ul-div").hide()
        };
        n._setSelectedData = function(E) {
            if (typeof(E) != "object" || E == null) { return }
            if (E.length > t.level || E.length == 0) { return }
            var v = t.isAsync ? f(null) : o;
            if (typeof(v) != "object" || v == null) { return }
            var x, G, H, K, I, J = "",
                D = false,
                C;
            H = $("#adp-wraper-" + k).children("div.ul-div").eq(0).children("ul");
            for (var z = 0; z < (K = H.children("li")).length; z++) {
                x = v[z];
                if (x.code == E[0]) {
                    g.code = x.code;
                    g.text = x.name;
                    g.level = 1;
                    r[0] = x.code;
                    e[0] = x.name;
                    r.splice(1, r.length - 1);
                    e.splice(1, e.length - 1);
                    G = x;
                    C = 0;
                    D = true;
                    H.children("li").removeClass("adp-active");
                    $(K[z]).addClass("adp-active");
                    continue
                }
            }
            if (!D) { return }
            for (var B = 1; B < E.length; B++) {
                if (E[B] == null || E[B] == "") { break }
                var w = t.isAsync ? f(G, g.level) : G.children;
                if (typeof(w) != "object" || w == null) { break }
                J = "";
                D = false;
                G = {};
                for (var A = 0; A < w.length; A++) {
                    if (w[A].code == E[B]) {
                        J += '<li data-code="' + w[A].code + '" title="' + w[A].name + '" class="adp-active">' + w[A].name + "</li>";
                        g.code = w[A].code;
                        g.text = w[A].name;
                        g.level = (B + 1);
                        r[B] = w[A].code;
                        e[B] = w[A].name;
                        r.splice(B + 1, r.length - B - 1);
                        e.splice(B + 1, e.length - B - 1);
                        G = w[A];
                        D = true;
                        C = B;
                        continue
                    }
                    J += '<li data-code="' + w[A].code + '" title="' + w[A].name + '">' + w[A].name + "</li>"
                }
                if (!D) { break }
                $("#adp-wraper-" + k).children("div.ul-div").eq(B).children("ul").empty().append(J);
                if (t.isShowBtn) { s(B) }
            }
            if (typeof(C) == "number") {
                $("#adp-wraper-" + k).children("div.ul-div").eq(C).show().siblings("div.ul-div").hide();
                var F = $("#adp-wraper-" + k).children("p").children("span").eq(C + 1).text();
                var L = $("#adp-wraper-" + k).children("p").children("span.adp-head-active");
                L.css("left", (0.2 * (C)).toPercent());
                setTimeout(function() { L.text(F) }, 200);
                for (var y = C + 1; y < t.level; y++) { $("#adp-wraper-" + k).children("div.ul-div").eq(y).children("ul").empty().append('<span class="adp-empty-text">' + t.emptyText + "</span>"); if (t.isShowBtn) { s(y) } }
            }
        };
        n._refreshData = function(v) {
            if (typeof v == "string" && p(v)) { v = JSON.parse(v) }
            o = v;
            if (o.length > 0) { n._getEachLevelData(0, t.level, o) }
            n._initFirstLevelData()
        };
        n._getTotalValueAsArray = function() { return { code: r, text: e } };
        n._getTotalValueAsText = function() { var w = ""; for (var v = 0; v < g.level; v++) { w += (v < g.level - 1 ? e[v] + t.separator : e[v]) } return w };
        n._getCurrentObject = function() { return g };
        n._show = function() {
            var v = u(event);
            $("#adp-wraper-" + k).css("left", v.x);
            $("#adp-wraper-" + k).css("top", v.y);
            $("#adp-wraper-" + k).fadeIn();
            $(".adp-wraper-backshadow").show()
        };
        n._hide = function() {
            $("#adp-wraper-" + k).fadeOut();
            $("div.adp-wraper-backshadow").hide()
        };
        n._on = function(v, w) { $("#adp-wraper-" + k + " div ul").delegate("li", v, w) };

        function p(x) { if (typeof x == "string") { try { var w = JSON.parse(x); if (typeof w == "object" && w) { return true } else { return false } } catch (v) { return false } } }

        function u(w) {
            var v = $("#" + t.id).offset().left - 10;
            var C = $("#" + t.id).offset().top + $("#" + t.id).height() + 5;
            if (t.isWithMouse) {
                var A = w || window.event;
                var B = document.documentElement.scrollLeft || document.body.scrollLeft;
                var z = document.documentElement.scrollTop || document.body.scrollTop;
                v = A.pageX || A.clientX + B;
                C = A.pageY || A.clientY + z
            }
            v += t.offsetX;
            C += t.offsetY;
            return { x: v, y: C }
        }

        function d() {
            var w = "";
            var y = $("head script");
            if (y.length > 0) { for (var A = 0; A < y.length; A++) { if (y[A].src.indexOf("/js/address-picker.js") != -1) { w = y[A].src.substring(0, y[A].src.indexOf("/js/address-picker.js")); break } } }
            if (w != "") {
                var v = document.querySelector(":root");
                v.style.setProperty("--theme-color", t.color);
                v.style.setProperty("--font-size", t.fontSize);
                var z = document.getElementsByTagName("head")[0];
                var x = document.createElement("script");
                var B = document.createElement("link");
                B.href = w + "/css/address-picker.css";
                B.rel = "stylesheet";
                B.type = "text/css";
                z.appendChild(B);
                if (t.data == "") { if (t.level == 1 || t.level == 2) { t.data = w + "/data/pc-code.json" } else { if (t.level == 4 || t.level == 5) { t.data = w + "/data/pcas-code.json" } else { t.data = w + "/data/pca-code.json" } } }
            }
        }

        function i() {
            var v = true;
            if (q instanceof Object) { t = $.extend({}, j, q) } else { if (typeof(q) == "string") { t.id = q } else { v = false } }
            if (t.level > t.levelDesc.length) { t.levelDesc = j.levelDesc }
            if (t.color == "") { t.color = j.color }
            if (t.fontSize == "") { t.fontSize = j.fontSize }
            if (t.id == "" || t.id == undefined) { v = false } else { if ($("#" + t.id).length == 0) { v = false } }
            if (t.isAsync && (typeof(t.asyncUrl) != "string" || t.asyncUrl == "")) { t.isAsync = j.isAsync }
            if (typeof(t.btnConfig) != "object" || t.btnConfig == null) {
                t.isShowBtn = false;
                t.btnConfig = j.btnConfig
            }
            return v
        }

        function s(y) {
            var w = $("#adp-wraper-" + k).children("div.ul-div").eq(y);
            var x = w.find("ul li");
            var v = Math.ceil(x.length / 5) * (c + 16) + 10;
            w.css("height", v > 65 ? v : 65 + "px")
        }

        function b() {
            var x, v = "";
            if (t.isShowBtn && (x = t.btnConfig).length > 0) {
                v = '<div class="adp-btn-area">';
                for (var w = 0; w < x.length; w++) { v += '<div id="adp_btn_' + w + '" class="adp-btn">' + x[w].text + "</div>" }
                v += "</div>"
            }
            return v
        }

        function f(z, y) {
            var v = [];
            if (t.isAsync) {
                var x = "",
                    w = "",
                    A = 1;
                if (z == null) { if ((typeof(l) != "undefined" && l != null)) { return l } } else {
                    x = z.code;
                    w = z.name ? z.name : z.text;
                    A = y
                }
                $.ajax({
                    data: { code: x, name: w, level: A },
                    url: t.asyncUrl,
                    async: false,
                    success: function(B) {
                        if (typeof(B) == "string" && p(B)) { B = JSON.parse(B) }
                        v = B;
                        if (z == null && typeof(l) == "undefined") { l = B }
                    }
                })
            }
            return v
        }
        n._init();
        return { on: n._on, show: n._show, hide: n._hide, refreshData: n._refreshData, getCurrentObject: n._getCurrentObject, getTotalValueAsArray: n._getTotalValueAsArray, getTotalValueAsText: n._getTotalValueAsText, clearSelectedData: n._clearSelectedData, setSelectedData: n._setSelectedData }
    };
    window.addressPicker = a
}());