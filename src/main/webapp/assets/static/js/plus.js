$('[data-toggle="tooltip"]').tooltip();
//active state
$(function () {
    $('ul.list-ul-sim li a').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        $this.closest('ul.list-ul-sim').find('.active').removeClass('active');
        $this.parent().addClass('active');
    });
});

var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.maxHeight){
            panel.style.maxHeight = null;
        } else {
            panel.style.maxHeight = panel.scrollHeight + "px";
        }
    });
}

function myTopnav() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}


// try {
//     onLoad();
// }
// catch(err) {
//
// }
// function onLoad() {
//     new InputMask().Initialize(document.querySelectorAll("#birth-date"),
//         {
//             mask: InputMaskDefaultMask.Date,
//             placeHolder: "Tìm theo ngày sinh"
//         });
// }
//


jQuery(document).ready(function($){

    $(document)
        .on('change','input[type="checkbox"]',function(e){
            var $t = $(this);
            var $form = $t.closest('form');
            var name = $t.attr('name');
            var selector = 'input[type="checkbox"]';
            var m = (new RegExp('^(.+)\\[([^\\]]+)\\]$')).exec( name );
            if( m ){
                selector += '[name^="'+m[1]+'["][name$="]"]';
            }else{
                selector += '[name="'+name+'"]';
            }
            $(selector, $form).not($t).prop('checked',false);
        });

});