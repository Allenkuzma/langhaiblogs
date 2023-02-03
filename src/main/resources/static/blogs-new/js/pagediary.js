layui.use(['jquery','flow'], function () {
    var $ = layui.jquery;
    //$('.monthToggle').click(function () {
    //    $(this).parent('h3').siblings('ul').slideToggle('slow');
    //    $(this).siblings('i').toggleClass('fa-caret-down fa-caret-right');
    //});
    $('.yearToggle').click(function () {
        $(this).parent('h2').siblings('.timeline-month').slideToggle('slow');
        $(this).siblings('i').toggleClass('fa-caret-down fa-caret-right');
    });
});
