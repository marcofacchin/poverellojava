"use strict"

import {byId, toon, verberg} from "./util.js";

/*const tableWrapper = byId("table-wrapper");
const tableBody = byId("toegevoegdeArtikelenBody");*/
const afdelingSelect = byId("afdeling-select");
const jaarSelect = byId("jaar-select");
const maandSelect = byId("maand-select");

async function maakAfdelingSelect() {
    const response = await fetch("afdelingen");
    if (response.ok) {
        const afdelingen = await response.json();
        for (const afdeling of afdelingen) {
            const option = document.createElement("option");
            option.innerText = afdeling.naam;
            option.value = afdeling.id;
            afdelingSelect.appendChild(option);
        }
        afdelingSelect.disabled = false;
    } else {
        toon("storing");
    }
}

/*afdelingSelect.addEventListener('input', () => {
    maandSelect.disabled = true;
    maakJaarSelect();
    jaarSelect.disabled = false;
});*/
/*jaarSelect.addEventListener('input', () => {
    maakMaandSelect();
    maandSelect.enabled = true;
});*/
/*maandSelect.addEventListener('input', () => {
    maakKasboekBody();
});*/

maakAfdelingSelect();
