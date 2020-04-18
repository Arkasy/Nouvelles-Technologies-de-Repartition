$(function() {
    $(document).on('click', '#navbar-menu', function() {
        $('#uphf-navigation-menu').attr("data-menu", ($('#uphf-navigation-menu').attr("data-menu") == "true") ? "false" : "true" )
    })

    $('#search-button').on('click', function () {
        var arguments = $('.uphf-form-search-criteria').serialize() + "&studio=" + $('#studioFilter').is(':checked') + '&appartement=' + $('#appartementFilter').is(':checked') + '&house=' + $('#houseFilter').is(':checked');
        console.log(arguments);
        $.get('/location/search?' + arguments, function (data) {
            $('#locations-list').html(data);
        })
    })
})