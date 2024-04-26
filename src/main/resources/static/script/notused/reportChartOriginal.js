const memberRole=$("#memberRole").val();
const memberId=$("#memberId").val();


$(document).ready(function() {
    init()
});

function init(){
    if (memberRole=='ROLE_PROSUMER'){
        chartProsumer()
    } else if(memberRole=='ROLE_CONSUMER'){
        chartConsumer()
    }
}

function chartProsumer(){
    $.ajax({
        url: 'report/getProsumerData', // 콘트롤러의 액션 URL
        type: 'GET', // HTTP 메서드 (GET, POST 등),
        data: {
            "memberId": memberId
            }, // 로그인 완료된 이후 넣어줄 예정
        dataType: 'json', // 서버에서 반환되는 데이터 타입
        success: beginProsumerAnalysis
        // error: function(jqXHR, textStatus, errorThrown) { // 요청이 실패하면 실행될 콜백 함수
        //     console.log('Error: ' + errorThrown);
        // }
    });// ajax: /analysis/getData
    // 보노보노 넣기 
}// initProsumer
function chartConsumer(){
    let capacity=parseInt($("#capacity").val());
    let cost=parseInt($("#cost").val());
    $.ajax({
        url: 'report/getConsumerData', // 콘트롤러의 액션 URL
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
    //보노보노넣기
}

// 분석보고서를 총 구성하는 함수
function beginProsumerAnalysis(respData){
    // drawActualConsumption(respData['ActualConsumptionMonths'],respData['ActualConsumption']); // 실제소비량 기반 누진구간 막대로 표현하는 그래프//,respData['ActualConsumptionBills']
    drawActualConsumption(respData['ActualConsumptionMonths'],respData['ActualConsumption'])
    drawProsumerProduction(respData['PredictProductionMonths'],respData['ActualProduction'],respData['PredictProduction']);
    // drawProsumerProduction(respData['PredictProductionMonths'],respData['ActualProduction'],respData['PredictProduction']);
    drawPrediction(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3']);

}
function beginConsumerAnalysis(respData){
    drawActualConsumption(respData['ActualConsumptionMonths'],respData['ActualConsumption']) // 실제소비량 기반 누진구간 막대로 표현하는 그래프//,respData['ActualConsumptionBills']
    .then(()=>{
        drawBreakEven(respData['RequiredMonths'],respData['NetRevenues']);
        drawPrediction(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3']);
    }) 
}

// 월과 소비량 리스트를 받아서 누진구간별 소비량 리스트를 반환하는 함수
// 반환 순서: 누진1구간 리스트, 누진2구간 리스트, 누진3구간 리스트
function getProgList(monthList,consList){
    let prog1List=[];
    let prog2List=[];
    let prog3List=[];
    for(let i = 0; i<monthList.length; ++i){
        if ((String(monthList[i]).endsWith('07월')) || (String(monthList[i]).endsWith('08월'))){
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
    let months=[];
    let consList=[];
    // let billList=[];
    for (let i =0; i<12; ++i){
        months.push(monthArr[i]); 
        consList.push(consumptionArr[i]);
    }
    // console.log(monthList.reverse())
    months.reverse();
    consList.reverse();
    let monthList=[];
    months.forEach((v,i)=>{
        let year = v.toString().substring(0,4);
        let month = v.toString().substring(4,6);
        let stringValue = `${year}년 ${month}월`;
        monthList.push(stringValue);
    })
    // billList.reverse();

    // console.log(monthList); console.log(consList)
    let prog = getProgList(monthList,consList);
    // console.log(prog['prog1']); console.log(prog['prog2']);


    // ### 그래프 그리기
    var options = {
        series: [
            {name: '누진 1구간', data: prog['prog1']},
            {name: '누진 2구간', data: prog['prog2']},
            {name: '누진 3구간', data: prog['prog3']}
        ],
        chart: {
            type: 'bar',
            stacked: true,
            width: 700,
            height: 425,
            toolbar: {show: true},
            zoom: {enabled: true},
            fontFamily: 'Noto Sans' // 모든 글꼴을 NotoSans로 변경
        },
        colors: ['#00FF00', '#FFFF00', '#FF0000'],
        responsive: [{
            breakpoint: 480,
            options: {
                legend: {position: 'bottom', offsetX: -10, offsetY: 0}
            }
        }],
        plotOptions: {
            bar: {
                horizontal: false,
                borderRadius: 10,
                dataLabels: {
                    total: {
                        enabled: true,
                        style: {
                            fontSize: '13px',
                            fontWeight: 900
                        },
                    },
                colors: '#000000'
                },
            },
        },
        xaxis: {
            categories: monthList,
            labels: {
                style: {
                    fontSize: '12px', // x축 라벨 텍스트의 폰트 사이즈를 키움
                    fontFamily: 'Noto Sans' // x축 라벨 텍스트의 글꼴을 NotoSans로 변경
                }
            }
        },
        yaxis: {
            labels: {
                formatter: function (value) {
                    return value + ' kWh';
                },
                style: {
                    fontSize: '13px', // y축 라벨 텍스트의 폰트 사이즈를 키움
                    fontFamily: 'Noto Sans' // y축 라벨 텍스트의 글꼴을 NotoSans로 변경
                }
            }
        },
        legend: {
            fontSize: 10,
        fontWeight: 500,
        offsetX: 0,
        offsetY: 0,
        markers: {
            width: 13,
            height: 13,
            shape: "square",
            size: 8
        },
        theme: {
            mode: "dark"
        },
        fill: {
            opacity: 0.7
        }
    }
}
    var chart = new ApexCharts(document.querySelector("#chart1"), options);
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
            name: "실제 발전량",
            data: actualProdArr,
            type: "area"
        },
        {
            name: "추정 최대발전량",
            data: maxProdArr,
            type: "line"
        },
        {
            name: "추정 최소발전량",
            data: minProdArr,
            type: "line"
        }
        ],
        chart: {
            type:"line",
            width: 730,
            height: 425,
            toolbar:{show:true}
        },

        dataLabels: {
        enabled: false
        },
        stroke: {
        curve: 'straight',
        dashArray: [0, 4, 4]
        },
        colors:["#FFFF00","#00FF00","#FF0000"],
        opacity:[0.9,0.5,0.5],
        legend: {
        tooltipHoverFormatter: function(val, opts) {
            return val + '<strong>' + opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] + 'kWh</strong>'
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

    };

    var chart = new ApexCharts(document.querySelector("#chart2"), options);
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

    var options = {
        series: [{
        data: netRevenuesYear
        }],
        chart: {
        type: 'line',
        width: 730,
        height: 425,
        },
        yaxis: [
            {
                title: {
                    text: '기대순이익'
                }
            }
        ],
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
            width: 315,
            height: 425,
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
            , width: 315,
            height: 425
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
    let months=[];
    monthList.forEach((v,i)=>{
        let year = v.toString().substring(0,4);
        let month = v.toString().substring(4,6);
        let stringValue = `${year}년 ${month}월`;
        months.push(stringValue);
    })


    let progList = getProgList(months,consList)
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



