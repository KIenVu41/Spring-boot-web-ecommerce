/* loading */
var myVar;

function myFunction() {
  myVar = setTimeout(showPage, 2000);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}
/* end */
/* toogle btn */
var el = document.getElementById("wrapper");
var toggleButton = document.getElementById("menu-toggle");

toggleButton.onclick = function () {
  el.classList.toggle("toggled");
};
/* end */

/* show password */
function showPassword1() {
  let x = document.getElementById("inputPassword1");
  if (x.type === "password") {
    x.type = "text";
    document.getElementById("show1").style.display = "none";
    document.getElementById("hide1").style.display = "block";
  } else {
    x.type = "password";
    document.getElementById("show1").style.display = "block";
    document.getElementById("hide1").style.display = "none";
  }
}
function showPassword2() {
  let y = document.getElementById("inputPassword2");
  if (y.type === "password") {
    y.type = "text";
    document.getElementById("show2").style.display = "none";
    document.getElementById("hide2").style.display = "block";
  } else {
    y.type = "password";
    document.getElementById("show2").style.display = "block";
    document.getElementById("hide2").style.display = "none";
  }
}
function showPassword3() {
  let z = document.getElementById("inputPassword3");
  if (z.type === "password") {
    z.type = "text";
    document.getElementById("show3").style.display = "none";
    document.getElementById("hide3").style.display = "block";
  } else {
    z.type = "password";
    document.getElementById("show3").style.display = "block";
    document.getElementById("hide3").style.display = "none";
  }
}


