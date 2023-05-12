var main = {
  init: function () {
    var _this = this;
    $('.submit-btn').on('click', function () {
      console.log($('#memberId').data('memberId'));
      _this.nickNameChange();
    });
  },

  nickNameChange: function () {
    var data = {
      nickName: $('#nickName').val(),
    };

    var memberId = $('#memberId').data('memberId');
    $.ajax({
      type: 'POST',
      url: '/member/mypage/' + memberId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('닉네임 변경 완료!');
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

main.init();
