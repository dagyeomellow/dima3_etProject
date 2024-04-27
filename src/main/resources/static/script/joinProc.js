let currentPage = 1;

$(document).ready(function(){
    init();
    $('#joinDocu').hide();
    $('#joinBtn').on('click', startJoinProc);
    $('#nextBtn').on('click',moveNext);
    $('#prevBtn').on('click',movePrev);
    $('form').on('submit', function(e) {
        e.preventDefault();
        submitProc();
    });
    $("input[type=radio][name=customerType]").on(
        'change',typeOptions);
    $("input[type=radio][name=solarPanel]").on('change',solarCapacity);
    
});

function init(){
    btnControl();
    showPage();    
}

function startJoinProc(){
    $('#joinInfo').hide();
    $('#joinDocu').show();
    $('#information').removeAttr('hidden');
    $('#prevBtn').removeAttr('hidden')
    $('#nextBtn').removeAttr('hidden')
}

function showPage(){
    for(let i =1; i<5; ++i){
        $(`#step${i}`).hide();
    }
    let step = $(`#step${currentPage}`);
    step.show();
    updatePagination();
}

function submitProc(){
    // 개인정보 이용동의(필수) 안되면 경고 띄우고 반려하기
    var isChecked = $('input[name="isAgree"]').is(':checked');
    if (!isChecked) {
        alert("개인정보 이용에 동의하지 않으시면, 회원가입이 불가합니다.");
        return
    }

    // form 데이터를 가져옵니다.
    var formData = $('form').serialize();

    // AJAX 요청을 보냅니다.
    $.ajax({
        type: 'POST',
        url: '/members/joinForm',
        data: formData,
        success: function(response) {
            // 요청이 성공적으로 완료되면 실행될 코드를 여기에 작성합니다.
            currentPage=4;
            showPage();
            btnControl();
            alert(response+"님 환영합니다!")
        },
        error: function(error) {
            // 요청이 실패하면 실행될 코드를 여기에 작성합니다.
            console.error(error);
        }
    });
}

function updatePagination() {
    // 모든 페이지 번호에서 'active' 클래스를 제거합니다.
    $('.number').removeClass('active');
    // currentPage에 해당하는 페이지 번호에 'active' 클래스를 추가합니다.
    $(`.number:contains(${currentPage})`).addClass('active');
}

function btnControl(){
    console.log(currentPage)
    let nextBtn = $('#nextBtn');
    let prevBtn = $('#prevBtn');
    switch(currentPage){
        case 1:
            prevBtn.prop('disabled', true);
            nextBtn.prop('disabled', false);
            break;
        case 2:
            prevBtn.prop('disabled', false);
            nextBtn.prop('disabled', false);
            break;
        case 3: 
        prevBtn.prop('disabled', false);
        nextBtn.prop('disabled', true);
            break;
        case 4:
            prevBtn.hide();
            nextBtn.hide();
            break;
    }
}

function moveNext(){
    currentPage++;
    showPage();
    btnControl();
}

function movePrev(){
    currentPage--;
    showPage();
    btnControl();
}

function typeOptions(){
    // 선택바 옵션들
    const options = {
        general: ["갑1", "갑2","을"],
        residential: ["저압", "고압"]
    };
    // 회원 유형 선택 이벤트 처리
    const consumerType = $("input[type=radio][name=customerType]:checked").val();
    const consumerTypeKey= consumerType == "주택용" ? "residential":"general";
    let contractType = $('#contractType');
    contractType.empty();
    options[consumerTypeKey].forEach(
        option=>{
            const optionElement = $("<option></option>").val(option).text(option);
            contractType.append(optionElement);
        }
    ); //forEach
    $('#optionContainer').removeAttr('hidden');
    
}
function solarCapacity(){
    let solarPanel=$("input[type=radio][name=solarPanel]:checked").val();
    console.log(solarPanel)
    if(solarPanel=='solarTrue'){
        $('#capacity').removeAttr('hidden');
    } else if (solarPanel=='solarFalse'){
        $('#capacity').attr('hidden', true);
    }
}
