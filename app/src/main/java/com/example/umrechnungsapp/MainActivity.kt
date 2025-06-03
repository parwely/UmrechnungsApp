package com.example.umrechnungsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umrechnungsapp.ui.theme.UmrechnungsAppTheme

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UmrechnungsApp(){
    //state Variables
}


//Berechnung der Umrechnung
fun berechnungUmrechnung(input: String, umrechnungstyp: Int ): String {
    if(eingabe.isEmpty()) {
        return "Bitte eine Zahl eingeben"
    }
    returntry{
        val wert = eingabe.toDouble()
        when (umrechnungstyp) {
            0 -> berechneAlterInMinuten(wert) // age to min
            1 -> berechneFlaecheInFußballfeldern(wert) // area to football fields
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

fun berechneFlaecheInFußballfeldern(flaeche: Double): String {
    val fussballfeld = 7140 // in Quadratmetern
    val anzahlFussballfelder = flaeche / fussballfeld
    return "Die Fläche entspricht ca. ${anzahlFussballfelder.toInt()} Fußballfeldern"
}

fun berechneGeldInZeit(geld: Double): String {
    val stunden = geld / 10 // assuming 10 Euro pro Stunde
    return "Mit diesem Geld kannst du ca. ${stunden.toInt()} Stunden arbeiten"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UmrechnungsAppTheme {
        Greeting("Android")
    }
}