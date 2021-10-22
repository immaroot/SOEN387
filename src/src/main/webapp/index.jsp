<%@ page import="ca.concordia.service.PollService" %>
<%@ page import="ca.concordia.core.Poll" %>
<%@ page import="ca.concordia.core.PollStatus" %>
<%@ page import="ca.concordia.core.Choice" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    PollService pollService = PollService.getInstance();

    Poll poll = pollService.getPoll();
    PollStatus pollStatus = poll == null ? PollStatus.NONE : poll.getStatus();
    Map<String, Integer> resultMap = pollService.getPollResults();
    ObjectMapper mapper = new ObjectMapper();
    String chartData = "";
    try {
        chartData = mapper.writeValueAsString(resultMap);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<div class="container pt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-sm-12 col-md-11 col-lg-10 col-xl-9">
            <div class="row pb-5">
                <div class="col">
                    <h2 class="text-center poll-title"><%= poll == null ? "Welcome!" : poll.getTitle()%></h2>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <% if (pollStatus == PollStatus.RUNNING) {%>
                    <div class="card">
                        <h4 class="card-header">
                            <%= poll.getQuestion()%>
                        </h4>
                        <div class="card-body">
                            <ul class="list-group">
                                <%
                                    int index = 0;
                                    for (Choice choice : poll.getChoices()) {
                                %>
                                <li class="list-group-item">
                                    <div>
                                        <label><input type="radio" name="q" value="<%=index%>" class="mr-2"><%=choice.getTitle()%></label>
                                    </div>
                                    <label class="ml-3"><%=choice.getDescription()%></label>
                                </li>
                                <%
                                        index++;
                                    }
                                %>
                            </ul>
                        </div>
                        <div class="card-footer">
                            <div class="text-center">
                                <button class="btn btn-primary" id="btn-vote">Vote</button>
                            </div>
                        </div>
                    </div>

                    <% } else if (pollStatus == PollStatus.RELEASED) { %>

                    <div id="piechart" style="width: 900px; height: 500px;"></div>

                    <div class="text-center">
                        <a class="btn btn-info" id="btn-download">Download</a>
                    </div>

                    <% } else { %>

                    <div class="alert alert-info" role="alert">
                        Poll is not available yet. Please try later.
                    </div>

                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/assets/jquery/jquery.min.js"></script>
<script src="/assets/bootstrap/bootstrap.bundle.js"></script>
<script src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
    var chartData = <%=chartData%>;
</script>
<script src="/js/index.js"></script>

</body>
</html>