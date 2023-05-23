var main = {
  init: function () {
    var _this = this;
    $('.submit-btn').on('click', function () {
      _this.nickNameChange();
    });




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




};

main.init();
