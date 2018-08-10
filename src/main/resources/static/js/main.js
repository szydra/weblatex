function saveImage(imageId, fileName) {
  var link = document.createElement("a");
  link.href = document.getElementById(imageId).src;
  link.download = fileName;
  link.type = "hidden";
  document.body.appendChild(link);
  link.click();
}