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
    let totalPrice = days * pricePerDay.textContent;
    calculatedPrice.textContent = totalPrice.toFixed(2) + ' BYN / ' + days;
}

pickUpDate.addEventListener('change', calculatePrice);
dropOffDate.addEventListener('change', calculatePrice);

calculatePrice();

let useOtherCardBtn = document.getElementById('use-other-card'),
    useLinkedCardsBtn = document.getElementById('use-linked-cards'),
    userPaymentBlock = document.querySelector('.user-payment');

useOtherCardBtn.addEventListener('click', function () {
    let addingBankcard = document.getElementById('adding-bankcard'),
        addBankcardBlock = document.querySelector('.add-bankcard'),
        addBankcard = document.getElementById('add-bankcard'),
        newCardInputs = addingBankcard.querySelectorAll('input'),
        bankcardInputs = document.querySelectorAll('.bankcard input');

    newCardInputs.forEach(function (item) {
        item.setAttribute('form', 'car-rental-form');
        item.removeAttribute('disabled');
    });

    bankcardInputs.forEach(function (item) {
        item.removeAttribute('form');
        item.checked = false;
    });

    addingBankcard.style.display = 'block';
    userPaymentBlock.style.display = 'none';
    useOtherCardBtn.style.display = 'none';
    useLinkedCardsBtn.style.display = 'initial';
    addBankcardBlock.style.display = 'block';
    addBankcard.setAttribute('checked', 'checked');
});

useLinkedCardsBtn.addEventListener('click', function () {
    let addingBankcard = document.getElementById('adding-bankcard'),
        addBankcardBlock = document.querySelector('.add-bankcard'),
        addBankcard = document.getElementById('add-bankcard'),
        newCardInputs = addingBankcard.querySelectorAll('input'),
        bankcardInputs = document.querySelectorAll('.bankcard input');

    newCardInputs.forEach(function (item) {
        item.removeAttribute('form');
        item.removeAttribute('value');
        item.setAttribute('disabled', '');
    });

    bankcardInputs.forEach(function (item) {
        item.setAttribute('form', 'car-rental-form');
    });

    addingBankcard.style.display = 'none';
    userPaymentBlock.style.display = 'block';
    useOtherCardBtn.style.display = 'initial';
    useLinkedCardsBtn.style.display = 'none';
    addBankcardBlock.style.display = 'none';
    addBankcard.removeAttribute('checked');
});

let passportInOfficeCheckBox = document.getElementById('passport-in-office'),
    passportInputs = document.querySelectorAll('#customer-passport .user-data-fields input');

passportInOfficeCheckBox.addEventListener('click', function() {

    if (passportInOfficeCheckBox.checked === true) {
        passportInputs.forEach(function (item) {
            item.removeAttribute('form');
            item.removeAttribute('required');
            item.setAttribute('disabled', '');
        });
    } else {
        passportInputs.forEach(function (item) {
            item.setAttribute('form', 'car-rental-form');
            item.setAttribute('required', '');
            item.removeAttribute('disabled');
        });
    }

});

let updatePassport = document.getElementById('update-passport'),
    passportInOffice = document.querySelector('.passport-in-office');

passportInputs.forEach(function (item) {
    if (item.value === '') {
        updatePassport.setAttribute('checked', 'checked');
        passportInOffice.style.display = 'initial';
    }
});

let paymentInOfficeCheckBox = document.getElementById('payment-in-office');

paymentInOfficeCheckBox.addEventListener('click', function() {
    let bankcard = document.querySelectorAll('.bankcard');

    if (paymentInOfficeCheckBox.checked === true) {
        bankcard.forEach(function (item) {
            item.classList.add('blocked-card');
            let bankcardInput = item.querySelector('input');
            bankcardInput.removeAttribute('form');
            bankcardInput.removeAttribute('required');
            bankcardInput.setAttribute('disabled', '');
            bankcardInput.checked = false;
            useOtherCardBtn.style.display = 'none';
        });
    } else {
        bankcard.forEach(function(item) {
            item.classList.remove('blocked-card');
            let bankcardInput = item.querySelector('input');
            bankcardInput.setAttribute('form', 'car-rental-form');
            bankcardInput.setAttribute('required', '');
            bankcardInput.removeAttribute('disabled');
            useOtherCardBtn.style.display = 'initial';
        });
    }
});