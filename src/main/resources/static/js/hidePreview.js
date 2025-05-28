document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('search-input');
    const preview = document.getElementById('search-preview');

    // hide the preview when clicking outside
    document.addEventListener('click', function (e) {
        if (!input.contains(e.target) && !preview.contains(e.target)) {
            preview.style.display = 'none';
        }
    });

    // show the preview when focusing on input if there is text
    input.addEventListener('focus', function () {
        if (input.value.trim() !== '') {
            preview.style.display = 'block';
        }
    });

    // hides the preview if the input is empty
    input.addEventListener('input', function () {
        if (input.value.trim() === '') {
            preview.style.display = 'none';
        } else {
            preview.style.display = 'block';
        }
    });
});

// to hide the profile dropdown when clicking outside
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.profile-btn').forEach(function(btn) {
        const dropdown = btn.parentElement.querySelector('.profile-dropdown');
        btn.addEventListener('click', function (e) {
            e.stopPropagation();
            dropdown.classList.toggle('hidden');
        });

        // hide the dropdown when clicking outside
        document.addEventListener('click', function () {
            dropdown.classList.add('hidden');
        });

        // prevent the dropdown from closing when clicking inside it
        dropdown.addEventListener('click', function (e) {
            e.stopPropagation();
        });
    });
});