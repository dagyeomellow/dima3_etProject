$(function(){
    $("#reportProsumerBtn").on("click", initProsumer);
    $("#reportConsumerBtn").on("click", initConsumer);
}); // 분석보고서 버튼에 클릭이벤트 추가


/*
init함수: 분석보고서 버튼을 누르면, 보고서 작성에 필요한 모든 데이터를 호출하는 함수
*/
function initProsumer(){
    // const memberId=$("#memberId").val(); // 로그인 완료된 이후 security 되면 memberId 이용.
    let memberId="test_ath"; // ######임시로 이미 저장된 테스트 아이디 사용
    $.ajax({
        url: '/analysis/getProsumerData', // 콘트롤러의 액션 URL
        type: 'GET', // HTTP 메서드 (GET, POST 등)
        data: {
            "memberId": memberId
            }, // 로그인 완료된 이후 넣어줄 예정
        dataType: 'json', // 서버에서 반환되는 데이터 타입
        success: beginProsumerAnalysis
        // error: function(jqXHR, textStatus, errorThrown) { // 요청이 실패하면 실행될 콜백 함수
        //     console.log('Error: ' + errorThrown);
        // }
    });// ajax: /analysis/getData
}// initProsumer
function initConsumer(){
    // const memberId=$("#memberId").val(); // 로그인 완료된 이후 security 되면 memberId 이용.
    let memberId="test_nje"; // ######임시로 이미 저장된 테스트 아이디 사용
    let capacity=$("#capacity").val();
    let cost=$("#cost").val();
    $.ajax({
        url: '/analysis/getConsumerData', // 콘트롤러의 액션 URL
        type: 'GET', // HTTP 메서드 (GET, POST 등)
        data: {
            "memberId": memberId,
            "capacity":capacity,
            "cost":cost
            }, // 로그인 완료된 이후 넣어줄 예정
        dataType: 'json', // 서버에서 반환되는 데이터 타입
        success: beginConsumerAnalysis
        // error: function(jqXHR, textStatus, errorThrown) { // 요청이 실패하면 실행될 콜백 함수
        //     console.log('Error: ' + errorThrown);
        // }
    });// ajax: /analysis/getData
}

// 분석보고서를 총 구성하는 함수
function beginProsumerAnalysis(respData){
    drawActualConsumption(respData['ActualConsumptionMonths'],respData['ActualConsumption']); // 실제소비량 기반 누진구간 막대로 표현하는 그래프//,respData['ActualConsumptionBills']
    drawProsumerProduction(respData['PredictProductionMonths'],respData['ActualProduction'],respData['PredictProduction']);
    drawPrediction(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3']);
}
function beginConsumerAnalysis(respData){
    drawActualConsumption(respData['ActualConsumptionMonths'],respData['ActualConsumption']); // 실제소비량 기반 누진구간 막대로 표현하는 그래프//,respData['ActualConsumptionBills']
    drawBreakEven(respData['RequiredMonths'],respData['NetRevenues'])
    drawPrediction(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3']);
}

// 월과 소비량 리스트를 받아서 누진구간별 소비량 리스트를 반환하는 함수
// 반환 순서: 누진1구간 리스트, 누진2구간 리스트, 누진3구간 리스트
function getProgList(monthList,consList){
    let prog1List=[];
    let prog2List=[];
    let prog3List=[];
    for(let i = 0; i<monthList.length; ++i){
        if ((String(monthList[i]).endsWith('7')) || (String(monthList[i]).endsWith('8'))){
            // console.log(monthList[i])
            // console.log(consList[i])
            if (consList[i]>450){
                prog1List.push(300);
                prog2List.push(150);
                prog3List.push(consList[i]-450);
            } else if(consList[i]>300){
                prog1List.push(300);
                prog2List.push(consList[i]-300);
                prog3List.push(0);
            } else{
                prog1List.push(consList[i]);
                prog2List.push(0);
                prog3List.push(0);
            }
        } else {
            if (consList[i]>400){
                prog1List.push(200);
                prog2List.push(200);
                prog3List.push(consList[i]-400);
            } else if(consList[i]>200){
                prog1List.push(200);
                prog2List.push(consList[i]-200);
                prog3List.push(0);
            } else{
                prog1List.push(consList[i]);
                prog2List.push(0);
                prog3List.push(0);
            }
        }
    }
    let prog={
        'prog1':prog1List, 'prog2':prog2List, 'prog3':prog3List
    }
    return prog;
}

/**
 * 
 * @param {*} monthArr 
 * @param {*} consumptionArr 
 * @param {*} billArr 
 */
function drawActualConsumption(monthArr,consumptionArr){
    // ### 그래프에 들어갈 자료 준비
    let monthList=[];
    let consList=[];
    // let billList=[];
    for (let i =0; i<12; ++i){
        monthList.push(monthArr[i]); 
        consList.push(consumptionArr[i]);
    }
    // console.log(monthList.reverse())
    monthList.reverse();
    consList.reverse();
    // billList.reverse();

    // console.log(monthList); console.log(consList)
    let prog = getProgList(monthList,consList);
    // console.log(prog['prog1']); console.log(prog['prog2']);


    // ### 그래프 그리기
    var options = {
        series: [{name: '누진 1구간',data: prog['prog1']}
        , {name: '누진 2구간',data: prog['prog2']}
        , {name: '누진 3구간',data: prog['prog3']}]
        ,chart: {
            type: 'bar',height: 600,stacked: true
            ,toolbar: {show: true}
            ,zoom: {enabled: true}
        }
        ,responsive: [{breakpoint: 480
            ,options: {
                legend: {position: 'bottom',offsetX: -10,offsetY: 0}
            }
        }]
        ,plotOptions: {bar: {
            horizontal: false,
            borderRadius: 10,
            dataLabels: {
            total: {enabled: true, style: {fontSize: '18px',fontWeight: 900}}
            }
        },
        },
        xaxis: {
        // type: 'String',
        categories: monthList
        },
        legend: {
        position: 'right',
        offsetY: 40
        },
        fill: {
        opacity: 1
        }
    };

    var chart = new ApexCharts(document.querySelector("#previousConsumptionChart"), options);
    chart.render();
}

/**
 * 
 * @param {*} monthsArr 
 * @param {*} actualProdArr 
 * @param {*} predictProdArr 
 */
function drawProsumerProduction(monthsArr,actualProdArr,predictProdArr){
    // 그래프에 들어갈 데이터 준비
    predictProdArr = predictProdArr.map(function(p) {
        return Math.round(p);
    });
    let minProdArr = [];
    let maxProdArr = [];
    // 오차범위 설정(최대 115%, 최소 85%)
    predictProdArr.forEach(element => {
        minProdArr.push(Math.round(element*0.85));
        maxProdArr.push(Math.round(element*1.15));
    });

    // 그래프 그리기
    var options = {
        series: [{
            name: "실제 태양광 발전량",
            data: actualProdArr,
            type: "area"
        },
        {
            name: "최대 추정",
            data: maxProdArr,
            type: "line"
        },
        {
            name: "최소 추정",
            data: minProdArr,
            type: "line"
        }
        ],
        chart: {
            type:"line",
            height: 750
        },
        dataLabels: {
        enabled: false
        },
        stroke: {
        curve: 'smooth',
        dashArray: [0, 10, 10]
        },
        colors:["#99CCCC","#FF0000","#FF0000"],
        opacity:[0.5,0.75,0.75],
        title: {
        text: '당신의 태양광 패널은 안녕하십니까????',
        align: 'left'
        },
        legend: {
        tooltipHoverFormatter: function(val, opts) {
            return val + ' - <strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + '</strong>'
        }
        },
        markers: {
        size: 0,
        hover: {
            sizeOffset: 6
        }
        },
        xaxis: {
        categories: monthsArr,
        },
        tooltip: {
        y: [
            {
            title: {
                formatter: function (val) {
                    return val
                }
            }
            },
            {title: {
                formatter: function (val) {
                return val 
                }
            }
            },
            { title: {
                formatter: function (val) {
                return val;
                }
            }
            }
        ]
        },
        grid: {
        borderColor: '#f1f1f1',
        }
    };

    var chart = new ApexCharts(document.querySelector("#prosumerProductionChart"), options);
    chart.render();
}

// 손익분기점 그래프
function drawBreakEven(requiredMonthsList, netRevenuesList){
    // 데이터 준비
    let point = 0;
    for (let i = 0; i < netRevenuesList.length; i++) {
        const revenue = netRevenuesList[i];
        if (revenue > 0) {
            point = i;
            break;
        }
    }

    let netRevenuesYear = [];
    let yearList = [];
    for (let i = 0; i <= Math.floor(point / 12); i++) {
        netRevenuesYear.push(Math.round(netRevenuesList[i * 12]));
        yearList.push(i*12);
    }
    netRevenuesYear.push(Math.round(netRevenuesList[point-1]));
    yearList.push(point-1);
    netRevenuesYear.push(Math.round(netRevenuesList[point + 11]));
    yearList.push(point+11);
    console.log(netRevenuesYear);
    console.log(yearList)
    console.log(point)
    console.log(netRevenuesYear[-1])
    var options = {
        series: [{
        data: netRevenuesYear
        }],
        chart: {
        type: 'line'
        },
        yaxis: [
            {
                title: {
                    text: '기대순이익'
                }
            }
        ],
        // xaxis:[
        //     {
        //         title:{
        //             text:'경과개월수'
        //         }
        //     }
        // ],
        annotations: {
            yaxis: [{// 0 수평축
            y: 0,
            borderColor: '#ff0000',
            strokeWidth: 5
            }
            ,{// 1년후 수익 수평축
                y:netRevenuesYear[netRevenuesYear.length - 1],
                borderColor: '#FF00FF',
                strokeWidth: 10,
                strokeDashArray: 3,
                label: {
                    borderColor: '#000000',
                    style: {
                        color: '#fff',
                        background: '#880000',
                    },
                    text: `1년 기대수익: ${netRevenuesYear[netRevenuesYear.length - 1]}`,
                    offsetX: -200,
                    orientation: 'horizontal'
                }
            }
            ],
            xaxis: [{//손익분기점 표시
            x: point-1,
            strokeDashArray: 5,
            borderColor: '#FF4560',
            label: {
                borderColor: '#FF4560',
                style: {
                    color: '#fff',
                    background: '#FF4560',
                },
                text: `손익분기점: ${Math.floor((point-1)/12)}년 ${point%12}개월`,
                offsetY: 250,
                orientation: 'horizontal'
            }
            }],
        },
        dataLabels: 
        {enabled: false}
        , stroke: {curve: 'smooth'}
        , grid: {
            padding: {
            right: 30,
            left: 20
            }
        }
        , title: {
        text: '손익분기점을 알고 싶나요?',
        align: 'left'
        }
    , labels: yearList
    };

    var chart = new ApexCharts(document.querySelector("#consumerBreakevenChart"), options);
    chart.render();
}

function drawPrediction(monthList,consList, price1List, price2List, price3List){
    let expenditure=getExpenditure(monthList,consList, price1List, price2List, price3List);
    var optionsBar = {
        chart: {
            type: 'bar',
            events: {
                dataPointMouseEnter: function(event, chartContext, config) {
                    var index = config.dataPointIndex;
                    chartPie.updateSeries(pieData[index]);
                }
            }
        },
        plotOptions: {
            bar: {
                horizontal: true,
            }
        },
        series: [{
            data: expenditure['totalExpenditure']
        }],
        xaxis: {
            categories: monthList,
        }
    };

    var chartBar = new ApexCharts(document.querySelector("#predictExpenditureBar"), optionsBar);
    chartBar.render();

    var pieData = [
        [expenditure['prog1Expenditure'][0],expenditure['prog2Expenditure'][0],expenditure['prog3Expenditure'][0]],
        [expenditure['prog1Expenditure'][1],expenditure['prog2Expenditure'][1],expenditure['prog3Expenditure'][1]],
        [expenditure['prog1Expenditure'][2],expenditure['prog2Expenditure'][2],expenditure['prog3Expenditure'][2]],
        [expenditure['prog1Expenditure'][3],expenditure['prog2Expenditure'][3],expenditure['prog3Expenditure'][3]]

    ];

    var optionsPie = {
        
        chart: {
            type: 'pie'
        },
        series: pieData[0],
        labels: ['누진1구간요금', '누진2구간요금', '누진3구간요금']
    };

    var chartPie = new ApexCharts(document.querySelector("#predictExpenditurePie"), optionsPie);
    chartPie.render();
}

/**
 * 예측시점, 예측소비량, 예측요금(누진구간별)을 받아서 총지출 및 구간별 지출을 반환해주는 함수
 * @param {*} monthList 
 * @param {*} consList 
 * @param {*} price1List 
 * @param {*} price2List 
 * @param {*} price3List 
 * @returns 
 */
function getExpenditure(monthList,consList, price1List, price2List, price3List){
    let progList = getProgList(monthList,consList)
    let totalExpenditure =[];
    let prog1Expenditure =[];
    let prog2Expenditure =[];
    let prog3Expenditure =[];

    for (let i=0; i<consList.length;++i){
        let pTotal = 0;
        pTotal=(progList['prog1'][i]*price1List[i])+(progList['prog2'][i]*price2List[i])+(progList['prog3'][i]*price3List[i]);
        totalExpenditure.push(Math.round(pTotal))

        let p1 = 0;
        p1 = (progList['prog1'][i]*price1List[i]);
        prog1Expenditure.push(Math.round(p1))

        let p2 = 0;
        p2 = (progList['prog2'][i]*price2List[i]);
        prog2Expenditure.push(Math.round(p2))

        let p3 = 0;
        p3 = (progList['prog3'][i]*price3List[i]);
        prog3Expenditure.push(Math.round(p3))
    }
    let expenditure={
        'totalExpenditure': totalExpenditure,
        'prog1Expenditure': prog1Expenditure,
        'prog2Expenditure': prog2Expenditure,
        'prog3Expenditure': prog3Expenditure
    };

    return expenditure;
}



