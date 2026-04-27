/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    // Configuración de tiempos límite por categoría (en segundos)
    const configTiempos = {
        'Entrante': { amarillo: 300, rojo: 600 },   // 5min y 10min
        'Principal': { amarillo: 900, rojo: 1200 }, // 15min y 20min
        'Postre': { amarillo: 300, rojo: 480 },    // 5min y 8min
        'Bebida': { amarillo: 120, rojo: 300 }     // 2min y 5min
    };

    function actualizarRelojes() {
        const ahora = new Date();
        const timers = document.querySelectorAll('.js-timer');

        timers.forEach(timer => {
            const horaInicio = new Date(timer.dataset.start);
            const diffSegundos = Math.floor((ahora - horaInicio) / 1000);
            
            // Formatear MM:SS
            const minutos = Math.floor(diffSegundos / 60).toString().padStart(2, '0');
            const segundos = (diffSegundos % 60).toString().padStart(2, '0');
            timer.innerText = `${minutos}:${segundos}`;

            // Lógica de colores
            const categoria = timer.dataset.category;
            const limites = configTiempos[categoria] || { amarillo: 600, rojo: 900 };

            if (diffSegundos >= limites.rojo) {
                timer.classList.replace('timer-green', 'timer-red');
                timer.classList.replace('timer-yellow', 'timer-red');
            } else if (diffSegundos >= limites.amarillo) {
                timer.classList.replace('timer-green', 'timer-yellow');
            }
        });
    }

    // Actualizar cada segundo
    setInterval(actualizarRelojes, 1000);
});

