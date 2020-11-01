function changeImage(imageSrc) {
    document.getElementById("main_car_img").src = imageSrc;
}

function enableEditing(formName, postButtonID, cancelButtonID) {
    let inputs = document.forms[formName].getElementsByTagName('input');
    let editButtons = document.getElementsByName('edit-button');
    if (document.getElementById('user-menu-content') == null) {
        let selects = document.forms[formName].getElementsByTagName('select');
        for (let i = 0; i < selects.length; i++) {
            selects[i].removeAttribute('disabled');
        }
    }
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].removeAttribute('disabled');
    }
    for (let i = 0; i < editButtons.length; i++) {
        editButtons[i].style.display = 'none';
    }
    document.getElementById(postButtonID).style.display = null;
    document.getElementById(cancelButtonID).style.display = null;
}

function disableEditing(formName, postButtonID, cancelButtonID) {
    let inputs = document.forms[formName].getElementsByTagName('input');
    let editButtons = document.getElementsByName('edit-button');
    if (document.getElementById('user-menu-content') == null) {
        let selects = document.forms[formName].getElementsByTagName('select');
        for (let i = 0; i < selects.length; i++) {
            selects[i].setAttribute('disabled', '');
        }
    }
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].setAttribute('disabled', '');
    }
    for (let i = 0; i < editButtons.length; i++) {
        editButtons[i].style.display = null;
    }
    document.getElementById(postButtonID).style.display = 'none';
    document.getElementById(cancelButtonID).style.display = 'none';
}

function check(registrationButton) {
    let button = document.getElementById(registrationButton);
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

function calculatePrice() {
    let pickUpDate = document.getElementById('pickUpDate').value;
    let dropOffDate = document.getElementById('dropOffDate').value;
    let pricePerDay = document.getElementById('discounted-price');
    if (pricePerDay == null) {
        pricePerDay = document.getElementById('common-price').textContent;
    } else {
        pricePerDay = pricePerDay.textContent;
    }

    let days = Math.floor((Date.parse(dropOffDate) - Date.parse(pickUpDate) ) / 86400000);
    let totalPrice = days * pricePerDay;
    let priceEl = document.getElementById('calculated-price');
    priceEl.textContent = totalPrice.toFixed(2) + ' BYN / ' + days;
}
