$(document).ready(function() {
    // 초기 설정: #page1을 제외한 모든 section 숨기기
    $("section:not(#page1)").hide();

    // 버튼 클릭 이벤트 정의
    defineButtonClickEvents();

    // 최소이득률에 따른 최소금액 계산 및 출력
    calculateAndDisplayMinPrice();

    
});

function defineButtonClickEvents() {
    $("#startConsult").click(function() {
        $("#page1").hide();
        $("#loading").show();
        setTimeout(function() {
            $("#loading").hide();
            $("#page2").show();
        }, 3555);
    });

    $("#startMatch").click(function() {
        $("#page2").hide();
        $("#page3").show();
    });

    $("#doMatching").click(function() {
        $("#page3").hide();
        $("#matching").show();
        setTimeout(function() {
            $("#matching").hide();
            $("#page4").show();
        }, 3555);
        // 요청금액의 가격 계산 및 출력
        calculateAndDisplayResultPercent();
    });

    $("#doTrade").click(function() {

        alert("거래가 완료되었습니다. 한국전력공사로 거래 결과가 전송됩니다. 자세한 사항은 마이페이지를 확인해주세요.");
        $("#page4").hide();
        $("#page5").show();

    });
}

function calculateAndDisplayMinPrice() {
    let optimalP = $("#optimalPrice").val();
    let tradeMinPercent = $("#tradeMinPercent").val();
    let tradeMinPrice = Math.round(optimalP * (tradeMinPercent / 100));
    let minPriceStr = new Intl.NumberFormat('ko-KR').format(tradeMinPrice) + ' 원';

    $('#tradeMinPrice').html(`<b>
    설정하신 수익률에 따른 최소거래액은 <br> <b class="fs-3 ps-8" style="color: #6f42c1"><u>${minPriceStr}</u></b> 입니다
</b>`);
    $("#tradeMinPercent").on('input', calculateAndDisplayMinPrice);
    console.log($('#tradeMinPercent').val()+"sdsd")
}

function calculateAndDisplayResultPercent() {
    
    let resultPrice = $('#resultPrice').val();
    let tradeMinPrice = Math.round($("#optimalPrice").val() * ($("#tradeMinPercent").val() / 100));
    let resultPercent = Math.round((resultPrice / tradeMinPrice) * 100);

    $('#resultPercent').html(`<b>설정하신 금액보다<br><span class="fs-4" style="color:#6f42c1">${resultPercent-100}%</span> 높은 가격으로 <br> <span class="">거래하실 수 있습니다.</span></b>`);
}