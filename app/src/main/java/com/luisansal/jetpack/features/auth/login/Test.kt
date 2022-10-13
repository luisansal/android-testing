package com.luisansal.jetpack.features.auth.login

//Problemas de diseño:
//1. No especificar el rango del número aleatorio en la clase Random.
//2. Declarar la variale random que nunca es usada.
//3. Tener el while es inncesario cuando se especifica el rango para el número aleatorio
//4. Los parametros del constructor de la clase DiceRoller deben estar de modo privado ya que solo se usa en la función roll()
import kotlin.random.Random

//Resultado final
class Dice(var value: Int)

class DiceRoller(private val diceList: ArrayList<Dice>, private val isCheated: Boolean) {
    fun roll(): ArrayList<Dice> {
        diceList.forEach { dice ->
            if (isCheated) {
                dice.value = 6
            } else {
                dice.value = Random.nextInt(1,6)
            }
        }
        return diceList
    }
}