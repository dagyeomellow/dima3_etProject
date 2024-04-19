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
    
});

function init(){
    btnControl();
    showPage();
    typeOptions();

    
}

function startJoinProc(){
    $('#joinInfo').hide();
    $('#joinDocu').show();
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
    $("input[type=radio][name=customerType]").on(
        'change',
        function(){
            const consumerType = this.value;
            const consumerTypeKey= consumerType == "주택용" ? "residential":"general";
            let contractType = $('#contractType');
            contractType.empty();
            options[consumerTypeKey].forEach(
                option=>{
                    const optionElement = $("<option></option>").val(option).text(option);
                    contractType.append(optionElement);
                }
            ); //forEach
            $('#optionContainer').show();
        }//function
    )
}




// 회원가입 폼 제출 처리
// document.getElementById("information").addEventListener("submit", function(event) {
//     event.preventDefault(); // 폼 기본 동작 방지
//     const formData = new FormData(this);
// // 여기서 formData를 이용하여 서버에 전송하거나 다른 처리를 할 수 있습니다.
//     console.log("회원 유형:", formData.get("userType"));
//     console.log("선택한 항목:", formData.get("selection"));
// });

// (function(){
//     let currentPage=1;
//     const prevBtn = document.querySelector(".form .footer .prev");
//     const nextBtn = document.querySelector(".form .footer .next");
//     // let page = 1;

//     function movePage(){
//         prevBtn.disabled=false;
//         nextBtn.disabled=false;
//         if(currentPage === 1){
//             prevBtn.disabled=true;
//         } else if(currentPage === 4){
//             nextBtn.disabled=true;
//         }
//         document.querySelector(".form .pagination .active").classList.remove("active");
//         document.querySelectorAll(".form .pagination .number")[currentPage-1].classList.add("active");
//         const stepNode = document.querySelector(".form .steps .step");
//         const width = ((currentPage-1)*stepNode.offsetWidth*-1)+"px";
//         stepNode.parentNode.style.marginLeft = width;
//     };
    

//     prevBtn.addEventListener("click",function(){
//         currentPage -= 1;
//         movePage();
//     });
//     nextBtn.addEventListener("click",function(){
//         console.log("넥스트 눌림")
//         currentPage += 1;
//         movePage();
//     });
// });
