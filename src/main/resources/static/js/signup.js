const form = document.querySelector('form');

form.addEventListener('submit', (event) => {
    const userId = document.querySelector('#userId');
    const password = document.querySelector('#password');
    const userName = document.querySelector('#userName');
    const email = document.querySelector('#email');
    const phoneNumber = document.querySelector('#phoneNumber');

    let isValid = true;

    if (userId.value === '') {
        document.querySelector('#userId ~ .error-message').classList.add('error-message');
        isValid = false;
    } else {
        document.querySelector('#userId ~ .error-message').classList.remove('error-message');
    }

    if (password.value === '') {
        document.querySelector('#password ~ .error-message').classList.add('error-message');
        isValid = false;
    } else {
        document.querySelector('#password ~ .error-message').classList.remove('error-message');
    }

    if (userName.value === '') {
        document.querySelector('#userName ~ .error-message').classList.add('error-message');
        isValid = false;
    } else {
        document.querySelector('#userName ~ .error-message').classList.remove('error-message');
    }

    // check other fields...

    if (!isValid) {
        event.preventDefault();
    }
});