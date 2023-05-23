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

  //안먹힘
  addToCart: function () {
    var data = {
      itemId: $('#itemId').text(),
    };
    var memberId = $('body').data('memberId');
    var itemId = $('#itemId').text();
    $.ajax({
      type: 'POST',
      url: '/item/' + itemId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('장바구니로 이동합니다.');
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  cartToOrder: function () {
    var data = {};

    var memberId = $('.memberId').text();
    $.ajax({
      type: 'POST',
      url: `/member/${memberId}/cart`,
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('주문 페이지로 이동합니다.');
        window.location.href = '/member/mypage/' + memberId;
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
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

  //   quantityChange: function (index) {
  //     var data={
  //         orderItemId:$('#orderItemId').eq(index).text(),
  //         quantity:$('#quantity').eq(index)
  //     }
  //     $.ajax({
  //         type: 'POST',
  //         url: '/member/' + memberId + 'cart',
  //         dataType: 'json',
  //         contentType: 'application/json; charset=utf-8',
  //         data: JSON.stringify(data),
  //       })
  //         .done(function () {
  //           alert('장바구니로 이동합니다.');
  //         })
  //         .fail(function (error) {
  //           alert(JSON.stringify(error));
  //         });
  //   },
};

main.init();
