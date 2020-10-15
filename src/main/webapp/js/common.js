function changeImage(imageSrc) {
    document.getElementById("main_car_img").src = imageSrc;
}

function enableEditing(formName, buttonName, ...editButtons) {
    var form = document.forms[formName].getElementsByTagName('input');
    for (var i = 0; i < form.length; i++) {
        form[i].removeAttribute('disabled');
    }
    for (var i = 0; i < editButtons.length; i++) {
        document.getElementById(editButtons[i]).style.display = 'none';
    }
    document.getElementById(buttonName).style.display = null;
}