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
});


function getMinPrice(){
    let minPricestr=new Intl.NumberFormat('ko-KR').format(tradeMinPrice) + ' 원'
    $('#tradeMinPrice').html(`최소이득률에 따른 최소금액은 <b style="color: rgba(247, 144, 62, 0.9)"><u>${minPricestr}</u></b>입니다`)
};
