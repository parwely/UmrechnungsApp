package com.example.umrechnungsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umrechnungsapp.ui.theme.UmrechnungsAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UmrechnungsAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                    ) {
                    UmrechnungsApp()
                }
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UmrechnungsApp(){
    //state Variables
    var input by remember { mutableStateOf("") }
    var umrechnungstyp by remember { mutableIntStateOf(0) }
    var ergebnisText by remember { mutableStateOf("Ergebnis wird hier angezeigt") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val umrechnungsOptionen = listOf(
        "Alter in Minuten",
        "Fläche in Fußballfeldern",
        "Geld in Zeit"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Titel
        Text(
            text = "Umrechnungs-App",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Dropdown für Umrechnungstyp
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
        ) {
            OutlinedTextField(
                value = umrechnungsOptionen[umrechnungstyp],
                onValueChange = { },
                readOnly = true,
                label = { Text("Umrechnungstyp wählen") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)  //ist das die korrekte AnchorType?
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                umrechnungsOptionen.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            umrechnungstyp = index
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Eingabefeld
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Wert eingeben") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Berechnen Button
        Button(
            onClick = {
                ergebnisText = berechneUmrechnung(input, umrechnungstyp)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Berechnen")
        }

        // Ergebnis Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = ergebnisText,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


//Berechnung der Umrechnung
fun berechneUmrechnung(input: String, umrechnungstyp: Int ): String {
    if(input.isEmpty()) {
        return "Bitte eine Zahl eingeben"
    }
    return try{
        val wert = input.toDouble()
        when (umrechnungstyp) {
            0 -> berechneAlterInMinuten(wert) // age to min
            1 -> berechneFlaecheInFussballfeldern(wert) // area to football fields
            2 -> berechneGeldInZeit(wert)  // money to time
            else -> "Unbekannter Umrechnungstyp"
        }
    } catch (e: NumberFormatException) {
        "Ungültige Eingabe, Bitte eine Zahl eingeben"
    }
}


fun berechneAlterInMinuten(alter: Double): String {
    val minuten = alter * 365.25 * 24 * 60
    return "Dein Alter in Minuten: ${minuten.toInt()} Minuten"
}

fun berechneFlaecheInFussballfeldern(flaeche: Double): String {
    val fussballfeld = 7140 // in Quadratmetern
    val anzahlFussballfelder = flaeche / fussballfeld
    return "Die Fläche entspricht ca. ${anzahlFussballfelder.toInt()} Fußballfeldern"
}



fun berechneGeldInZeit(geld: Double): String {
    val tage = geld / (24 * 60 * 60)
    return when {
        tage < 1 -> "Das entspricht ca. ${String.format(Locale.GERMAN, "%.1f", tage * 24)} Stunden."
        tage < 365 -> "Das entspricht ca. ${String.format(Locale.GERMAN, "%.1f", tage)} Tagen."
        else -> "Das entspricht ca. ${String.format(Locale.GERMAN, "%.1f", tage / 365.25)} Jahren."
    }
}

@Preview(showBackground = true)
@Composable
fun UmrechnungsAppPreview() {
    UmrechnungsAppTheme {
        UmrechnungsApp()
    }
}