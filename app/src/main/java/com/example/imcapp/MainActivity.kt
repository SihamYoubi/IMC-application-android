package com.example.imcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.imcapp.ui.theme.IMCAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IMCCalculator()
                }
            }
        }
    }
}

@Composable
fun IMCCalculator() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var imageRes by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Poids (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Taille (m)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val w = weight.toFloatOrNull()
            val h = height.toFloatOrNull()
            if (w != null && h != null && h > 0) {
                val imc = w / (h * h)
                val category = when {
                    imc < 18.5 -> {
                        imageRes = R.drawable.maigre
                        "Maigreur"
                    }
                    imc < 25 -> {
                        imageRes = R.drawable.normal
                        "Normal"
                    }
                    imc < 30 -> {
                        imageRes = R.drawable.surpoids
                        "Surpoids"
                    }
                    imc < 40 -> {
                        imageRes = R.drawable.obese
                        "Obésité modérée"
                    }
                    else -> {
                        imageRes = R.drawable.t_obese
                        "Obésité sévère"
                    }
                }
                result = "IMC: %.2f\nCatégorie: %s".format(imc, category)
            } else {
                result = "Entrez des valeurs valides"
                imageRes = null
            }
        }) {
            Text("Calculer l'IMC")
        }

        Text(text = result, color = Color.Black)

        imageRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
