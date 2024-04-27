let optimalP=$("#optimalPrice").val();
let tradeMinPnt = $("#tradeMinPercent").val();
let tradeMinP=Math.round(optimalP*(tradeMinPnt/100));
let minPstr=new Intl.NumberFormat('ko-KR').format(tradeMinP) + ' 원'

let tradeMinPercent = $("#tradeMinPercent").val();
let tradeMinPrice=Math.round(optimalP*(tradeMinPercent/100));

let resultPrice=$('#resultPrice').val();
let resultPercent= Math.round((resultPrice/tradeMinPrice)*100)

$(document).ready(function() {
    $('#tradeMinPrice').html(`최소이득률에 따른 최소금액은 <b style="color: rgba(247, 144, 62, 0.9)"><u>${minPstr}</u></b>입니다`)
    $("#tradeMinPercent").on('change',getMinPrice)
    $('#resultPercent').html(`요청금액의 <b style="color: #D032D0;">${resultPercent}%</b>의 가격으로<br>거래하실 수 있습니다.`)
    // 초기 설정: #page1을 제외한 모든 section 숨기기
    $("section:not(#page1)").hide();

    // #startConsult 버튼 클릭 이벤트
    $("#startConsult").click(function() {
        $("#page1").hide();
        $("#loading").show();
        setTimeout(function() {
            $("#loading").hide();
            $("#page2").show();
        }, 3550);
    });

    // #startMatch 버튼 클릭 이벤트
    $("#startMatch").click(function() {
        $("#page2").hide();
        $("#page3").show();
    });

    // #doMatching 버튼 클릭 이벤트
    $("#doMatching").click(function() {
        $("#page3").hide();
        $("#matching").show();
        setTimeout(function() {
            $("#matching").hide();
            $("#page4").show();
        }, 3000);
    });

    // #doTrade 버튼 클릭 이벤트
    $("#doTrade").click(function() {
        var r = confirm("결제하시겠습니까?");
        if (r == true) {
            alert("거래가 완료되었습니다. 한국전력공사로 거래 결과가 전송됩니다. 자세한 사항은 마이페이지를 확인해주세요.");
            $("#page4").hide();
            $("#page5").show();
        }
    });


    
});


function getMinPrice(){
    let minPricestr=new Intl.NumberFormat('ko-KR').format(tradeMinPrice) + ' 원'
    $('#tradeMinPrice').html(`최소이득률에 따른 최소금액은 <b style="color: rgba(247, 144, 62, 0.9)"><u>${minPricestr}</u></b>입니다`)
};


$(document).ready(function() {

});