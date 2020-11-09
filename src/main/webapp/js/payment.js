let useAnotherBankcardBtn = document.getElementById('use-another-bankcard'),
    anotherBankcard = document.querySelector('.another-bankcard'),
    useLinkedCardsBtn = document.getElementById('use-linked-cards'),
    bankcardsBlock = document.querySelector('.bankcards');

function enableAddingNewCard () {
    let addingBankcard = document.getElementById('adding-bankcard'),
        addBankcardBlock = document.querySelector('.add-bankcard'),
        addBankcard = document.getElementById('add-bankcard'),
        newCardInputs = addingBankcard.querySelectorAll('input'),
        bankcardInputs = document.querySelectorAll('.bankcard input');

    newCardInputs.forEach(function (item) {
        item.setAttribute('form', 'pay-order-form');
        item.setAttribute('required', '');
        item.removeAttribute('disabled');
    });

    bankcardInputs.forEach(function (item) {
        item.removeAttribute('form');
        item.checked = false;
    });

    anotherBankcard.style.padding = '20px';
    addingBankcard.style.display = 'block';
    bankcardsBlock.style.display = 'none';
    useAnotherBankcardBtn.style.display = 'none';
    useLinkedCardsBtn.style.display = 'initial';
    addBankcardBlock.style.display = 'block';
    addBankcard.setAttribute('checked', 'checked');
}

useAnotherBankcardBtn.addEventListener('click', enableAddingNewCard);

let bankcardBlock = document.querySelector('.bankcard');
if (bankcardBlock == null) {
    enableAddingNewCard();
}

useLinkedCardsBtn.addEventListener('click', function () {
    let addingBankcard = document.getElementById('adding-bankcard'),
        addBankcardBlock = document.querySelector('.add-bankcard'),
        addBankcard = document.getElementById('add-bankcard'),
        newCardInputs = addingBankcard.querySelectorAll('input'),
        bankcardInputs = document.querySelectorAll('.bankcard input');

    newCardInputs.forEach(function (item) {
        item.removeAttribute('form');
        item.removeAttribute('value');
        item.removeAttribute('required')
        item.setAttribute('disabled', '');
    });

    bankcardInputs.forEach(function (item) {
        item.setAttribute('form', 'pay-order-form');
    });

    anotherBankcard.style.padding = '0';
    addingBankcard.style.display = 'none';
    bankcardsBlock.style.display = 'block';
    useAnotherBankcardBtn.style.display = 'initial';
    useLinkedCardsBtn.style.display = 'none';
    addBankcardBlock.style.display = 'none';
    addBankcard.removeAttribute('checked');
});