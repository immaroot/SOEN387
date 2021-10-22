<%@ page import="ca.concordia.service.PollService" %>
<%@ page import="ca.concordia.core.Poll" %>
<%@ page import="ca.concordia.core.Choice" %>
<%@ page import="ca.concordia.core.PollStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (session.getAttribute("loggedIn") == null) {
        response.sendRedirect("/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Poll</title>
    <link href="/assets/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    String action;
    String error = null;
    PollService pollService = PollService.getInstance();


    if (request.getMethod().equals("POST")) {
        //handle submit
        action = request.getParameter("action");
        String title = request.getParameter("title");
        String question = request.getParameter("question");
        String[] choiceTitles = request.getParameterValues("choiceTitle");
        String[] choiceNotes = request.getParameterValues("choiceNotes");
        List<Choice> choices = new ArrayList<>();
        for(int i = 0; i < choiceTitles.length; i++) {
            Choice choice = new Choice(choiceTitles[i], choiceNotes[i]);
            choices.add(choice);
        }

        try {
            switch (action) {
                case "create":
                    pollService.createPoll(title, question, choices);
                    break;
                case "update":
                    pollService.updatePoll(title, question, choices);
                    break;
                case "run":
                    pollService.runPoll();
                    break;
                case "release":
                    pollService.releasePoll();
                    break;
                case "clear":
                    pollService.clearPoll();
                    break;
                case "close":
                    pollService.closePoll();
                    break;
                case "unrelease":
                    pollService.unreleasePoll();
                    break;
                case "logout":
                    session.removeAttribute("loggedIn");
                    response.sendRedirect("/login.jsp");
                    return;
                default:
                    break;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    Poll poll = pollService.getPoll();
    PollStatus pollStatus = poll == null ? PollStatus.NONE : poll.getStatus();
    String btnSaveText = pollStatus == PollStatus.NONE ? "Create" : "Update";

%>

<div class="container pt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-sm-12 col-md-11 col-lg-10 col-xl-9">
            <div class="row pb-5">
                <div class="col">
                    <h2 class="text-center">Poll Manager</h2>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <form method="post">
                            <div class="card-body">
                                <label>Title</label>
                                <div>
                                    <input type="text" class="form-control" name="title"
                                           value="<%= poll == null ? "" : poll.getTitle() %>">
                                </div>
                                <div>
                                    <label>Question</label>
                                    <div>
                                        <input type="text" class="form-control" name="question"
                                               value="<%= poll == null ? "" : poll.getQuestion() %>">
                                    </div>
                                </div>
                                <div class="mt-3">
                                    <label>Choices</label>
                                    <div class="row-choices">
                                        <% if (poll != null) { %>
                                        <% for (Choice choice : poll.getChoices()) {%>
                                        <div class="row mb-2">
                                            <label class="col-md-2">Choice: </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" name="choiceTitle"
                                                       value="<%=choice.getTitle()%>">
                                            </div>
                                            <label class="col-md-2">Description: </label>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" name="choiceNotes"
                                                       value="<%=choice.getDescription()%>">
                                            </div>
                                        </div>
                                        <% } %>
                                        <% } %>
                                    </div>
                                    <br/>
                                    <button class="btn btn-success" id="btn-add-choice">Add Choice</button>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="text-center">
                                    <% if (pollStatus == PollStatus.NONE || pollStatus == PollStatus.CREATED || pollStatus == PollStatus.RUNNING) { %>
                                    <button class="btn btn-primary" id="btn-save" name="action"
                                            value="<%=pollStatus == PollStatus.NONE ? "create" : "update"%>"><%=btnSaveText%></button>
                                    <% } %>
                                    <% if (pollStatus == PollStatus.CREATED) { %>
                                    <button class="btn btn-primary" id="btn-run" name="action" value="run">Run</button>
                                    <% } %>
                                    <% if (pollStatus == PollStatus.RUNNING) { %>
                                    <button class="btn btn-info" id="btn-release" name="action" value="release">Release</button>
                                    <% } %>
                                    <% if (pollStatus == PollStatus.RUNNING || pollStatus == PollStatus.RELEASED) { %>
                                    <button class="btn btn-primary" id="btn-clear" name="action" value="clear">Clear</button>
                                    <% } %>
                                    <% if (pollStatus == PollStatus.RELEASED) { %>
                                    <button class="btn btn-primary" id="btn-unrelease" name="action" value="unrelease">Unrelease</button>
                                    <button class="btn btn-primary" id="btn-close" name="action" value="close">Close</button>
                                    <% } %>

                                    <button class="btn btn-danger" id="btn-close" name="action" value="logout">Logout</button>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="/assets/jquery/jquery.min.js"></script>
<script src="/assets/bootstrap/bootstrap.bundle.js"></script>
<script src="/js/manager.js"></script>


<script type="text/javascript">
    (function () {
        <% if (error != null) { %>
            alert('<%=error%>');
        <% } %>
    })();
</script>

</body>
</html>