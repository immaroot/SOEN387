<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="polls" scope="request" type="java.util.List<ca.concordia.poll.core.Poll>"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SuperPoll</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<div class="container pt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-sm-12 col-md-11 col-lg-10 col-xl-9">
            <div class="row pb-5">
                <div class="col">
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
            </div>
            <div class="row pb-5">
                <div class="col">
                    <h2 class="text-center">Admin Section</h2>
                </div>
            </div>
            <c:forEach items="${polls}" var="poll" varStatus="loop">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="mx-auto my-auto justify-content-center p-5">
                                <h2>ID: ${poll.pollID}</h2>
                                <h2>Title: ${poll.title}</h2>
                                <h2>Question: ${poll.question}</h2>
                                <h4>
                                    The poll is currently in <c:choose><c:when test="${poll.open}">OPEN</c:when><c:otherwise>CLOSED</c:otherwise></c:choose> state.
                                </h4>
                                <h4>
                                    The poll has currently a ${poll.status} status.
                                </h4>
                            </div>
                            <div class="card-footer">
                                <form action="admin?poll=${poll.pollID}" method="post">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <c:choose>
                                                <c:when test="${poll.open}">
                                                    <c:if test="${poll.status == 'CREATED'}">
                                                        <button class="btn btn-primary" name="action" value="run">Run Poll</button>
                                                    </c:if>
                                                    <c:if test="${(poll.status == 'CREATED' || poll.status == 'RUNNING')}">
                                                        <button class="btn btn-primary" name="action" value="update">Update</button>
                                                    </c:if>
                                                    <c:if test="${poll.status == 'RUNNING'}">
                                                        <button class="btn btn-primary" name="action" value="release">Release</button>
                                                        <button class="btn btn-primary" name="action" value="clear">Clear</button>
                                                    </c:if>
                                                    <c:if test="${poll.status == 'RELEASED'}">
                                                        <button class="btn btn-primary" name="action" value="clear">Clear</button>
                                                        <button class="btn btn-primary" name="action" value="unrelease">Unrelease</button>
                                                        <button class="btn btn-primary" name="action" value="close">Close</button>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-primary" name="action" value="create">Create</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>