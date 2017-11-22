<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.journal.model.JournalArticle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<style type="text/css">
    #editor {
        position: relative;
        width: 900px;
        height: 400px;
    }
</style>

<portlet:resourceURL var="resourceURL">
</portlet:resourceURL>

<div class="row">
    <div class="col-md-3">

        <label for="articleSelect">Select Article</label>
        <select style="font-size:12px" onchange="isDirty = true; lastchange=new Date().getTime();<portlet:namespace/>populateVars();" id="articleSelect">
            <option label="-- None --" name="-- None --" value="" ></option>
            <%
                List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticles(scopeGroupId);
                for (JournalArticle article : articles) {
                    String articleId = article.getArticleId();
                    String articleTitle = article.getTitle(locale);
                    String structureId = article.getDDMStructureKey();
                    double articleVersion = article.getVersion();
                    String structureName = article.getDDMStructure().getName(locale);

            %> <option label="<%=articleTitle%> <%=articleVersion%> (<%= structureName %>)" name="<%= articleId %>" value="<%= articleId %>" ></option> <%
            }
        %>

        </select>
        <div id="vars"></div>

    </div>
    <div class="col-md-9">
        <div id="theError"></div>
        <label for="editor">Template code</label>
        <div id="editor">some text</div>
        

    </div>
</div>

<h2>Result:</h2>
<div id="theResult"></div>

<script type="text/javascript">

    var isDirty = false;
    var lastchange = new Date().getTime();
    var editor;

    setInterval(function() {
        if (isDirty && ((new Date().getTime()) - lastchange) > 1300) {
            console.log("rendering at " + new Date());
            lastchange = new Date().getTime();
            isDirty = false;
            <portlet:namespace/>renderTemplate();
        }
    }, 500);

    function <portlet:namespace/>populateVars() {
        var articleId = document.getElementById('articleSelect').value;

        if (!articleId) {
            document.getElementById('vars').innerHTML = '';
            return;
        }

        <portlet:namespace/>auiXmlHttpRequest('<%= resourceURL %>', {
            "<portlet:namespace/>cmd": 'getVars',
            "<portlet:namespace/>articleId": articleId
        }, function (result) {
            var varobj = JSON.parse(result);
            var html='<p style="line-height: 110%;">';

            varobj.forEach(function(theVar) {
                html+= ("<div style='display:inline-block'><code>" + theVar + "</code>&nbsp;&nbsp;</div>");
            });
            html += ('</p>');
            document.getElementById('vars').innerHTML = html;
        }, function (errmsg) {
            document.getElementById('vars').innerHTML = errmsg;
        });

    }

    function <portlet:namespace/>renderTemplate() {
        var templateStr = editor.getValue();
        var articleId = document.getElementById('articleSelect').value;

        editor.session.clearAnnotations();

        <portlet:namespace/>auiXmlHttpRequest('<%= resourceURL %>', {
            "<portlet:namespace/>cmd": 'renderTemplate',
            "<portlet:namespace/>template": templateStr,
            "<portlet:namespace/>articleId": articleId
        }, function (result) {
            document.getElementById("theResult").innerHTML = result;
            document.getElementById("theError").innerHTML = '';
        }, function (errmsg) {
            document.getElementById("theResult").innerHTML = '';
            document.getElementById("theError").innerHTML = '<span style="font-size:.8em;color:red;font-style:italic;">' +
                    errmsg + '</span><br>';
            // highlight line
            // at line 62, column 39 in templateName
            var re = /\s+line\W+(\d+).*column\W+(\d+)/i ;
            var result = errmsg.match(re);
            var lineno = result[1];
            var colno = result[2];
            console.log("err: line: " + lineno + " col: " + colno);
            if (lineno >= 0 && colno >= 0) {
                editor.gotoLine(lineno, colno, true);

                editor.session.setAnnotations([{
                    row: lineno-1,
                    column: colno-1,
                    text: errmsg,
                    type: "error"
                }]);
            }

        });
    }

    function <portlet:namespace/>auiXmlHttpRequest(url, data, onSuccess, onError) {

        AUI().use(
                "aui-base", "aui-io-plugin", "aui-io-request",
                function (A) {
                    A.io.request(
                            url,
                            {
                                data: data,
                                dataType: "json",
                                on: {
                                    success: function (event, id, obj) {
                                        var responseData = this.get("responseData");
                                        var stat = responseData.stat;
                                        if (stat == 'ok') {
                                            onSuccess(responseData.result);
                                        } else if (stat == 'error') {
                                            onError(responseData.error);
                                        } else {
                                            onError("unknown error");
                                        }
                                    },
                                    failure: function (event, id, obj) {
                                        onError(JSON.stringify(event));
                                    }
                                }
                            }
                    );
                }
        );
    }


    AUI().ready(function() {
        editor = ace.edit("editor");
        ace.require("ace/ext/language_tools");
        editor.setOptions({
            enableBasicAutocompletion: true
        });

        var ftlmode = ace.require("ace/mode/ftl").Mode;
        editor.session.setMode(new ftlmode());

        editor.on('change', function() {
            lastchange = new Date().getTime();
            isDirty = true;
        });
    });



</script>