$(document).ready(function ($) {
    WIDTH = $(window).width();
    HEIGHT = $(window).height();

    $('.main-row').css("height", HEIGHT - $('.top_info_div').height() + "px");
    $('#add-new-user-btn').click(function () {
        $(this).css({"color": "#000", "background": "#fff", "border-color": "#D5D1D1"});
        $('#users-table-btn').css({"color": "#007bff", "background": "transparent", "border-color": "transparent"});
        $('.admin-page-content-wrapper').css("display", "none");
        $('.add-new-user-content-wrapper').css("display", "block");
    })
    $('#users-table-btn').click(function () {
        $(this).css({"color": "#000", "background": "#fff", "border-color": "#D5D1D1"});
        $('#add-new-user-btn').css({
            "color": "#007bff",
            "background": "transparent",
            "border-color": "transparent"
        });
        $('.admin-page-content-wrapper').css("display", "block");
        $('.add-new-user-content-wrapper').css("display", "none");
    })

});