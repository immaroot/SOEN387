window.jQuery(function ($) {
    var rowChoices = $('.row-choices');
    $('#btn-add-choice').click(function () {
        rowChoices.append($('<div class="row mb-2">\n' +
            '<label class="col-md-2">Choice: </label>\n' +
            '<div class="col-md-4">\n' +
            '<input type="text" class="form-control" name="choiceTitle">\n' +
            '</div>\n' +
            '<label class="col-md-2">Description: </label>\n' +
            '<div class="col-md-4">\n' +
            '<input type="text" class="form-control" name="choiceNotes">\n' +
            '</div>\n' +
            '</div>'));
        return false;
    });

    $('#btn-save').click(function () {
        if (!saveData()) {
            return false;
        }
        return true;
    });

    function saveData() {
        var data = {
            title: $('[name=title]').val(),
            question: $('[name=question]').val(),
            choices: []
        };
        if (!data.title) return alert('Please input title');
        if (!data.question) return alert('Please input question');

        var valid = true;
        $.each(rowChoices.children(), function (i, row) {
            row = $(row);
            var choice = {
                title: row.find('[name=choiceTitle]').val(),
                notes: row.find('[name=choiceNotes]').val() || ' '
            };
            if (!choice['title']) {
                valid = false;
                return false;
            }
            data.choices.push(choice);
        });
        if (!valid) return alert('Please type choice title');
        if (data.choices.length < 2) return alert('Please add choices');

        console.log(data);
        return true;
    }
});