function changeImage(imageSrc) {
    document.getElementById("main_car_img").src = imageSrc;
}

function enableEditing(formName, postButtonID, cancelButtonID) {
    var inputs = document.forms[formName].getElementsByTagName('input');
    var editButtons = document.getElementsByName('edit-button');
    if (document.getElementById('user-menu-content') == null) {
        var selects = document.forms[formName].getElementsByTagName('select');
        for (var i = 0; i < selects.length; i++) {
            selects[i].removeAttribute('disabled');
        }
    }
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].removeAttribute('disabled');
    }
    for (var i = 0; i < editButtons.length; i++) {
        editButtons[i].style.display = 'none';
    }
    document.getElementById(postButtonID).style.display = null;
    document.getElementById(cancelButtonID).style.display = null;
}

function disableEditing(formName, postButtonID, cancelButtonID) {
    var inputs = document.forms[formName].getElementsByTagName('input');
    var editButtons = document.getElementsByName('edit-button');
    if (document.getElementById('user-menu-content') == null) {
        var selects = document.forms[formName].getElementsByTagName('select');
        for (var i = 0; i < selects.length; i++) {
            selects[i].setAttribute('disabled', '');
        }
    }
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].setAttribute('disabled', '');
    }
    for (var i = 0; i < editButtons.length; i++) {
        editButtons[i].style.display = null;
    }
    document.getElementById(postButtonID).style.display = 'none';
    document.getElementById(cancelButtonID).style.display = 'none';
}

function check(registration_button) {
    var button = document.getElementById(registration_button);
    if (document.getElementById('politics').checked) {
        button.disabled = '';
        button.classList.remove('inactive');
    } else {
        button.disabled = 'disabled';
        button.classList.add('inactive');
    }
}

function confirmAction(message) {
    return confirm(message);
}

function closeWarning() {
    window.location.href = 'mainController?command=go_to_main_page';
}
