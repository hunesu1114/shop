var main = {
  init: function () {
    var _this = this;
    $('#btn-save').on('click', function () {
      _this.save();
    });

    $('#btn-update').on('click', function () {
      _this.update();
    });

    $('#btn-delete').on('click', function () {
      _this.delete();
    });

    $('.search').click(function () {
      _this.search();
    });

    $('.searchPageSearch').click(function () {
      _this.searchPageSearch();
    });
  },
  save: function () {
    var data = {
      name: $('#name').val(),
      price: $('#price').val(),
      feature: $('#feature').val(),
    };

    $.ajax({
      type: 'POST',
      url: '/item/registration/api',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('상품이 등록되었습니다.');
        window.location.href = '/item/list/1';
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  update: function () {
    var data = {
      name: $('#name').val(),
      price: $('#price').val(),
      feature: $('#feature').val(),
    };

    var id = $('#id').val();

    $.ajax({
      type: 'PUT',
      url: '/item/edit/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {
        alert('상품 정보가 수정되었습니다.');
        window.location.href = '/item/' + id;
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  search: function () {
    var data = {
      keyword: $('#keyword').val(),
      page: $('#page').text(),
    };

    var id = $('#id').val();

    $.ajax({
      type: 'POST',
      url: '/item/list/' + page,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {})
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  searchPageSearch: function () {
    var data = {
      keyword: $('#keyword').val(),
    };

    var id = $('#id').val();

    $.ajax({
      type: 'POST',
      url: '/item/list/' + page,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
    })
      .done(function () {})
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
  delete: function () {
    alert('상품이 삭제되었습니다.');
  },
};

main.init();
