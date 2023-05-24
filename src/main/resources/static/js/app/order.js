var main = {
  init: function () {
    var _this = this;
    $('#buy').on('click', function () {
      _this.addToCart();
    });

    $('#buyAllInCart').on('submit', function () {
      _this.cartToOrder();
    });

    $('.pay-done').click(function () {
      _this.paymentDone();
    });
  },


  paymentDone: function () {
    var data = {
      orderId: $('.orderId').text(),
    };

    var memberId = $('.memberId').text();
    $.ajax({
      type: 'POST',
      url: '/member/paymentLogic/' + memberId,
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('결제 완료! 마이페이지로 이동합니다.');
        window.location.href = '/member/mypage/' + memberId;
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },


};

main.init();
