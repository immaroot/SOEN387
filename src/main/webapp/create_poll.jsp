<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SuperPoll</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="container pt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-sm-12 col-md-11 col-lg-10 col-xl-9">
            <div class="row pb-5">
                <div class="col">
                    <h2 class="text-center">Create a new poll</h2>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <form action="${pageContext.request.contextPath}/create" method="post">
                            <div class="card-body">
                                    <div class="row">
                                        <div class="mb-5 px-4">
                                            <label for="name">
                                                Name of the Poll
                                            </label>
                                            <input type="text" class="form-control" id="name" name="name" />
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="mb-5 px-4">
                                            <label for="question">
                                                The Question
                                            </label>
                                            <input type="text" class="form-control" id="question" name="question"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="mb-2">
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                    <div class="mb-3">
                                                        <label for="option1">
                                                            <strong>Option 1</strong>
                                                        </label>
                                                        <input type="text" class="form-control" id="option1" name="choice" />
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="text-muted" for="description1">
                                                            Description (optional)
                                                        </label>
                                                        <textarea class="form-control" id="description1" name="description"> </textarea>
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="mb-3">
                                                        <label for="option2">
                                                            <strong>Option 2</strong>
                                                        </label>
                                                        <input type="text" class="form-control" id="option2" name="choice"/>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="text-muted" for="description2">
                                                            Description (optional)
                                                        </label>
                                                        <textarea class="form-control" id="description2" name="description"> </textarea>
                                                    </div>
                                                </li>
                                                <div id="newRow"></div>
                                            </ul>
                                        </div>
                                    </div>
                            </div>
                            <div class="card-footer">
                                <div class="row">
                                    <div class="col d-flex justify-content-between">
                                        <button id="addNewRow" type="button" class="btn btn-danger">Add another option</button>
                                        <button class="btn btn-success" type="submit">Create</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">
    let i = 3;

    $("#addNewRow").click(function() {
        let html = '';
        html +=    '<li class="list-group-item">                                         ';
        html +=    '    <div class="mb-3">                                               ';
        html +=    '        <label for="option' + i + '">                                         ';
        html +=    '            <strong>Option ' + i + '</strong>                                ';
        html +=    '        </label>                                                     ';
        html +=    '        <input type="text" class="form-control" id="option' + i + '" name="choice"/>       ';
        html +=    '    </div>                                                           ';
        html +=    '    <div class="mb-3">                                               ';
        html +=    '        <label class="text-muted" for="description' + i + '">                 ';
        html +=    '            Description (optional)                                   ';
        html +=    '        </label>                                                     ';
        html +=    '        <textarea class="form-control" id="description' + i + '" name="description"> </textarea> ';
        html +=    '    </div>                                                           ';
        html +=    '</li>                                                                 ';

        console.log(i);
        i++;
        console.log("now i is: " + i);
        $("#newRow").append(html);
    });
</script>
</body>
</html>