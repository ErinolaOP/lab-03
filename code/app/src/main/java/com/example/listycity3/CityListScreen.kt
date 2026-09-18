package com.example.listycity3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color


@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity : (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCity by remember { mutableStateOf<City?>(null) }
    var showAddCityFields by remember { mutableStateOf(false) }
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }

    val updatedCity = City(
        name = newCityName,
        province = newProvinceName
    )
    val isEditingorAdding = showAddCityFields || selectedCity != null

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        selectedCity = null
                        showAddCityFields = !showAddCityFields
                    }
                ) {Text("+") }
        }
        if (isEditingorAdding) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text(if(selectedCity != null)"Updated City" else "City") },
                    modifier = Modifier.weight(1f)

                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text(if(selectedCity != null)"Updated Province" else "Province") },
                    modifier = Modifier.weight(1f)

                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            if(selectedCity != null){
                                onUpdateCity(selectedCity!!, updatedCity)
                            }
                            else{
                            onAddCity(
                                City(name = newCityName, province = newProvinceName)
                            )}
                            newCityName = ""
                            newProvinceName = ""
                            selectedCity = null
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text (if(selectedCity != null)("UPDATE CITY") else "ADD CITY")
                }
            }
        }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(cities) { index, city ->
            CityRow(city = city,
                isSelected = (city == selectedCity),
                onClick = { //Select city, field population then enters update ver
                    selectedCity = city
                    newCityName = city.name
                    newProvinceName = city.province
                    showAddCityFields = false
                }
            )
            if (index < cities.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}
}

@Composable
fun CityRow(city: City, isSelected: Boolean, onClick: ()-> Unit) {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick()} //made clickable
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            color = if(isSelected) Color.Gray else Color.Unspecified,
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            color = if(isSelected) Color.Gray else Color.Unspecified,
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = {_, _, ->}
        )
    }
}