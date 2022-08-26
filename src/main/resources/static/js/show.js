var clockTarget = document.getElementById("clock");


function clock() {
    var date = new Date();

    // date Object를 받아오고
    var month = date.getMonth();

    // 달을 받아옵니다
    var clockDate = date.getDate();

    // 몇일인지 받아옵니다
    var day = date.getDay();

    // 요일을 받아옵니다.
    var week = ['일', '월', '화', '수', '목', '금', '토'];

    // 요일은 숫자형태로 리턴되기때문에 미리 배열을 만듭니다.
    var hours = date.getHours();

    // 시간을 받아오고
    var minutes = date.getMinutes();

    // 분도 받아옵니다.
    var seconds = date.getSeconds();

    // 초까지 받아온후
    clockTarget .innerText =

        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes }`  : minutes }`;

    // 월은 0부터 1월이기때문에 +1일을 해주고

    // 시간 분 초는 한자리수이면 시계가 어색해보일까봐 10보다 작으면 앞에0을 붙혀주는 작업을 3항연산으로 했습니다.
}



function init() {

    clock();

// 최초에 함수를 한번 실행시켜주고
    setInterval(clock, 1000);

// setInterval이라는 함수로 매초마다 실행을 해줍니다.

// setInterval은 첫번째 파라메터는 함수이고 두번째는 시간인데 밀리초단위로 받습니다. 1000 = 1초

}

init();

$(function(){
    $("input[type='text']").keypress(function(e){
        if(e.keyCode == 13 && $(this).val().length){
            var _val = $(this).val();
            var _class = $(this).attr("class");
            $(this).val('');
            
            var _tar = $(".chat_wrap .inner").append('<div class="item '+_class+'"><div class="box"><p class="msg">'+_val+'</p><span class="time">'+currentTime()+'</span></div></div>');

            var lastItem = $(".chat_wrap .inner").find(".item:last");
            setTimeout(function(){
                lastItem.addClass("on");
            },10);

            var position = lastItem.position().top + $(".chat_wrap .inner").scrollTop();
            console.log(position);

            $(".chat_wrap .inner").stop().animate({scrollTop:position},500);
        }
    });

});


