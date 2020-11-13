let calculatedPrice = document.getElementById('calculated-price'),
    pickUpDate = document.getElementById('pickUpDate'),
    dropOffDate = document.getElementById('dropOffDate'),
    pricePerDay = document.getElementById('discounted-price');

if (pricePerDay == null) {
    pricePerDay = document.getElementById('common-price');
}

function calculatePrice() {
    let days = Math.floor(
        (Date.parse(dropOffDate.value) - Date.parse(pickUpDate.value)) / 86400000);
    if (days > 0) {
        let totalPrice = days * pricePerDay.textContent;
        calculatedPrice.textContent = totalPrice.toFixed(2) + ' BYN / ' + days;
    } else {
        calculatedPrice.textContent = 'INCORRECT DATA';
    }
}

pickUpDate.addEventListener('change', calculatePrice);
dropOffDate.addEventListener('change', calculatePrice);

calculatePrice();

let passportInOfficeCheckBox = document.getElementById('passport-in-office'),
    updatePassport = document.getElementById('update-passport'),
    passportInputs = document.querySelectorAll('#customer-passport .user-data-fields input');

passportInOfficeCheckBox.addEventListener('click', function() {

    if (passportInOfficeCheckBox.checked === true) {
        passportInputs.forEach(function (item) {
            item.removeAttribute('form');
            item.removeAttribute('required');
            item.setAttribute('disabled', '');
        });
        updatePassport.removeAttribute('checked');
        updatePassport.setAttribute('disabled', '');
    } else {
        passportInputs.forEach(function (item) {
            item.setAttribute('form', 'car-rental-form');
            item.setAttribute('required', '');
            item.removeAttribute('disabled');
        });
        updatePassport.setAttribute('checked', 'checked');
        updatePassport.removeAttribute('disabled');
    }

});

let passportInOffice = document.querySelector('.passport-in-office');

passportInputs.forEach(function (item) {
    if (item.value === '') {
        updatePassport.setAttribute('checked', 'checked');
        passportInOffice.style.display = 'initial';
    }
});