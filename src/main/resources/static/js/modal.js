document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('editAccountForm');
    if (!form) return;

    const emailInput = form.querySelector('input[name="email"]');
    const originalEmail = emailInput ? emailInput.value : "";

    form.addEventListener('submit', function (e) {
        let msg = "Are you sure you want to update your information?";
        if (emailInput && emailInput.value !== originalEmail) {
            msg = "Changing your email will require you to log in again. Do you want to continue?";
        }
        if (!window.confirm(msg)) {
            e.preventDefault();
            form.reportValidity();
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form[th\\:action="@{/account/edit-password}"], form[action="/account/edit-password"]');
    if (!form) return;

    form.addEventListener('submit', function (e) {
        const msg = "Changing your password will require you to log in again. Do you want to continue?";
        if (!window.confirm(msg)) {
            e.preventDefault();
            form.reportValidity();
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const logoutBtn = document.getElementById('logoutBtn');
    const logoutForm = logoutBtn ? logoutBtn.closest('form') : null;

    if (logoutBtn && logoutForm) {
        logoutBtn.addEventListener('click', function (e) {
            if (window.confirm('Are you sure you want to log out?')) {
                logoutForm.submit();
            }
        });
    }
});