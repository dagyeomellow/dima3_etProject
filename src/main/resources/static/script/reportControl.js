$(document).ready(function() {

    var showSection = function(hideId, showId) {
        $('html, body').animate({
            scrollTop: $(showId).offset().top-105
        }, 100); // 1000ms 동안 스크롤
    };

    var promptUser = function(message) {
        prompt(message);
    };

// zanim.js 애니메이션 재실행

    $("#startReport").click(function() {
        showSection("#page1", "#page2");
    });

    $("#showFirstChart").click(function() {
        showSection("#page2", "#page3");
    });

    $("#homeSolution").click(function() {
        promptUser("상단을 연결하시겠습니까?");
    });

    $("#showPage4").click(function() {
        showSection("#page3", "#page4");
    });

    $("#showSecondChart").click(function() {
        showSection("#page4", "#page5");
    });

    $("#analysisConsult").click(function() {
        promptUser("상단을 연결하시겠습니까?");
    });

    $("#showPage6").click(function() {
        showSection("#page5", "#page6");
    });

    $("#showThirdChart").click(function() {
        showSection("#page6", "#page7");
    });

    $("#solarConsult").click(function() {
        promptUser("상단을 연결하시겠습니까?");
    });

    $("#showPage8").click(function() {
        showSection("#page7", "#page8");
    });
});