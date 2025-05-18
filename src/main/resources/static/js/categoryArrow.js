document.addEventListener('htmx:afterSwap', function(evt) {
    if(evt.target && evt.target.classList.contains('ml-4')) {
        const btn = evt.target.parentElement.querySelector('button');
        if(btn) {
            const arrow = btn.querySelector('.category-arrow');
            if(arrow) {
                arrow.textContent = evt.target.classList.contains('hidden') ? '►' : '▼';
            }
        }
    }
});

// toggle arrow on click (before htmx loads)
document.addEventListener('click', function(evt) {
    if(evt.target.closest('button[aria-expanded]')) {
        const btn = evt.target.closest('button[aria-expanded]');
        const arrow = btn.querySelector('.category-arrow');
        const targetId = btn.getAttribute('hx-target').replace('#', '');
        const target = document.getElementById(targetId);
        if(arrow && target) {
            setTimeout(() => {
                arrow.textContent = target.classList.contains('hidden') ? '►' : '▼';
            }, 100);
        }
    }
});