$(document).ready(function() {
    $('#simulBtn').on('click',insertSimul);

    var images = ['#mainimage1', '#mainimage2', '#mainimage3', '#mainimage4'];
    var index = 0;
    var interval;

    // 처음에는 첫 번째 이미지만 보이게 합니다.
    $(images.join(',')).hide();
    $(images[index]).show();

    function cycleImages() {
        var nextIndex = (index + 1) % images.length;

        // 현재 이미지를 왼쪽으로 슬라이드하면서 사라지게 합니다.
        $(images[index]).animate({left: '-100%'}, 700, function() {
            $(this).hide().css('left', '0');
        });

        // 다음 이미지를 오른쪽에서 슬라이드하면서 나타나게 합니다.
        $(images[nextIndex]).css('left', '100%').show().animate({left: '0'}, 700);

        index = nextIndex;
    }

    interval = setInterval(cycleImages, 3500);

    $('#pause').on('click', function(){
        clearInterval(interval);
    })
    $('#play').on('click', function(){
        setTimeout(function(){},1000)
        interval = cycleImages();
        clearInterval(interval);
        interval = setInterval(cycleImages, 3500);
    })
});



// 이름을 랜덤으로 생성하는 함수
function getRandomName() {
    var names = ['정다○','안태○','남지○','주진○','이길○','이태○','홍서○','김미○','오지○','이현○','심세○','전하○','강민○','김희○','김도○','김인○','윤주○','노재○','이상○','이길○','박수○'];
    return names[Math.floor(Math.random() * names.length)];
}

// 기간을 랜덤으로 생성하는 함수 (3개월이 제일 많게)
function getRandomPeriod() {
    var periods = [2, 3, 3, 3, 4, 5, 6, 7, 8, 9, 10];
    return periods[Math.floor(Math.random() * periods.length)];
}

// 거래량을 랜덤으로 생성하는 함수 (기간에 비례)
function getRandomVolume(period) {
    var min = 450;
    var max = 2000;
    var volumePerMonth = (max - min) / 9; // 9 is the range of the period (10 - 2 + 1)
    return Math.round(min + volumePerMonth * (period - 2));
}

// 거래 내용을 생성하는 함수
function generateTrade() {
    var name1 = getRandomName();
    var name2 = getRandomName();
    var period = getRandomPeriod();
    var volume = getRandomVolume(period);

    return '<ul><b>' + name1 + '님과 ' + name2 + '님의 ' + period + '개월 ' + volume + 'kWh 거래가 체결되었습니다.</b></ul>';
}

// 여러 거래 내용을 생성하는 함수
function generateTrades(count) {
    var trades = '';
    for (var i = 0; i < count; i++) {
        trades += generateTrade();
    }
    return trades;
}

// 결과를 출력합니다.
$('#liveTrade').html(generateTrades(30));

$(document).ready(function(){
    // 모달 창을 띄우는 버튼을 클릭하면 모달 창을 띄웁니다.
    $("#simulation").click(function(){
      $("#simulModal").show();
    });
  
    // 닫기 버튼을 클릭하면 모달 창을 닫습니다.
    $(".close").click(function(){
      $("#simulModal").hide();
    });
});

function insertSimul(){
    $('#simulResult').html("분석 중...")
    setTimeout(function(){
        $('#simulResult').html("214,410")
    }, 1000)
}