/**
 * DatePicker 사용을 위한 javascript
 */
$(function() {
    $(".datepicker").datepicker({
        authoHide: true,
        format: 'yyyy-mm-dd',
        language: 'ko-KR',
        weekStart: 0
    });

});
