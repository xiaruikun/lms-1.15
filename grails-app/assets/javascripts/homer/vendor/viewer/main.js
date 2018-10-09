window.onload = function() {

    'use strict';
    // var viewer = new Viewer(document.querySelector('.docs-pictures'));
    var galley = document.querySelector('.docs-pictures');
    var viewer = new Viewer(galley, {
            url: 'data-original',
            navbar:false
        });
};
