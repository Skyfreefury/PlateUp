// --- CURSOR MÁGICO ---
const cursor = document.getElementById('cursor');
const ring = document.getElementById('cursorRing');
let mx=0,my=0,rx=0,ry=0;

document.addEventListener('mousemove', e => { mx=e.clientX; my=e.clientY; });

function animCursor() {
  if(cursor && ring) {
    cursor.style.left=mx+'px'; cursor.style.top=my+'px';
    rx+=(mx-rx)*0.12; ry+=(my-ry)*0.12;
    ring.style.left=rx+'px'; ring.style.top=ry+'px';
  }
  requestAnimationFrame(animCursor);
}
animCursor();

document.querySelectorAll('a,button').forEach(el => {
  el.addEventListener('mouseenter',()=>{ if(cursor) cursor.style.width='20px'; if(cursor) cursor.style.height='20px'; if(ring) ring.style.width='56px'; if(ring) ring.style.height='56px';});
  el.addEventListener('mouseleave',()=>{ if(cursor) cursor.style.width='10px'; if(cursor) cursor.style.height='10px'; if(ring) ring.style.width='36px'; if(ring) ring.style.height='36px';});
});


// --- EFECTOS DE SCROLL Y NAVBAR ---
window.addEventListener('scroll', () => {
  const navbar = document.getElementById('navbar');
  if(navbar && !navbar.classList.contains('scrolled-fixed')) {
      if(window.scrollY > 60) {
          navbar.classList.add('scrolled');
      } else {
          if(window.location.pathname === '/') {
              navbar.classList.remove('scrolled');
          }
      }
  }

  const sec = document.getElementById('fuego');
  const bg = document.getElementById('fuegoBg');
  if(sec && bg){
    const r = sec.getBoundingClientRect();
    const p = -r.top / (r.height + window.innerHeight);
    bg.style.transform = `scale(1.12) translateY(${p*40}px)`;
  }
});


// --- ANIMACIONES DE APARICIÓN (REVEAL) ---
const reveals = document.querySelectorAll('.reveal');
const obs = new IntersectionObserver(entries => {
  entries.forEach(e => {
      if(e.isIntersecting){ e.target.classList.add('visible'); obs.unobserve(e.target); }
  });
}, {threshold:0.12});
reveals.forEach(r => obs.observe(r));


// --- CONTADORES NUMÉRICOS ---
function animCount(el, target, fmt) {
  let cur=0; const step=target/70;
  const t = setInterval(() => {
    cur = Math.min(cur+step, target);
    el.textContent = fmt ? Math.floor(cur).toLocaleString('es-ES') : Math.floor(cur);
    if(cur >= target) clearInterval(t);
  }, 16);
}

const cObs = new IntersectionObserver(entries => {
  entries.forEach(e => {
      if(e.isIntersecting){ 
          document.querySelectorAll('.stat-num').forEach(el => animCount(el, +el.dataset.count, false)); 
          cObs.disconnect(); 
      }
  });
}, {threshold:0.3});
const cEl = document.querySelector('.exp-stats');
if(cEl) cObs.observe(cEl);

const fObs = new IntersectionObserver(entries => {
  entries.forEach(e => {
      if(e.isIntersecting){ 
          document.querySelectorAll('.fuego-stat-num').forEach(el => animCount(el, +el.dataset.count, +el.dataset.count>=1000)); 
          fObs.disconnect(); 
      }
  });
}, {threshold:0.3});
const fEl = document.querySelector('.fuego-stats');
if(fEl) fObs.observe(fEl);


// --- PARTICULAS DOTS ---
const dotsEl = document.getElementById('expDots');
if(dotsEl) {
  for(let i=0; i<12; i++){
    const d = document.createElement('div');
    d.className = 'exp-dot';
    d.style.cssText = `left:${Math.random()*90}%;top:${Math.random()*90}%;animation:scrollPulse ${2+Math.random()*3}s ${Math.random()*2}s ease-in-out infinite;`;
    dotsEl.appendChild(d);
  }
}


// --- FORMULARIOS Y TOASTS ---
function handleReserva(e) {
  e.preventDefault();
  showToast('✦ Reserva confirmada — Le contactaremos en breve');
  e.target.reset();
}

function showToast(msg) {
  const t = document.getElementById('toast');
  if(t) {
    t.textContent = msg; 
    t.classList.add('show');
    setTimeout(() => t.classList.remove('show'), 3500);
  }
}

function scrollToReserva() {
  const res = document.getElementById('reserva');
  if(res) res.scrollIntoView({behavior:'smooth'});
}