let returnAct = document.getElementById('return-act'),
    returnDate = document.getElementById('return-date'),
    newCarOdometer = document.getElementById('new-car-odometer'),
    orderStatus = document.getElementById('order-status');

function enableReturnAct() {
    if (orderStatus.value === '5') {
        returnAct.style.display = 'block';
        returnDate.setAttribute('required', '');
        newCarOdometer.setAttribute('required', '');
    } else {
        returnAct.style.display = 'none';
        returnDate.removeAttribute('required');
        newCarOdometer.removeAttribute('required');
    }
}

orderStatus.addEventListener('change', enableReturnAct);

enableReturnAct();

let allInputs = document.querySelectorAll('input'),
    allTextarea = document.querySelectorAll('textarea'),
    allSelects = document.querySelectorAll('select'),
    changeOrderBtn = document.getElementById('change-order-btn');
function disableAllInputs() {
    allInputs.forEach(function (item) {
        item.setAttribute('disabled', '');
    })

    allTextarea.forEach(function (item) {
        item.setAttribute('disabled', '');
    })

    allSelects.forEach(function (item) {
        item.setAttribute('disabled', '');
    })
}

function enableAllInputs() {
    allInputs.forEach(function (item) {
        item.removeAttribute('disabled');
    })

    allTextarea.forEach(function (item) {
        item.removeAttribute('disabled');
    })

    allSelects.forEach(function (item) {
        item.removeAttribute('disabled');
    })
}

let enableOrderEditingBtn = document.getElementById('enable-order-editing'),
    cancelOrderEditingBtn = document.getElementById('cancel-order-editing');

enableOrderEditingBtn.addEventListener('click', function () {
    enableAllInputs();
    changeOrderBtn.style.display = 'block';
    enableOrderEditingBtn.style.display = 'none';
    cancelOrderEditingBtn.style.display = 'block';
})

cancelOrderEditingBtn.addEventListener('click', function () {
    disableAllInputs();
    changeOrderBtn.style.display = 'none';
    enableOrderEditingBtn.style.display = 'block';
    cancelOrderEditingBtn.style.display = 'none';
})

if (orderStatus.value === '5') {
    disableAllInputs();
    changeOrderBtn.style.display = 'none';
    enableOrderEditingBtn.style.display = 'block';
    cancelOrderEditingBtn.style.display = 'none';
}