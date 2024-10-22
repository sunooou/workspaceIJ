function preview(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function () {
        const imgPreview = document.getElementById('previewImage');
        imgPreview.src = reader.result;
        imgPreview.style.display = 'block';
    };
    if(file) {
        reader.readAsDataURL(file);
    }
}