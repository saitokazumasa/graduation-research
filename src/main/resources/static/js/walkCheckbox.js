const walkCheckbox = document.getElementById('walkFlag');
const walkThreshold = document.getElementById('walkThreshold');

walkCheckbox.addEventListener('change', () => {
    walkThreshold.disabled = !walkCheckbox.checked;
});
