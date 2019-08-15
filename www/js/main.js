// Variables
let isMenuOpen = false;
let navMenu = document.querySelector('.navbar');
let navList = document.querySelector('.navbar-list');
let navItems = document.querySelectorAll('.navbar-item');
let toggleBtn = document.getElementById('toggle-button');

// Functions
function toggleMenu() {
    !isMenuOpen ? openMenu() : closeMenu();
}

function openMenu() {
    navMenu.style.opacity = '1';
    navMenu.style.visibility = 'visible';
    navList.style.maxHeight = '100vh';
    for (let i = 0; i < navItems.length; i++) {
        navItems[i].style.opacity = '1';
        // navItems[i].style.display = 'block';
    }
    toggleBtn.className = toggleBtn.className.replace('fa-bars', 'fa-times');
    isMenuOpen = true;
}

function closeMenu() {
    navMenu.style.opacity = '0';
    navMenu.style.visibility = 'hidden';
    navList.style.maxHeight = '0vh';
    for (let i = 0; i < navItems.length; i++) {
        navItems[i].style.opacity = '0';
        // navItems[i].style.display = 'none';
    }
    toggleBtn.className = toggleBtn.className.replace('fa-times', 'fa-bars');
    isMenuOpen = false;
}