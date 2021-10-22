window.jQuery(function ($) {
    var chartDiv = $('#piechart');
    $('#btn-vote')['click'](function () {
        var input = $('input[name=q]:checked');
        var choice = input.val();
        if (!choice) {
            alert('Please choose one!');
            return;
        }

        $['post']('/choice', {
            choice: choice
        }).done(function (res) {
            if (!res.success) {
                alert(res.message);
                return;
            }
            alert('Voted successfully!');
        }).fail(function (res) {
            console.log(res);
            alert('Something went wrong with the network.');
        })
    });

    $('#btn-download').click(function () {
        window.open('/download?' + $.param({
            name: $('.poll-title').text(),
            format: 'text'
        }), 'dn');
    });

    function initChart() {
        if (!chartDiv.length) return;
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);
    }

    function drawChart() {
        console.log(chartData);
        var gData = [['Choice', 'Count']];
        $.each(Object.keys(chartData), function (i, k) {
            gData.push([k, chartData[k]])
        });
        console.log(gData);
        var data = google.visualization.arrayToDataTable(gData);

        var options = {
            title: 'Poll Results'
        };

        var chart = new google.visualization.PieChart(chartDiv[0]);

        chart.draw(data, options);
    }

    initChart();
});