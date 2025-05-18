document.addEventListener('click', function (evt) {
    const btn = evt.target.closest('button[data-category-id]');
    if (btn) {
        const catId = btn.getAttribute('data-category-id');
        const target = document.getElementById('posts-list-cat__' + catId);
        const arrow = btn.querySelector('.category-arrow');
        if (target && arrow) {
            target.classList.toggle('hidden');
            arrow.textContent = target.classList.contains('hidden') ? '►' : '▼';
        }
    }
});