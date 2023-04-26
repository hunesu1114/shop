var main = {
    init : function () {
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
    },
    save : function () {
        var data = {
            name: $('#name').val(),
            price: $('#price').val(),
            feature: $('#feature').val()
        };

        $.ajax({
            type: 'POST',
            url: '/item/registration/api',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('상품이 등록되었습니다.');
            window.location.href = '/item/list/1';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    /*여기서 오류 생김*/
    update : function () {
        var data = {
            name: $('#name').val(),
            price: $('#price').val(),
            feature: $('#feature').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/item/edit/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('상품 정보가 수정되었습니다.');
            window.location.href = '/item/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();