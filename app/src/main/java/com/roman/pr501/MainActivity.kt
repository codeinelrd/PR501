/**
 * Paquete que contiene la implementación de la aplicación para el cálculo de hipotecas.
 */
package com.roman.pr501

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Clase principal que representa la actividad principal de la aplicación.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF003366) ) {
                HipotecaCalculator()
            }
        }
    }
}

/**
 * Función composable que define la interfaz de usuario para el cálculo de hipotecas.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HipotecaCalculator() {
    // Estado para los valores de entrada y el resultado del cálculo
    var precioTotal by remember { mutableStateOf("") }
    var plazos by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // Obtener el controlador de teclado
    val keyboardController = LocalSoftwareKeyboardController.current

    // Diseño de la interfaz de usuario usando Compose
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de entrada para el precio total de la hipoteca
        TextField(
            value = precioTotal,
            onValueChange = { precioTotal = it },
            label = { Text("Precio Total de la Hipoteca") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada para el número de plazos
        TextField(
            value = plazos,
            onValueChange = { plazos = it },
            label = { Text("Número de Plazos") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular la cuota mensual
        Button(
            onClick = {
                try {
                    // Convertir las entradas a tipos numéricos
                    val precioTotalDouble = precioTotal.toDoubleOrNull() ?: 0.0
                    val plazosInt = plazos.toIntOrNull() ?: 0

                    // Validar las entradas y realizar el cálculo
                    if (precioTotalDouble <= 0 || plazosInt <= 0) {
                        resultado = "Ingrese valores válidos para Precio Total y Plazos"
                    } else {
                        // Realizar el cálculo de la cuota mensual aquí
                        val cuotaMensual = precioTotalDouble / plazosInt
                        resultado = "Cuota Mensual: ${cuotaMensual.format(2)}Є"
                    }
                } catch (e: Exception) {
                    resultado = "Error: ${e.message}"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFF003366)) ) {
            Text("Calcular Cuota Mensual", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado del cálculo
        Text(
            resultado,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFFFF0000))
    }
}

/**
 * Extensión para formatear un número decimal con la cantidad especificada de dígitos decimales.
 * @param digits Cantidad de dígitos decimales.
 * @return Cadena formateada.
 */
fun Double.format(digits: Int) = "%.${digits}f".format(this)

/**
 * Vista previa de la interfaz de usuario.
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HipotecaCalculator()
}
