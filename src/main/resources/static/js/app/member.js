var main = {
  init: function () {
    var _this = this;
    $('.submit-btn').on('click', function () {
      _this.nickNameChange();
    });

    $('.pay-done').click(function(){
        _this.paymentDone();
    })
  },

  nickNameChange: function () {
    var data = {
      nickName: $('.nickName').val(),
    };

    var memberId = $('#memberId').text();
    $.ajax({
      type: 'POST',
      url: '/member/mypage/' + memberId,
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('닉네임 변경 완료!');
        window.location.href='/member/mypage/'+memberId;
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  paymentDone : function(){
    var data={
        orderId:$('.orderId').text(),
    };

    var memberId = $('.memberId').text();
        $.ajax({
          type: 'POST',
          url: '/member/paymentLogic/' + memberId,
          contentType: "application/json; charset=utf-8",
          data: JSON.stringify(data),
        })
          .done(function () {
            alert('결제 완료! 마이페이지로 이동합니다.');
            window.location.href='/member/mypage/'+memberId;
          })
          .fail(function (error) {
            alert(JSON.stringify(error));
          });
  }
};

main.init();
