$(document).ready(function() {
    $('#country').change(function() {
        $('#myForm').submit();
        console.log('counrty changed');
    });
});
