<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SuperPoll</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="poll" scope="request" type="ca.concordia.poll.core.Poll"/>
<div class="container pt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-sm-12 col-md-11 col-lg-10 col-xl-9">
            <div class="row pb-5">
                <div class="col">
                    <h2 class="text-center">${poll.title}</h2>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <div class="card">
                        <h4 class="card-header">
                            ${poll.question}
                        </h4>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/poll/${poll.pollID}" method="post">
                                <ul class="list-group mb-3">
                                <c:forEach items="${poll.choices}" var="choice" varStatus="loop">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <p>
                                            <label for="choice${loop.count}">
                                                <strong>${choice.title}</strong> <br>
                                                <small class="text-muted">
                                                <c:if test="${choice.description != null}">
                                                    ${choice.description}
                                                </c:if>
                                                </small>
                                            </label>
                                        </p>
                                        <input type="radio" id="choice${loop.count}" name="choice" value="${choice.title}">
                                    </li>
                                </c:forEach>
                                </ul>
                                <button type="submit" class="btn btn-success float-end">submit vote</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>