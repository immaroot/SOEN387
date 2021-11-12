<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h2 class="text-center">The Results</h2>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-body justify-content-center">
                            <div class="mx-auto my-auto justify-content-center">
                                <jsp:useBean id="results" scope="request" type="java.util.HashMap<java.lang.String,java.lang.Integer>"/>
                                <c:forEach items="${results}" var="entry">
                                    <div class="list-group">
                                        <ul class="list-group mb-3">
                                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                                <p>${entry.key}</p>
                                                <p>${entry.value}</p>
                                            </li>
                                        </ul>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="card-footer">
                            <form action="${pageContext.request.contextPath}/download" method="get">
                                <div class="form-group">
                                    <label for="format">Choose format to download</label>
                                    <select class="form-control" id="format">
                                        <option>rows</option>
                                        <option>csv</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-success">Download</button>
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