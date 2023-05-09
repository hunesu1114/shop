var main = {
  init: function () {
    var _this = this;
    $('#buy').on('click', function () {
      _this.addToCart();
    });
    $('#buyAllInCart').click(function () {
      _this.buyAllInCart();
    });

    // const orderItemsCnt = $('#orderItems').length;

    // for(let i=0;i<orderItemsCnt;i++){
    //     $('#quantity').eq(i).on('input', function (i) {
    //         _this.quantityChange(i);
    //       });
    // }
  },
  addToCart: function () {
    var data = {
      itemId: $('#itemId').dataset.itemId,
    };
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
  buyAllInCart: function () {
    var data = {
      memberId: $('#memberId').dataset.memberId,
      orderItemId: $('#itemId').text(),
      quantities: $('#quantity'),
    };
    $.ajax({
      type: 'POST',
      url: '/member/' + memberId + 'cart',
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
