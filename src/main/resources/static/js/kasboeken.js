"use strict"

import {byId, toon, verberg, autocomplete} from "./util.js";

const tableWrapper = byId("table-wrapper");
const tableElement = byId("kasboekTable");
const afdelingSelect = byId("afdeling-select");
const jaarSelect = byId("jaar-select");
const maandSelect = byId("maand-select");
const omschrijvingInput = byId("omschrijving-input");

function initSelect(element, text0) {
    if (element.options.length > 1) {
        while (element.options.length) {
            element.remove(0);
        }
        const option0 = document.createElement("option");
        option0.innerText = text0;
        element.appendChild(option0);
    }
    element.disabled = true;
}

function initTable() {
    byId("kasboekBody").remove();
    const tableBody0 = document.createElement('tbody');
    tableBody0.id = "kasboekBody";
    tableElement.appendChild(tableBody0);
}

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

async function maakJaarSelect(afdelingId) {
    const response = await fetch(`kasboeken/${afdelingId}/jaren`);
    if (response.ok) {
        const jaren = await response.json();
        if (jaren.length > 0) {
            for (const jaar of jaren) {
                const option = document.createElement("option");
                option.innerText = jaar;
                option.value = jaar;
                jaarSelect.appendChild(option);
            }
            jaarSelect.disabled = false;
        }
    } else {
        toon("storing");
    }
}

async function maakMaandSelect(afdelingId, jaar) {
    const response = await fetch(`kasboeken/${afdelingId}/${jaar}/maanden`);
    if (response.ok) {
        const maanden = await response.json();
        for (const maand of maanden) {
            const option = document.createElement("option");
            option.innerText = maand;
            option.value = maand;
            maandSelect.appendChild(option);
        }
        maandSelect.disabled = false;
    } else {
        toon("storing");
    }
}

async function maakKasboekBody(afdelingId, jaar, maand) {
    const response = await fetch(`kasboeken/${afdelingId}/${jaar}/${maand}`);
    if (response.ok) {
        const kasboek = await response.json();
        /*byId("afdeling").innerText = kasboek.afdelingId;
        byId("jaar").innerText = kasboek.jaar;
        byId("maand").innerText = kasboek.maand;*/
        initTable();
        for (const verrichting of kasboek.verrichtingen) {
            const tr = byId("kasboekBody").insertRow();
            const volgnummerTd = document.createElement('td');
            volgnummerTd.innerText = verrichting.volgnummer;
            tr.appendChild(volgnummerTd);
            const dagTd = document.createElement('td');
            dagTd.innerText = verrichting.dag;
            tr.appendChild(dagTd);
            const omschrijvingTd = document.createElement('td');
            omschrijvingTd.innerText = verrichting.omschrijving;
            tr.appendChild(omschrijvingTd);
            const bedragTd = document.createElement('td');
            bedragTd.innerText = verrichting.bedrag;
            tr.appendChild(bedragTd);
            const ticketTd = document.createElement('td');
            ticketTd.innerText = verrichting.ticket;
            tr.appendChild(ticketTd);
            const typeTd = document.createElement('td');
            typeTd.innerText = verrichting.type;
            tr.appendChild(typeTd);
        }
        byId("table-wrapper").hidden = false;
        getOmschrijvingen(afdelingSelect.value);
    } else {
        toon("storing");
    }
}

async function getOmschrijvingen(afdelingId) {
    const response = await fetch(`omschrijvingen/${afdelingId}`);
    if (response.ok) {
        const omschrijvingenArray =  await response.json();
        autocomplete(omschrijvingInput, omschrijvingenArray);
    } else {
        toon("storing");
    }
}

afdelingSelect.addEventListener('input', () => {
    verberg("storing");
    byId("table-wrapper").hidden = true;
    initSelect(jaarSelect, "--Kies jaar--");
    initSelect(maandSelect, "--Kies maand--");
    if (afdelingSelect.selectedIndex > 0) {
        maakJaarSelect(afdelingSelect.value);
    }
});
jaarSelect.addEventListener('input', () => {
    verberg("storing");
    byId("table-wrapper").hidden = true;
    initSelect(maandSelect, "--Kies maand--");
    if (jaarSelect.selectedIndex > 0) {
        maakMaandSelect(afdelingSelect.value, jaarSelect.value);
    }

});
maandSelect.addEventListener('input', () => {
    verberg("storing");
    byId("table-wrapper").hidden = true;
    if (maandSelect.selectedIndex > 0) {
        maakKasboekBody(afdelingSelect.value, jaarSelect.value, maandSelect.value);
    }
});

maakAfdelingSelect();


