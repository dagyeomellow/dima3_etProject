// Chart.defaults.backgroundColor = '#999999';
Chart.defaults.borderColor = '#6f42c1';
Chart.defaults.color = '#fff';


const memberId=$("#memberId").val();
const memberRole=$("#memberRole").val();

$(document).ready(function() {
    init();
    
    $('#breakevenBtn').on('click', chart2)
    
});
/**
 * 에이잭스 실행 목록
 */


function init(){
    $.ajax({
        url: 'report/prepare',
        type: 'GET',
        data:{
            "memberId": memberId
        },
        dataType: 'json',
        success: function(resp){
            // console.log("결과: ", resp['result']);
            // AJAX 요청이 성공적으로 완료되면, 이후 함수들을 호출합니다.
            chart1();
            chart3();
            chart2();
        }
    })
}

function chart1(){
    $.ajax({
        url: 'report/getActualConsumptionData',
        type: 'GET',
        data:{
            "memberId": memberId
        },
        dataType: 'json',
        success: drawActualConsumption
    })
}
function chart2(){
    if(memberRole=='ROLE_PROSUMER'){
        $.ajax({
            url: 'report/getPredictProductionData',
            type: 'GET',
            data:{
                "memberId": memberId
            },
            dataType: 'json',
            success: drawPredictProdcution
        })
    } else if(memberRole=='ROLE_CONSUMER'){
        let capacity=$("input[type=radio][name=capacity]").val();
        let cost=$('#cost').val()*10000;
        
        $.ajax({
            url: 'report/getBreakevenData',
            type: 'GET',
            data:{
                "memberId": memberId,
                "capacity": capacity,
                "cost": cost
            },
            dataType: 'json',
            success: drawBreakeven,
            
        }),
        waitforChart()
    }
}
function chart3(){
    $.ajax({
        url: 'report/getFutureData',
        type: 'GET',
        data:{
            "memberId": memberId
        },
        dataType: 'json',
        success: drawFuture
    })
}
/**
 * ----------------------------------------------------------------------------
 */

/**
 * 일반 함수 목록(함수 내의 기능들 목록)
 */
function calculateAverage(arr) {
    const sum = arr.reduce((acc, curr) => acc + curr, 0);
    return Math.round(sum / arr.length);
}

function getProgList(monthList,consList){
    let prog1List=[];
    let prog2List=[];
    let prog3List=[];
    for(let i = 0; i<monthList.length; ++i){
        if ((String(monthList[i]).endsWith('7월')) || (String(monthList[i]).endsWith('8월'))){
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
        'prog3Expenditure': prog3Expenditure,
        'expenditureMonths': months
    };

    return expenditure;
}
function getPanelStatus(predictProdAvg, actualProdAvg){
    if (predictProdAvg*1.5<actualProdAvg){
        return "주의: 지나치게 발전량이 많아요!"
    } else if (predictProdAvg*0.95<actualProdAvg){
        return "정상: 예측량과 비슷한 발전량이네요!"
    } else if (predictProdAvg*0.85<actualProdAvg){
        return '<span class="fs-2" style="color: #FF0066">관리요망</span> 발전량이 낮은 수준이에요!'
    } else if (predictProdAvg*0.7>actualProdAvg){
        return "심각: 태양광 패널 상태 점검이 필요해보여요!"
    }
}
/**
 * ----------------------------------------------------------------------------
 */

/**
 * 차트 함수 목록
 */
function drawActualConsumption(resp){
    let months = [];
    let consList=[];
    let actualConsumptionMonths=resp['ActualConsumptionMonths'];
    let actualConsumption=resp['ActualConsumption'];
    for(let i=0; i<12; ++i){
        let year = actualConsumptionMonths[i].toString().substring(0,4);
        let month = actualConsumptionMonths[i].toString().substring(4,6);
        let stringValue = `${year}년 ${month}월`;
        months.push(stringValue);

        consList.push(actualConsumption[i]);
    }
    months.reverse(); consList.reverse();
    let prog = getProgList(months, consList);

    const labels = months;
    const data = {
        labels: labels,
        datasets: [
            {
            label: '누진세 1구간',
            data: prog['prog1'],
            backgroundColor: 'rgba(0, 255, 0, 0.6)',
            borderWidth:2,
            borderColor:'rgba(0, 255, 0, 1)'
            },
            {
            label: '누진세 2구간',
            data: prog['prog2'],
            backgroundColor: 'rgba(255, 255, 0, 0.6)',
            borderWidth:2,
            borderColor:'rgba(255, 255, 0, 1)'
            },
            {
            label: '누진세 3구간',
            data: prog['prog3'],
            backgroundColor: 'rgba(255, 0, 0, 0.6)', 
            borderWidth:2,
            borderColor:'rgba(255, 0, 0, 1)'
            },
        ] // @@ 차트의 배경색은 html 스타일부분에서 처리
    };
    const config = {
        type: 'bar',
        data: data,
        options: {
            responsive: false,
            scales: {
                x: {
                    stacked: true,
                    ticks: {
                        fontColor: 'white' // x축 텍스트 색상
                    },
                    grid:{
                        display: false
                    }
                },
                y: {
                    stacked: true,
                    ticks: {
                        fontColor: 'white', // y축 텍스트 색상
                        callback: function(value, index, values) {
                            return value + 'kWh'; // y축 값에 "kWh" 붙이기
                        }
                    }
                }
            },
            layout: {
                padding: 20
            },
            plugins: {
                grid: {
                    display: true,
                    drawTicks: false,
                    },
                title: {
                    display: true,
                    text: '누진세 구간별 우리집 전력소비량',
                    font: {
                        size: 24,
                        weight: 'bold',
                        color:'#ffffff'
                    }
                },
                legend: {
                    labels: {
                        fontColor: 'white' // 범례 텍스트 색상
                    }
                },
                datalabels: {
                    // color: 'black',
                    backgroundColor: 'white',
                    // borderRadius: 4,
                    formatter: function(value, context) {
                        let sum = 0;
                        for (let i = 0; i <= context.dataIndex; i++) {
                            sum += context.dataset.data[i];
                        }
                        return sum; // 각 막대의 누적값을 표시
                    }
                }
            }
        }
    };

    const ctx = document.getElementById('chart1').getContext('2d');
    new Chart(ctx, config);

    let yearAvg=calculateAverage(consList);

    let summer = [];
    let etc = [];
    for (let i = 0; i < months.length; i++) {
        let m = months[i];
        let c = consList[i];
        if (m.endsWith('07월') || m.endsWith('08월')) {
            summer.push(c);
        } else {
            etc.push(c);
        }
    }

    let summerAvg = summer.reduce((a, b) => a + b, 0) / summer.length;
    let etcAvg = etc.reduce((a, b) => a + b, 0) / etc.length;
    let summerDiff=summerAvg-etcAvg;

    let prog2Total = prog['prog2'].reduce((a,b)=> a+b, 0);
    let prog3Total = prog['prog3'].reduce((a,b)=> a+b, 0);
    
    $('#chart1result1').html(`<span class="far fa-check-circle text-700 me-2"></span>작년 한 해 월평균 사용량: <b>${yearAvg} kWh</b>`);
    $('#chart1result2').html(`<span class="far fa-check-circle text-700 me-2"></span>평월 대비 여름 월평균 사용량: <b>${summerDiff} kWh</b>`);
    $('#chart1result3').html(`
        <span class="far fa-check-circle text-700 me-2"></span>누진 3구간 총사용량: <b class="fs-2" style="color: #FF0066">${prog3Total} kWh</b>
        <br><span class="far fa-check-circle text-700 me-2"></span>누진 2구간 총사용량: <b class="fs-2" style="color: #ccaa00">${prog2Total} kWh</b>`);


}

function drawPredictProdcution(resp){
    let months = [];
    let minProdArr = [];
    let maxProdArr = [];
    let actualProdArr=resp['ActualProduction']
    console.log(actualProdArr)
    let predictProdArr=resp['PredictProduction'];
    let predictProdMonths=resp['PredictProductionMonths']
    for(let i=0; i<12; ++i){
        let year = predictProdMonths[i].toString().substring(0,4);
        let month = predictProdMonths[i].toString().substring(4,6);
        let stringValue = `${year}년 ${month}월`;
        months.push(stringValue);
    }

    predictProdArr = predictProdArr.map(function(p) {
        return Math.round(p);
    });

    // 오차범위 설정(최대 115%, 최소 85%)
    predictProdArr.forEach(element => {
        minProdArr.push(Math.round(element*0.85));
        maxProdArr.push(Math.round(element*1.15));
    });

    const data = {
        labels: months,
        datasets: [
            {
            label: '실제 태양광 발전량',
            data: actualProdArr,
            borderColor: '#D032D0',
            backgroundColor: '#D032D0',
            fill: false
            },
            {
            label: '발전량 최대추정치',
            data: maxProdArr,
            borderColor: 'rgba(255, 255, 0, 1)',
            backgroundColor: 'rgba(255, 255, 0, 0.6)',
            hidden: true,
            fill: {
                target: '+1',
                below: 'rgba(0, 255, 0, 0.4)',
                above: 'rgba(0, 255, 0, 0.4)'

            },
            borderWidth:2
            },
            {
            label: '발전량 최소추정치',
            data: minProdArr,
            borderColor: 'rgba(255, 0, 0, 1)',
            backgroundColor: 'rgba(255, 0, 0, 0.6)',
            hidden: true,
            fill: false,
            borderWidth:2
            },
            {
            label: '추정 태양광 발전량',
            data: predictProdArr,
            borderColor: '#E7E4EE',
            backgroundColor: '#E7E4EE',
            fill: {below: 'rgba(0, 255, 0, 0.4)', above: 'rgba(255, 0, 0, 0.4)', target: '-3'},
            borderWidth:2
            }
        ]
    };
    const config = {
        type: 'line',
        data: data,
        options: {
            responsive:false,
            scales: {
                y: {
                    stacked: false,
                    ticks: {
                        fontColor: 'white', // y축 텍스트 색상
                        callback: function(value, index, values) {
                            return value + 'kW'; // y축 값에 "kW" 붙이기
                        }
                    },
                    grid: {
                        display: false
                    }
                }
            },
            plugins: {
                filler: {
                    propagate: false
                },
                'samples-filler-analyser': {
                    target: 'chart-analyser'
                },
                title: {
                    display: true,
                    text: '실제 및 추정 태양광 발전량',
                    font: {
                        size: 24,
                        weight: 'bold',
                        color:'#ffffff'
                    }
                },
                legend: {
                    labels: {
                        fontColor: 'white' // 범례 텍스트 색상
                    }
                },
                },
                interaction: {
                    intersect: false,
                },

        },
    };
    const ctx = document.getElementById('chart2').getContext('2d');
    new Chart(ctx, config);

    let prodAvg= Math.round(actualProdArr.reduce((a,b)=> a+b, 0) / actualProdArr.length);
    let prodDiff= actualProdArr.reduce((a,b)=> a+b, 0) - predictProdArr.reduce((a,b)=> a+b, 0);
    let panelStatus=getPanelStatus(predictProdArr.reduce((a,b)=> a+b, 0), actualProdArr.reduce((a,b)=> a+b, 0))

    $('#chart2result1').html(`<span class="far fa-check-circle text-700 me-2"></span>작년 한 해 월평균 발전량: <b>${prodAvg} kWh</b>`);
    $('#chart2result2').html(`<span class="far fa-check-circle text-700 me-2"></span>추정발전량과 실제발전량의 평균 차이: <b>${prodDiff} kWh</b>`);
    $('#chart2result3').html(`<span class="far fa-check-circle text-700 me-2"></span>태양광 패널의 관리 상태-<br> <b>${panelStatus}</b>`);
}

function drawFuture(respData){
    // drawPrediction(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3']);
    let expenditure=getExpenditure(respData['PredictPriceMonths'],respData['PredictConsumption'],respData['PredictPriceProg1'],respData['PredictPriceProg2'],respData['PredictPriceProg3'])

    var pieData = [
        [expenditure['prog1Expenditure'][0],expenditure['prog2Expenditure'][0],expenditure['prog3Expenditure'][0]],
        [expenditure['prog1Expenditure'][1],expenditure['prog2Expenditure'][1],expenditure['prog3Expenditure'][1]],
        [expenditure['prog1Expenditure'][2],expenditure['prog2Expenditure'][2],expenditure['prog3Expenditure'][2]],
        [expenditure['prog1Expenditure'][3],expenditure['prog2Expenditure'][3],expenditure['prog3Expenditure'][3]]
    ];

    const labels = expenditure['expenditureMonths'];

// Bar chart
var ctxBar = document.getElementById('chart3').getContext('2d');
if(window.chartBar != undefined)
    window.chartBar.destroy();
window.chartBar = new Chart(ctxBar, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: '월간 전기요금',  // Add legend
            data: expenditure['totalExpenditure'],
            backgroundColor: 'rgba(255, 204, 255, 0.6)',
            borderColor: 'rgba(255, 204, 255, 1)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: false,
        indexAxis: 'y',
        scales: {
            x: {
                beginAtZero: true,
                ticks: {
                    // Add '원' to the x-axis labels
                    callback: function(value, index, values) {
                        return new Intl.NumberFormat('ko-KR').format(value) + '원';
                    }
                }
            }
        },
        plugins: {
            title: {
                display: true,
                text: '우리집 4개월 예상 전기요금',  // Add chart title
                font: {
                    size: 24  // Set font size
                }
            },
            tooltip: {
                enabled: true,
                callbacks: {
                    // Add '원' to the tooltip labels
                    label: function(context) {
                        var label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.parsed.x !== null) {
                            label += new Intl.NumberFormat('ko-KR').format(context.parsed.x) + '원';
                        }
                        return label;
                    },
                    beforeTitle: function(tooltipItems) {
                        var index = tooltipItems[0].dataIndex;
                        chartPie.data.datasets[0].data = pieData[index];
                        chartPie.update();
                    }
                }
            }
        }
    }
});

    // Pie chart
    var ctxPie = document.getElementById('chart4').getContext('2d');
    if(window.chartPie != undefined)
        window.chartPie.destroy();
    window.chartPie = new Chart(ctxPie, {
        type: 'doughnut',
        data: {
            labels: ['누진 1구간', '누진 2구간', '누진 3구간'],
            datasets: [{
                data: pieData[0],
                backgroundColor: [
                    'rgba(0, 255, 0, 0.6)',
                    'rgba(255, 255, 0, 0.6)',
                    'rgba(255, 0, 0, 0.6)'
                ],
                borderColor: [
                    'rgba(0, 255, 0, 1)',
                    'rgba(255, 255, 0,1)',
                    'rgba(255, 0, 0, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: false,
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            var total = 0;
                            var dataArr = tooltipItem.dataset.data;
                            dataArr.map(data => {
                                total += data;
                            });
                            var value = tooltipItem.parsed;
                            var percentage = ((value/total) * 100).toFixed(1);
                            return tooltipItem.label + ': ' + new Intl.NumberFormat('ko-KR').format(value) + '원 (' + percentage + '%)';
                        }
                    }
                }
            }
        }
    });
    var totalExpenditure = 0;
    for (var i = 0; i < pieData.length; i++) {
        for (var j = 0; j < pieData[i].length; j++) {
            totalExpenditure += pieData[i][j];
        }
    }

    var prog1Expenditure = 0;
    var prog2Expenditure = 0;
    var prog3Expenditure = 0;
    for (var i = 0; i < pieData.length; i++) {
        prog1Expenditure += pieData[i][0];
        prog2Expenditure += pieData[i][1];
        prog3Expenditure += pieData[i][2];
    }

    var prog1Percentage = ((prog1Expenditure / totalExpenditure) * 100).toFixed(2);
    var prog2Percentage = ((prog2Expenditure / totalExpenditure) * 100).toFixed(2);
    var prog3Percentage = ((prog3Expenditure / totalExpenditure) * 100).toFixed(2);

    $('#chart3result1').html('<span class="far fa-check-circle text-700 me-2"></span>누진 3구간 비중: <span style="color: #FF0066">' + prog3Percentage + '%</span><br>' +
                             '<span class="far fa-check-circle text-700 me-2"></span>누진 2구간 비중: <span style="color: #ccaa00">' + prog2Percentage + '%</span><br>' +
                             '<span class="far fa-check-circle text-700 me-2"></span>누진 1구간 비중: <span style="color: #66aa00">' + prog1Percentage + '%</span><br>');

}

function drawBreakeven(resp){
    let requiredMonthsList =resp["RequiredMonths"]
    let netRevenuesList=resp["NetRevenues"]
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
    

    var chartElement = document.querySelector("#chart2");
    if (chartElement) {
        chartElement.innerHTML = '';
    }
    var options = {
        series: [{
            data: netRevenuesYear,
            name: '예상 순이익', // Set data label value
            color: '#FFB6C1' // Set line color to light pink
        }],
        chart: {
            type: 'line',
            width: 600,
            height: 500,
            background: 'rgba(0,0,33,0.8)', // Set chart background color to black
            foreColor: '#ffffff', // Set chart text color to white
            toolbar: {
                show: false // Remove toolbar
            },
            border: {
                width: 8, // Set border width
                color: '#800080' // Set border color to purple
            }
        },
        yaxis: [
            {
                title: {
                    text: '기대순이익'
                },
                labels: {
                    formatter: function (value) {
                        return value.toLocaleString() + '원'; // Format y-axis as "1,000,000원"
                    }
                }
            }
        ],
        xaxis: {
            title: {
                text: '소요 개월' // Set x-axis label title
            }
        },
        annotations: {
            xaxis: [{//손익분기점 표시
                x: point-1,
                strokeDashArray: 5,
                borderColor: '#FF4560',
                label: {
                    borderColor: '#FF4560',
                    style: {
                        color: '#fff',
                        background: '#FF4560',
                        fontSize: '21px' // Set annotation text size
                    },
                    text: `손익분기점: ${Math.floor((point-1)/12)}년 ${point%12}개월`,
                    offsetY: 250,
                    offsetX: -100,
                    orientation: 'horizontal'
                }
            }],
        },
        dataLabels: 
        {enabled: false},
        stroke: {curve: 'smooth'},
        grid: {
            padding: {
                right: 30,
                left: 20
            }
        },
        title: {
            text: '태양광 발전기 설치시 손익분기점',
            align: 'center',
            style: {
                fontSize: '24px' // Set title size
            }
        },
        labels: yearList
    };
    
    var chart = new ApexCharts(document.querySelector("#chart2"), options);
    chart.render();

    console.log(requiredMonthsList[requiredMonthsList.length-1])
    let benefitmonths=(requiredMonthsList[requiredMonthsList.length-1]-12)*0.2;

    $('#breakevenResult').html(`${Math.floor(benefitmonths/12)}년 ${Math.floor(benefitmonths%12)}개월`);
}

function waitforChart(){
    var chartElement = document.querySelector("#chart2");
    if (chartElement) {
        chartElement.innerHTML = '<h2 class="fs-3 pt-7"><b>불러오는 중</b></h2>';
    }
}
