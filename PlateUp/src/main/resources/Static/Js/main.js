// --- CURSOR MÁGICO ---
const cursor = document.getElementById('cursor');
const ring = document.getElementById('cursorRing');
let mx = 0, my = 0, rx = 0, ry = 0;

document.addEventListener('mousemove', e => { mx = e.clientX; my = e.clientY; });

function animCursor() {
    if (cursor && ring) {
        cursor.style.left = mx + 'px'; cursor.style.top = my + 'px';
        rx += (mx - rx) * 0.12; ry += (my - ry) * 0.12;
        ring.style.left = rx + 'px'; ring.style.top = ry + 'px';
    }
    requestAnimationFrame(animCursor);
}
animCursor();

function initHoverEffects() {
    document.querySelectorAll('a, button, .menu-tab').forEach(el => {
        el.addEventListener('mouseenter', () => {
            if (cursor) { cursor.style.width = '20px'; cursor.style.height = '20px'; }
            if (ring) { ring.style.width = '56px'; ring.style.height = '56px'; }
        });
        el.addEventListener('mouseleave', () => {
            if (cursor) { cursor.style.width = '10px'; cursor.style.height = '10px'; }
            if (ring) { ring.style.width = '36px'; ring.style.height = '36px'; }
        });
    });
}

// --- EFECTOS DE SCROLL Y NAVBAR ---
window.addEventListener('scroll', () => {
    const navbar = document.getElementById('navbar');
    if (navbar && !navbar.classList.contains('scrolled-fixed')) {
        if (window.scrollY > 60) {
            navbar.classList.add('scrolled');
        } else {
            if (window.location.pathname === '/' || window.location.pathname.endsWith('index.html')) {
                navbar.classList.remove('scrolled');
            }
        }
    }

    const sec = document.getElementById('fuego');
    const bg = document.getElementById('fuegoBg');
    if (sec && bg) {
        const r = sec.getBoundingClientRect();
        const p = -r.top / (r.height + window.innerHeight);
        bg.style.transform = `scale(1.12) translateY(${p * 40}px)`;
    }
});

// --- LÓGICA DE TEMAS (DARK / LIGHT) ---
function handleTheme() {
    const themeToggleBtn = document.getElementById('theme-toggle');
    const themeIcon = document.getElementById('theme-icon');
    
    if (!themeToggleBtn) return;

    // Restaurar tema guardado al cargar la página
    if (localStorage.getItem('theme') === 'light') {
        document.documentElement.setAttribute('data-theme', 'light');
        if (themeIcon) themeIcon.innerText = '🌙';
    }

    themeToggleBtn.addEventListener('click', () => {
        const isLight = document.documentElement.getAttribute('data-theme') === 'light';
        
        if (isLight) {
            document.documentElement.removeAttribute('data-theme');
            localStorage.setItem('theme', 'dark');
            if (themeIcon) themeIcon.innerText = '☀️';
        } else {
            document.documentElement.setAttribute('data-theme', 'light');
            localStorage.setItem('theme', 'light');
            if (themeIcon) themeIcon.innerText = '🌙';
        }
    });
}

// --- CONTADORES ANIMADOS (SECCIÓN FUEGO) ---
function initCounters() {
    const counters = document.querySelectorAll('.fuego-stat-num[data-count]');
    if (!counters.length) return;

    const obs = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (!entry.isIntersecting) return;
            obs.unobserve(entry.target);
            const target = parseInt(entry.target.dataset.count, 10);
            const duration = 1800;
            const startTime = performance.now();
            function step(now) {
                const progress = Math.min((now - startTime) / duration, 1);
                const ease = 1 - Math.pow(1 - progress, 3); // easeOutCubic
                entry.target.textContent = Math.round(ease * target);
                if (progress < 1) requestAnimationFrame(step);
            }
            requestAnimationFrame(step);
        });
    }, { threshold: 0.3 });

    counters.forEach(c => obs.observe(c));
}

// --- TEMPORIZADORES DE ESPERA EN COMANDAS ---
function initWaitTimers() {
    const timers = document.querySelectorAll('.wait-time');
    if (!timers.length) return;

    // Umbral de urgencia configurable por página via data-urgency-minutes en <body>
    const urgencyMinutes = parseInt(document.body.dataset.urgencyMinutes || '15');

    function tick() {
        const ahora = new Date();
        timers.forEach(el => {
            const start = el.getAttribute('data-start');
            if (!start) return;
            const dif = Math.floor((ahora - new Date(start)) / 1000);
            if (dif >= 0) {
                const min = Math.floor(dif / 60);
                const seg = dif % 60;
                el.innerText = `${min}m ${seg}s`;
                if (min >= urgencyMinutes) el.classList.add('urgent');
            }
        });
    }

    setInterval(tick, 1000);
    tick();
}

// --- INICIALIZACIÓN GENERAL ---
document.addEventListener('DOMContentLoaded', () => {
    handleTheme();
    initHoverEffects();
    initCounters();
    initWaitTimers();

    // Animaciones Reveal
    const reveals = document.querySelectorAll('.reveal');
    const obs = new IntersectionObserver(entries => {
        entries.forEach(e => {
            if (e.isIntersecting) { e.target.classList.add('visible'); obs.unobserve(e.target); }
        });
    }, { threshold: 0.12 });
    reveals.forEach(r => obs.observe(r));

    // Menú Hamburguesa
    const burgerBtn = document.getElementById('burger-btn');
    const navLinks = document.getElementById('nav-links');
    if (burgerBtn && navLinks) {
        burgerBtn.addEventListener('click', () => { navLinks.classList.toggle('active'); });
    }

    // Panel CRUD entrada suave
    const mainContent = document.getElementById('main-content');
    if (mainContent) { setTimeout(() => mainContent.classList.add('visible'), 100); }
});

// --- FUNCIONES GLOBALES (FUERA DEL DOMCONTENTLOADED) ---
// Deben estar en scope global porque se llaman desde atributos onclick del HTML

function tabMenu(gridId, btn) {
    document.querySelectorAll('.menu-grid').forEach(grid => grid.classList.add('hidden'));
    document.querySelectorAll('.menu-tab').forEach(t => t.classList.remove('active'));
    document.getElementById(gridId).classList.remove('hidden');
    btn.classList.add('active');
}

function filterMenu(cat, btn) {
    document.querySelectorAll('.menu-tab').forEach(t => t.classList.remove('active'));
    btn.classList.add('active');
    document.querySelectorAll('#carta-grid .menu-card').forEach(card => {
        const visible = cat === 'todos' || card.dataset.cat === cat;
        card.style.display = visible ? '' : 'none';
    });
}

// Envía el formulario de reserva vía fetch para evitar recarga de página
function handleReserva(e) {
    e.preventDefault();

    const body = new URLSearchParams({
        nombre:     document.getElementById('r-nombre').value.trim(),
        telefono:   document.getElementById('r-telefono').value.trim(),
        email:      document.getElementById('r-email').value.trim(),
        comensales: document.getElementById('r-comensales').value,
        fecha:      document.getElementById('r-fecha').value,
        hora:       document.getElementById('r-hora').value,
    });

    // Deshabilitar el botón durante el fetch evita envíos duplicados
    const btn = e.target.querySelector('button[type="submit"]');
    btn.disabled = true;
    btn.querySelector('span').textContent = 'Procesando...';

    fetch('/reservas', { method: 'POST', body, headers: { 'Content-Type': 'application/x-www-form-urlencoded' } })
        .then(r => r.json())
        .then(data => {
            showToast(data.exito ? '✦ ' + data.mensaje : '✖ ' + data.mensaje);
            if (data.exito) e.target.reset();
        })
        .catch(() => showToast('✖ Error de conexión. Inténtelo de nuevo.'))
        .finally(() => {
            btn.disabled = false;
            btn.querySelector('span').textContent = 'Confirmar Reserva';
        });
}

function showToast(msg) {
    const t = document.getElementById('toast');
    if (t) {
        t.textContent = msg;
        t.classList.add('show');
        setTimeout(() => t.classList.remove('show'), 3500);
    }
}

function scrollToReserva() {
    const res = document.getElementById('reserva');
    if (res) res.scrollIntoView({ behavior: 'smooth' });
}